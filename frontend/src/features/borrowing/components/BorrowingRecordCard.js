import React from 'react';

function BorrowRecordCard({ record, error }) {
    if (error) return <div className="card-result error" style={{marginTop: '15px'}}><p>{error}</p></div>;
    if (!record) return null;

    return (
        <div className="card-result" style={{marginTop: '15px', borderTop: '1px solid #00ffcc', paddingTop: '15px'}}>
            <h4>Kết quả tìm kiếm:</h4>
            <p><strong>ID Phiếu mượn:</strong> {record.recordId}</p>
            <p><strong>Tên sách:</strong> {record.bookName}</p>
            <p><strong>ID sách:</strong> {record.bookId}</p>
            <p><strong>Sinh viên mượn:</strong> {record.studentName} <strong>  (Mã Sinh viên:  </strong>     {record.studentId})</p>
            <p><strong>Ngày mượn:</strong> {record.borrowDate}</p>
            <p><strong>Ngày trả:</strong> {record.returnDate || 'Chưa trả'}</p>
        </div>
    );
}
export default BorrowRecordCard;