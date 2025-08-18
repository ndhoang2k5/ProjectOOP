import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';
import * as api from '../../../service/mockApi';

// SỬA LẠI: Thêm prop onUpdateSuccess
function StudentEditForm({ onUpdateSuccess }) {
  const [searchId, setSearchId] = useState('');
  const [studentToEdit, setStudentToEdit] = useState(null);
  const [formData, setFormData] = useState({ name: '', age: '', email: '' });
  const [message, setMessage] = useState('');

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchId) return;
    setMessage('');
    setStudentToEdit(null);
    try {
      const result = await api.getStudentById(searchId);
      if (result && result.student) {
        const foundStudent = result.student;
        setStudentToEdit(foundStudent);
        setFormData({
          name: foundStudent.studentName,
          age: foundStudent.studentAge,
          email: foundStudent.studentEmail,
        });
      } else {
        setMessage(`Không tìm thấy sinh viên với mã: ${searchId}`);
      }
    } catch (err) {
       // SỬA LẠI: Hiển thị lỗi cụ thể hơn từ backend
      const errorMessage = err.response?.data?.error || err.message;
      setMessage(`Lỗi khi tìm kiếm: ${errorMessage}`);
    }
  };

  const handleFormChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    if (!studentToEdit) return;
    try {
      await api.updateStudent(studentToEdit.studentId, formData);
      setMessage('Cập nhật thông tin sinh viên thành công!');
      
      // THÊM MỚI: Gọi hàm callback của component cha để báo hiệu cập nhật thành công
      // và truyền ID của sinh viên vừa được cập nhật.
      if (onUpdateSuccess) {
        onUpdateSuccess(studentToEdit.studentId);
      }

      // Reset form sau một khoảng trễ để người dùng đọc được thông báo
      setTimeout(() => {
        setStudentToEdit(null);
        setSearchId('');
        setMessage('');
      }, 2000);

    } catch (err) {
      const errorMessage = err.response?.data?.error || err.message;
      setMessage(`Lỗi khi cập nhật: ${errorMessage}`);
    }
  };

  // ... (phần return JSX giữ nguyên y hệt) ...
  return (
    <div>
      {/* --- FORM TÌM KIẾM SINH VIÊN ĐỂ SỬA --- */}
      <form onSubmit={handleSearch}>
        <div className="form-group">
          <Input
            placeholder="Nhập mã sinh viên cần sửa"
            value={searchId}
            onChange={(e) => setSearchId(e.target.value)}
          />
          <Button type="submit">Tìm để sửa</Button>
        </div>
      </form>

      {/* Hiển thị thông báo nếu có */}
      {message && <p style={{ marginTop: '10px' }}>{message}</p>}

      {/* --- FORM SỬA THÔNG TIN (chỉ hiện khi đã tìm thấy sinh viên) --- */}
      {studentToEdit && (
        <form onSubmit={handleUpdate} style={{ marginTop: '20px', borderTop: '1px solid #eee', paddingTop: '20px' }}>
          <h4>
            Đang sửa thông tin cho: <strong>{studentToEdit.studentName}</strong> (Mã SV: {studentToEdit.studentId})
          </h4>
          <div className="form-group">
            <Input
              name="name"
              value={formData.name}
              onChange={handleFormChange}
              placeholder="Họ và tên"
              required
            />
            <Input
              type="number"
              name="age"
              value={formData.age}
              onChange={handleFormChange}
              placeholder="Tuổi"
              required
            />
            <Input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleFormChange}
              placeholder="Email"
              required
            />
          </div>
          <Button type="submit">Lưu thay đổi</Button>
        </form>
      )}
    </div>
  );
}

export default StudentEditForm;