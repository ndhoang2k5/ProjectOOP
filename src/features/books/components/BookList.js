import React from 'react';
import Table from '../../../components/common/Table';

const bookColumns = [
  { key: 'id', header: 'ID' },
  { key: 'title', header: 'Tiêu đề' },
  { key: 'author', header: 'Tác giả' },
  { key: 'isbn', header: 'ISBN' },
  { key: 'quantityInStock', header: 'Trong kho' },
];

function BookList({ books, loading }) {
  return <Table columns={bookColumns} data={books} loading={loading} />;
}
export default BookList;