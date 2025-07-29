import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

// Import các thành phần layout và các trang tính năng
import Sidebar from './components/layout/Sidebar';
import BooksPage from './features/books/BooksPage';
import StudentsPage from './features/students/StudentsPage';
import BorrowingPage from './features/borrowing/BorrowingPage';

function App() {
  return (
    // Bố cục chung của ứng dụng
    <div className="app-container">
      {/* Sidebar luôn được hiển thị */}
      <Sidebar />

      {/* Phần nội dung chính, nơi các trang sẽ được hiển thị */}
      <main className="main-content">
        {/* Hệ thống định tuyến */}
        <Routes>
          {/* 
            Route mặc định:
            Khi người dùng truy cập vào đường dẫn gốc "/",
            tự động chuyển hướng họ đến trang "/books".
            "replace" để không tạo lịch sử duyệt cho việc chuyển hướng này.
          */}
          <Route path="/" element={<Navigate replace to="/books" />} />

          {/* Định nghĩa các trang tương ứng với từng đường dẫn */}
          <Route path="/books" element={<BooksPage />} />
          <Route path="/students" element={<StudentsPage />} />
          <Route path="/borrowing" element={<BorrowingPage />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;