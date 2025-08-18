import React from 'react';

function StudentStatusCard({ status, error }) {
  if (error) return <p className="error">{error}</p>;
  if (!status) return <p>Nhập mã sinh viên ở trên để xem thông tin.</p>;
  
  const activeBorrows = status.activeBorrows || [];
  
  return (
    <div>
      {}
      <h4>Thông tin sinh viên</h4>
      {status.student ? (
        <p><strong>Mã SV:</strong> {status.student.studentId} - <strong>Tên:</strong> {status.student.studentName} - <strong>Email:</strong> {status.student.studentEmail}</p>
      ) : (
        <p>Không có thông tin sinh viên.</p>
      )}

      {}
      <h4>Sách đang mượn ({activeBorrows.length}):</h4>
      {activeBorrows.length > 0 ? (
        <ul>
          {activeBorrows.map(b => (
            <li key={b.id}>ID Mượn: {b.id} - Sách: "{b.bookTitle}" (Ngày mượn: {b.borrowDate})</li>
          ))}
        </ul>
      ) : <p></p>}
    </div>
  );
}

export default StudentStatusCard;