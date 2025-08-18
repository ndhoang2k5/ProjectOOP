import React from 'react';
import { NavLink } from 'react-router-dom';
import logo from '../../assets/images/logo.jpg'; // Đường dẫn đến ảnh, thay đổi theo cấu trúc thư mục của bạn

function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <img src={logo} alt="Library Logo" className="logo-img" />
        <h1>Quản lý thư viện</h1>
      </div>
      <nav>
        <ul>
          <li><NavLink to="/books">Quản lý Sách</NavLink></li>
          <li><NavLink to="/students">Quản lý Sinh viên</NavLink></li>
          <li><NavLink to="/borrowing">Mượn/Trả sách</NavLink></li>
        </ul>
      </nav>
    </aside>
  );
}

export default Sidebar;