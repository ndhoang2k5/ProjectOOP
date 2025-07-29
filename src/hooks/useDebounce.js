import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Sidebar from './components/layout/Sidebar';
import BooksPage from './features/books/BooksPage';
import StudentsPage from './features/students/StudentsPage';
import BorrowingPage from './features/borrowing/BorrowingPage';

function App() {
  return (
    <div className="app-container">
      <Sidebar />
      <main className="main-content">
        <Routes>
          <Route path="/" element={<Navigate replace to="/books" />} />
          <Route path="/books" element={<BooksPage />} />
          <Route path="/students" element={<StudentsPage />} />
          <Route path="/borrowing" element={<BorrowingPage />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;