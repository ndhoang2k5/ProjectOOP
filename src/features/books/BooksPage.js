import React, { useState, useEffect } from 'react';
import BookList from './components/BookList';
import BookForm from './components/BookForm';
import BookSearch from './components/BookSearch'; // <-- Dùng component search
import useDebounce from '../../hooks/useDebounce';
import * as api from '../../service/mockApi'; 

function BooksPage() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const debouncedSearchTerm = useDebounce(searchTerm, 500);
  const [isCollapsed, setIsCollapsed] = useState(false); // State thu gọn

  useEffect(() => {
    const fetchBooks = async () => {
      setLoading(true);
      const results = await api.searchBooks(debouncedSearchTerm);
      setBooks(results);
      setLoading(false);
    };
    fetchBooks();
  }, [debouncedSearchTerm]);

  const handleAddBook = async (bookData) => {
    await api.addBook(bookData);
    alert('Thêm sách thành công!');
    setSearchTerm(''); // Reset search để tải lại toàn bộ danh sách
    if (debouncedSearchTerm === '') { // Nếu đang không search thì trigger useEffect
      const results = await api.searchBooks('');
      setBooks(results);
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
        <button
          onClick={() => setIsCollapsed(!isCollapsed)}
          className="btn mb-4"
        >
          {isCollapsed ? 'Mở rộng danh sách' : 'Thu gọn danh sách'}
        </button>
        <div className={`collapse-wrapper ${isCollapsed ? 'collapsed' : ''}`}>
          <div className="p-2">
            <BookList books={books} loading={loading} />
          </div>
        </div>
      </div>
      <div className="card">
        <h3>Thêm sách mới</h3>
        <BookForm onSubmit={handleAddBook} />
      </div>
    </div>
  );
}

export default BooksPage;