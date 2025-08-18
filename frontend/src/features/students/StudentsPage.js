// src/features/students/StudentsPage.js

import React, { useState } from 'react';
import StudentStatusCard from './components/StudentStatusCard';
import StudentForm from './components/StudentForm';
import StudentSearch from './components/StudentSearch';
import StudentEditForm from './components/StudentEditForm';
import * as api from '../../service/mockApi';

function StudentsPage() {
  const [status, setStatus] = useState(null);
  const [error, setError] = useState('');
  const [addMessage, setAddMessage] = useState(''); // Thêm state cho thông báo

  const handleSearch = async (studentId) => {
    if (!studentId) return;
    setError(''); 
    setStatus(null);
    try { 
      setStatus(await api.getStudentById(studentId)); 
    } catch (err) { 
      setError(err.message); 
    }
  };

  const handleAddStudent = async (studentData) => {
    setAddMessage(''); // Reset thông báo
    try {
      if (!studentData.studentId || !studentData.name || !studentData.age || !studentData.email) {
          alert('Vui lòng nhập đầy đủ thông tin: Mã SV, Tên, Tuổi và Email.');
          return;
      }
      const newStudent = await api.addStudent(studentData);
      setAddMessage(`Thêm sinh viên ${newStudent.studentName} thành công!`); // Hiển thị thông báo thành công
      setTimeout(() => setAddMessage(''), 3000); // Ẩn thông báo sau 3s
    } catch(err) { 
      const errorMessage = err.response?.data?.error || err.message;
      setAddMessage(`Lỗi: ${errorMessage}`); // Hiển thị thông báo lỗi
    }
  };

  // THÊM HÀM MỚI: Hàm này sẽ được gọi bởi StudentEditForm sau khi cập nhật thành công.
  // Nhiệm vụ của nó là gọi lại handleSearch để tải lại dữ liệu mới nhất.
  const handleEditSuccess = (updatedStudentId) => {
    // Kiểm tra xem sinh viên đang được hiển thị có phải là sinh viên vừa được sửa không
    if (status && status.student && status.student.studentId === updatedStudentId) {
      handleSearch(updatedStudentId);
    }
  };

  return (
    <div>
      <h2 className="page-header">Quản lý Sinh viên</h2>
      
      <div className="card">
        <h3>Tìm kiếm thông tin sinh viên</h3>
        <StudentSearch onSearch={handleSearch} />
        <hr style={{border: 'none', borderTop: '1px solid #eee', margin: '20px 0'}} />
        <StudentStatusCard status={status} error={error} />
      </div>
      
      <div className="card">
        <h3>Thêm sinh viên mới</h3>
        <StudentForm onSubmit={handleAddStudent} />
        {/* Hiển thị thông báo thêm sinh viên */}
        {addMessage && <p style={{marginTop: '10px'}}>{addMessage}</p>}
      </div>
      
      <div className="card">
        <h3>Chỉnh sửa thông tin sinh viên</h3>
        {/* SỬA LẠI: Truyền prop onUpdateSuccess vào component con */}
        <StudentEditForm onUpdateSuccess={handleEditSuccess} />
      </div>
    </div>
  );
}
export default StudentsPage;