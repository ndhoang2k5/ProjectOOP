import React from 'react';
import { NavLink } from 'react-router-dom';

function Sidebar() {
  return (
    <aside className="sidebar">
      <h1>Quản lý thư viện</h1>
      <nav>
        <ul>
          <li><NavLink to="/books">Quản lý Sách</NavLink></li>
          <li><NavLink to="/students">Quản lý Sinh viên</NavLink></li>
          <li><NavLink to="/borrowing">Mượn/Trả sách</NavLink></li>
          <li><NavLink to="/books">Kiểm tra Sách</NavLink></li>
        </ul>
      </nav>
    </aside>
  );
}

// Chỉ có một export default duy nhất trong file này
export default Sidebar;