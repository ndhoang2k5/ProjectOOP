import React from 'react';
import Input from '../../../components/common/Input';

function BookSearch({ searchTerm, onSearchChange }) {
  return (
    <div className="form-group">
      <Input
        type="search"
        placeholder="Nhập tiêu đề hoặc tác giả để tìm..."
        value={searchTerm}
        onChange={(e) => onSearchChange(e.target.value)}
      />
    </div>
  );
}
export default BookSearch;