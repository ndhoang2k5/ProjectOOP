import React from 'react';
import Table from '../../../components/common/Table';

function BookList({ books, loading }) {
  const bookColumns = [
    { key: 'bookId', header: 'ID' },
    { key: 'bookName', header: 'Tên sách' },
    { key: 'author', header: 'Tác giả' },
    { key: 'bookQuantity', header: 'Số lượng' },
  ];

  return <Table columns={bookColumns} data={books} loading={loading} />;
}

export default BookList;