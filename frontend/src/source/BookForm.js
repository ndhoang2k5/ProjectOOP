import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';

function BookForm({ onSubmit }) {
  const initialData = { title: '', author: '', isbn: '', quantityInStock: 0 };
  const [book, setBook] = useState(initialData);

  const handleChange = (e) => setBook({ ...book, [e.target.name]: e.target.value });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ ...book, quantityInStock: parseInt(book.quantityInStock, 10) });
    setBook(initialData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <Input name="title" value={book.title} onChange={handleChange} placeholder="Tiêu đề" required />
        <Input name="author" value={book.author} onChange={handleChange} placeholder="Tác giả" required />
      </div>
      <div className="form-group">
        <Input name="isbn" value={book.isbn} onChange={handleChange} placeholder="ISBN" required />
        <Input name="quantityInStock" type="number" value={book.quantityInStock} onChange={handleChange} placeholder="Số lượng" required />
      </div>
      <Button type="submit">Lưu sách</Button>
    </form>
  );
}
export default BookForm;