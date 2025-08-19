// frontend/src/App.js

import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

// DÒNG SỬA 1: Thay đổi cách import
import { QRCodeSVG } from 'qrcode.react'; // Sửa từ 'QRCode' thành '{ QRCodeSVG }'

// Import các thành phần layout và các trang tính năng
import Sidebar from './components/layout/Sidebar';
import BooksPage from './features/books/BooksPage';
import StudentsPage from './features/students/StudentsPage';
import BorrowingPage from './features/borrowing/BorrowingPage';

function App() {

  // PHẦN THÊM MỚI: Định nghĩa URL cho mã QR
  // !!! QUAN TRỌNG: Hãy thay thế "192.168.1.XX" bằng địa chỉ IP của bạn
  const localNetworkURL = 'http://192.168.20.139:3000'; // sửa địa chỉ IP cho phù hợp với mạng nội bộ của bạn

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
      
      {/* --- PHẦN THÊM MỚI: Hiển thị mã QR --- */}
      <div style={{
        position: 'fixed',
        left: '20px',
        bottom: '20px',
        padding: '15px',
        backgroundColor: 'white',
        borderRadius: '8px',
        boxShadow: '0 4px 8px rgba(0,0,0,0.2)',
        textAlign: 'center'
      }}>
        <p style={{ margin: '0 0 10px 0', fontWeight: 'bold' }}>Quét để mở trên điện thoại</p>
        {/* DÒNG SỬA 2: Thay đổi tên component */}
        <QRCodeSVG value={localNetworkURL} size={128} /> {/* Sửa từ <QRCode> thành <QRCodeSVG> */}
      </div>
      {/* --- KẾT THÚC PHẦN THÊM MỚI --- */}
    </div>
  );
}

export default App;
