import React from 'react';
import Table from '../../../components/common/Table';
import Button from '../../../components/common/Button';
function BookList({ books, loading, onDelete }) {

  const bookColumns = [
    { key: 'bookId', header: 'ID' },
    { key: 'bookName', header: 'Tên sách' },
    { key: 'author', header: 'Tác giả' },
    { key: 'bookQuantity', header: 'Số lượng' },

    {
      const: 'actions',
      header: 'Hành động',
      render: (book) => (
        <Button onClick={() => onDelete(book.bookId)} className="danger">
          xóa
        </Button>
      )
    }
  ];


  return <Table columns={bookColumns} data={books} loading={loading} />;
}
export default BookList;