import React from 'react';
import Table from '../../../components/common/Table';
const bookColumns = [
  { key: 'bookId', header: 'ID' },
  { key: 'bookName', header: 'Tên sách' },
  { key: 'bookQuantity', header: 'Số lượng' },
];

function BookList({ books, loading }) {
  return <Table columns={bookColumns} data={books} loading={loading} />;

}

export default BookList;