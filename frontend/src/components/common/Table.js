import React from 'react';

function Table({ columns, data, loading }) {
  if (loading) return <p>Đang tải dữ liệu...</p>;


  if (!data || data.length === 0) return <p>Trong kho không có sách này.</p>;

  return (
    <table>
      <thead>
        <tr>
          {columns.map((col) => <th key={col.key}>{col.header}</th>)}
        </tr>
      </thead>
      <tbody>
        {data.map((row, rowIndex) => (
          <tr key={rowIndex}>
            {columns.map((col) => <td key={col.key}>{row[col.key]}</td>)}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

// Chỉ có một export default duy nhất trong file này
export default Table;