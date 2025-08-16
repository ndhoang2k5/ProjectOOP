// File: src/features/books/BooksPage.js

import React, { useState, useEffect, useMemo } from 'react';
import BookList from './components/BookList';
import BookForm from './components/BookForm';
import BookSearch from './components/BookSearch';
import Pagination from '../../components/common/Pagination'; // Component để chia trang
import useDebounce from '../../hooks/useDebounce';
import * as api from '../../service/mockApi'; 

// Cấu hình: Số sách hiển thị trên mỗi trang để "thu gọn" danh sách
const ITEMS_PER_PAGE = 15; 

function BooksPage() {
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const debouncedSearchTerm = useDebounce(searchTerm, 500);

  // `allBooks` sẽ chứa TOÀN BỘ sách tải về từ backend
  const [allBooks, setAllBooks] = useState([]);
  
  // State để quản lý trang người dùng đang xem
  const [currentPage, setCurrentPage] = useState(1);

  // SỬA LỖI: useEffect này chỉ chạy MỘT LẦN để tải tất cả sách về
  useEffect(() => {
    const fetchAllBooks = async () => {
      setLoading(true);
      try {
        // Luôn gọi với chuỗi rỗng để lấy TẤT CẢ sách
        const results = await api.searchBooks(''); 
        setAllBooks(Array.isArray(results) ? results : []);
      } catch (error) {
        console.error("Lỗi khi tải danh sách sách:", error);
        setAllBooks([]);
      } finally {
        setLoading(false);
      }
    };
    fetchAllBooks();
  }, []); // Mảng rỗng đảm bảo useEffect chỉ chạy 1 lần khi component được tạo

  // --- LOGIC LỌC VÀ PHÂN TRANG (Client-Side) ---

  // 1. Lọc sách dựa trên từ khóa tìm kiếm
  const filteredBooks = useMemo(() => {
    if (!debouncedSearchTerm) {
      return allBooks;
    }
    const lowerCaseTerm = debouncedSearchTerm.toLowerCase();
    return allBooks.filter(book =>
      (book.title || book.bookName)?.toLowerCase().includes(lowerCaseTerm) ||
      (book.author)?.toLowerCase().includes(lowerCaseTerm)
    );
  }, [allBooks, debouncedSearchTerm]);

  // 2. Tính toán tổng số trang và "cắt" ra sách cho trang hiện tại
  const totalPages = Math.ceil(filteredBooks.length / ITEMS_PER_PAGE);
  const currentBooks = useMemo(() => {
    const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
    return filteredBooks.slice(startIndex, startIndex + ITEMS_PER_PAGE);
  }, [currentPage, filteredBooks]);

  // Khi người dùng gõ tìm kiếm, tự động quay về trang 1
  useEffect(() => {
    setCurrentPage(1);
  }, [debouncedSearchTerm]);

  const handleAddBook = async (bookData) => {
    setLoading(true);
    try {
      await api.addBook(bookData);
      alert('Thêm sách thành công!');
      // Tải lại toàn bộ danh sách để có dữ liệu mới nhất
      const results = await api.searchBooks(''); 
      setAllBooks(results);
    } catch (error) {
      console.error('Lỗi khi thêm sách:', error);
      alert('Thêm sách thất bại!');
    } finally {
      setLoading(false);
    }
  };
  
  return (
    <div>
      <h2 className="page-header">Quản lý Sách</h2>
      <div className="card">
        <h3>Tìm kiếm sách</h3>
        <BookSearch searchTerm={searchTerm} onSearchChange={setSearchTerm} />
      </div>
      <div className="card">
        <h3>Danh sách</h3>
        {/* BookList bây giờ chỉ nhận danh sách sách của trang hiện tại */}
        <BookList books={currentBooks} loading={loading} />
        
        {/* Component phân trang để "thu gọn" danh sách */}
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={page => setCurrentPage(page)}
        />
      </div>
      <div className="card">
        <h3>Thêm sách mới</h3>
        <BookForm onSubmit={handleAddBook} />
      </div>
    </div>
  );
}

export default BooksPage;