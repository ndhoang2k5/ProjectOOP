import React from 'react';

function StudentStatusCard({ status, error }) {
  if (error) return <p className="error">{error}</p>;
  if (!status) return <p>Nhập mã sinh viên ở trên để xem thông tin.</p>;
  
  return (
    <div>
      <h4>Thông tin sinh viên</h4>
      <p><strong>Mã SV:</strong> {status.student.studentId} - <strong>Tên:</strong> {status.student.studentName}</p>
      <h4>Sách đang mượn ({status.activeBorrows.length}):</h4>
      {status.activeBorrows.length > 0 ? (
        <ul>
          {status.activeBorrows.map(b => (
            <li key={b.id}>ID Mượn: {b.id} - Sách: "{b.bookTitle}" (Ngày mượn: {b.borrowDate})</li>
          ))}
        </ul>
      ) : <p>Không có sách nào đang mượn.</p>}
    </div>
  );
}

export default StudentStatusCard;
