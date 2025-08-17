import React, { useState } from 'react';
import StudentStatusCard from './components/StudentStatusCard';
import StudentForm from './components/StudentForm';
import StudentSearch from './components/StudentSearch';
import * as api from '../../service/mockApi';

function StudentsPage() {
  const [status, setStatus] = useState(null);
  const [error, setError] = useState('');
  
  const handleSearch = async (studentId) => {
    if (!studentId) return;
    setError(''); setStatus(null);
    try { setStatus(await api.getStudentStatus(studentId)); } 
    catch (err) { setError(err.message); }
  };

  const handleAddStudent = async (studentData) => {
    try {
      await api.addStudent(studentData);
      alert(`Thêm sinh viên ${studentData.name} thành công!`);
    } catch(err) { alert(`Lỗi: ${err.message}`); }
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
      </div>
      <div className="card">
        <h3>Chỉnh sửa thông tin sinh viên</h3>
      </div>
    </div>
  );
}
export default StudentsPage;