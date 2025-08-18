// src/features/borrowing/BorrowingPage.js
import React, { useState } from 'react';
import BorrowingForm from './components/BorrowingForm';
import ReturnForm from './components/ReturnForm';
import * as api from '../../service/mockApi';
import FindBorrowForm from './components/FindBorrowForm';
import BorrowRecordCard from './components/BorrowingRecordCard';

// Component con để hiển thị kết quả (Giữ nguyên, đã đúng)
function TransactionResult({ result }) {
  if (!result) return null;

  if (result.type === 'borrow') {
    return (
      <div className="card-result success">
        <h4>Tạo phiếu mượn thành công!</h4>
        <p><strong>ID Phiếu mượn:</strong> {result.data.recordId}</p>
        <p><strong>Mã Sinh viên:</strong> {result.data.studentId}</p>
        <p><strong>ID Sách:</strong> {result.data.bookId}</p>
        <p><strong>Ngày mượn:</strong> {result.data.borrowDate}</p>
      </div>
    );
  }

  if (result.type === 'return') {
    return (
      <div className="card-result success">
        <h4>Ghi nhận trả sách thành công!</h4>
        <p><strong>ID Phiếu mượn:</strong> {result.data.recordId}</p>
        <p>Sách (ID: {result.data.bookId}) đã được trả vào ngày {result.data.returnDate}.</p>
      </div>
    );
  }

  if (result.type === 'error') {
     // Sửa lại để hiển thị lỗi từ backend một cách chi tiết hơn
     const errorMessage = result.message.includes(":") ? result.message.split(":")[1] : result.message;
     return <div className="card-result error"><h4>Lỗi:</h4><p>{errorMessage}</p></div>
  }

  return null;
}


function BorrowingPage() {
  const [latestTransaction, setLatestTransaction] = useState(null);
  const [message, setMessage] = useState({text: "", type: "info"});
  const [foundRecord, setFoundRecord] = useState(null);
  const [findError, setFindError] = useState('');

  const showMessage = (text, type) => {
    setMessage({ text, type });
    setTimeout(() => setMessage({ text: '', type: '' }), 5000);
  };

  const handleCreateBorrow = async (studentId, bookId) => {
    setLatestTransaction(null); // Xóa kết quả cũ trước khi thực hiện hành động mới
    try {
      const resultData = await api.createBorrowing(studentId, bookId);
      setLatestTransaction({ type: 'borrow', data: resultData });
    } 
    catch (err) {
      // handleError trong mockApi đã throw error, ta bắt nó ở đây
      // err.response.data chứa object { "error": "message" } từ backend
      const message = err.response?.data?.error || err.message;
      setLatestTransaction({ type: 'error', message: message });
    }
  };

  // SỬA LẠI TÊN HÀM CHO NHẤT QUÁN VÀ SỬA LOGIC BÁO LỖI
  const handleReturnBook = async (borrowingId) => {
    setLatestTransaction(null); // Xóa kết quả cũ
    try {
      const resultData = await api.returnBook(borrowingId);
      setLatestTransaction({ type: 'return', data: resultData });
    } 
    catch (err) {
      const message = err.response?.data?.error || err.message;
      setLatestTransaction({ type: 'error', message: message });
    }
  };

  const handleFindBorrow = async (recordId) => {
    setFoundRecord(null);
    setFindError('');
    try {
      const result = await api.getBorrowRecordById(recordId);
      setFoundRecord(result);
    } catch (err) {
      setFindError(err.message);
    }
  };

  return (
    <div>
      <h2 className="page-header">Quản lý Mượn/Trả sách</h2>
      
      {/* {latestTransaction && <TransactionResult result={latestTransaction} />} */}
      {message.text && <p className={message.type}>{message.text}</p>}
      
      <div className="card">
        <h3>Tạo mượn sách</h3>
        <BorrowingForm onSubmit={handleCreateBorrow} />
      </div>

      <div className="card">
        <h3>Kết thúc mượn sách (Trả sách)</h3>
        {/* SỬA LẠI PROP ĐỂ TRUYỀN VÀO HÀM MỚI */}
        <ReturnForm onSubmit={handleReturnBook} />
      </div>
      <div className="card">
        <h3>Tìm phiếu mượn</h3>
        <FindBorrowForm onSearch={handleFindBorrow}/>
        <BorrowRecordCard record={foundRecord} error={findError}/>
      </div>
    </div>
  );
}
export default BorrowingPage;