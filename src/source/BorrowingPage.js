import React, { useState } from 'react';
import BorrowingForm from './components/BorrowingForm';
import ReturnForm from './components/ReturnForm';
import * as api from '../../service/mockApi';

function BorrowingPage() {
  const [message, setMessage] = useState({ text: '', type: '' });
  const showMessage = (text, type) => {
      setMessage({ text, type });
      setTimeout(() => setMessage({ text: '', type: '' }), 5000);
  }

  const handleCreateBorrow = async (studentId, bookId) => {
    try { await api.createBorrowing(studentId, bookId); showMessage('Tạo phiếu mượn thành công!', 'success'); } 
    catch (err) { showMessage(`Lỗi: ${err.message}`, 'error'); }
  };

  const handleEndBorrow = async (borrowingId) => {
    try { await api.endBorrowing(borrowingId); showMessage('Ghi nhận trả sách thành công!', 'success'); } 
    catch (err) { showMessage(`Lỗi: ${err.message}`, 'error'); }
  };

  return (
    <div>
      <h2 className="page-header">Quản lý Mượn/Trả sách</h2>
      {message.text && <p className={message.type}>{message.text}</p>}
      <div className="card">
        <h3>Tạo mượn sách</h3>
        <BorrowingForm onSubmit={handleCreateBorrow} />
      </div>
      <div className="card">
        <h3>Kết thúc mượn sách (Trả sách)</h3>
        <ReturnForm onSubmit={handleEndBorrow} />
      </div>
    </div>
  );
}
export default BorrowingPage;