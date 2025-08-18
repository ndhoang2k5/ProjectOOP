
import React, { useState, useEffect, useMemo } from 'react';
import BookList from './components/BookList';
import BookForm from './components/BookForm';
import BookSearch from './components/BookSearch';
import Pagination from '../../components/common/Pagination';
import useDebounce from '../../hooks/useDebounce';
import * as api from '../../service/mockApi';
import BookDeleteForm from './components/BookDeleteForm';

const ITEMS_PER_PAGE = 15;

function BooksPage() {
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const debouncedSearchTerm = useDebounce(searchTerm, 500);
  const [allBooks, setAllBooks] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    const fetchAllBooks = async () => {
      setLoading(true);
      try {
        const results = await api.searchBooks('');
        setAllBooks(Array.isArray(results) ? results : []);
      } catch (error) {
        setAllBooks([]);
      } finally {
        setLoading(false);
      }
    };
    fetchAllBooks();
  }, []);

  const handleAddBook = async (bookData) => {
    setLoading(true);
    try {
      await api.addBook(bookData);
      alert('Thêm sách thành công!');
      const results = await api.searchBooks('');
      setAllBooks(results);
    } catch (error) {
      alert('Thêm sách thất bại!');
    } finally {
      setLoading(false);
    }
  };
  

  const handleDeleteBook = async (bookId) => {
    if (window.confirm(`Bạn có chắc chắn muốn xóa sách có ID ${bookId}?`)) {
      try {
        await api.deleteBook(bookId);
        alert('Xóa sách thành công!');
        // Cập nhật lại danh sách trên giao diện mà không cần gọi lại API
        setAllBooks(prevBooks => prevBooks.filter(book => book.bookId !== bookId));
      } catch (err) {
        // Lỗi đã được xử lý trong apiService
      }
    }
  };

  const filteredBooks = useMemo(() => {
    if (!debouncedSearchTerm) return allBooks;
    const lowerCaseTerm = debouncedSearchTerm.toLowerCase();
    return allBooks.filter(book =>
      (book.bookName)?.toLowerCase().includes(lowerCaseTerm) ||
      (book.author)?.toLowerCase().includes(lowerCaseTerm)
    );
  }, [allBooks, debouncedSearchTerm]);

  const totalPages = Math.ceil(filteredBooks.length / ITEMS_PER_PAGE);
  const currentBooks = useMemo(() => {
    const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
    return filteredBooks.slice(startIndex, startIndex + ITEMS_PER_PAGE);
  }, [currentPage, filteredBooks]);

  useEffect(() => {
    setCurrentPage(1);
  }, [debouncedSearchTerm]);
  
  return (
    <div>
      <h2 className="page-header">Quản lý Sách</h2>
      <div className="card">
        <h3>Tìm kiếm sách</h3>
        <BookSearch searchTerm={searchTerm} onSearchChange={setSearchTerm} />
      </div>
      <div className="card">
        <h3>Danh sách</h3>
        <BookList 
          books={currentBooks} 
          loading={loading} 
        />
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={setCurrentPage}
        />
      </div>
      <div className="card">
        <h3>Thêm sách mới</h3>
        <BookForm onSubmit={handleAddBook} />
      </div>
      <div className="card">
        <h3>Xóa sách</h3>
        <BookDeleteForm onDelete={handleDeleteBook} />
      </div>
    </div>
  );
}

export default BooksPage;