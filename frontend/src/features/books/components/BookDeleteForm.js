import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';

function BookDeleteForm({ onDelete }) {
  const [bookIdToDelete, setBookIdToDelete] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!bookIdToDelete) {
      alert('Vui lòng nhập ID sách cần xóa.');
      return;
    }
    onDelete(parseInt(bookIdToDelete, 10));
    setBookIdToDelete('');
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <Input
          type="number"
          value={bookIdToDelete}
          onChange={(e) => setBookIdToDelete(e.target.value)}
          placeholder="Nhập ID sách cần xóa"
          required
        />
        <Button type="submit" variant="danger">
          Xác nhận Xóa
        </Button>
      </div>
    </form>
  );
}

export default BookDeleteForm;