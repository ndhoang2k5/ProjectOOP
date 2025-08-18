import React from 'react';

function Pagination({ currentPage, totalPages, onPageChange }) {
  // Nếu chỉ có 1 trang hoặc không có trang nào, không cần hiển thị các nút
  if (totalPages <= 1) {
    return null;
  }

  const handlePrevious = () => {
    // Chỉ gọi khi không phải ở trang đầu tiên
    if (currentPage > 1) {
      onPageChange(currentPage - 1);
    }
  };

  const handleNext = () => {
    // Chỉ gọi khi không phải ở trang cuối cùng
    if (currentPage < totalPages) {
      onPageChange(currentPage + 1);
    }
  };

  return (
    <div className="pagination-container">
      <button 
        onClick={handlePrevious} 
        disabled={currentPage === 1} 
        className="btn"
      >
        Trang trước
      </button>

      <span className="pagination-info">
        Trang {currentPage} / {totalPages}
      </span>

      <button 
        onClick={handleNext} 
        disabled={currentPage === totalPages} 
        className="btn"
      >
        Trang sau
      </button>
    </div>
  );
}

export default Pagination;