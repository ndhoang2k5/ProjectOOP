// File: src/features/books/components/BookForm.js
import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';

function BookForm({ onSubmit }) {
  // THÊM bookId vào state ban đầu
  const initialData = { bookId: '', bookName: '', author: '', bookQuantity: 0 };
  const [book, setBook] = useState(initialData);

  const handleChange = (e) => setBook({ ...book, [e.target.name]: e.target.value });


  const handleSubmit = (e) => {
    e.preventDefault();


    // Chuyển đổi cả bookId và bookQuantity thành số nguyên trước khi gửi đi
    onSubmit({
      ...book,
      bookId: parseInt(book.bookId, 10),
      bookQuantity: parseInt(book.bookQuantity, 10)
    });
    setBook(initialData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        {/* THÊM MỚI: Input cho Book ID */}
        <Input
          name="bookId"
          type="number" // Đảm bảo người dùng nhập số
          value={book.bookId}
          onChange={handleChange}
          placeholder="Mã sách (ID)"
          required
        />
        <Input name="bookName" value={book.bookName} onChange={handleChange} placeholder="Tên sách" required />
      </div>
      <div className="form-group">
        <Input name="author" value={book.author} onChange={handleChange} placeholder="Tác giả" required />
        <Input name="bookQuantity" type="number" value={book.bookQuantity} onChange={handleChange} placeholder="Số lượng" required />
      </div>
      <Button type="submit">Lưu sách</Button>
    </form>
  );
}
export default BookForm;