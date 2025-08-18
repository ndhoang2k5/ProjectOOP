// src/features/students/components/StudentEditForm.js
import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';
// GIẢ SỬ: File gọi API thực tế của bạn là 'apiService.js' chứ không phải mockApi
//import * as api from '../../../service/apiService';
import * as api from '../../../service/mockApi';

// CẢI TIẾN: Thêm prop 'onUpdateSuccess' để thông báo cho component cha khi cập nhật thành công
function StudentEditForm({ onUpdateSuccess }) {
  const [searchId, setSearchId] = useState('');
  const [studentToEdit, setStudentToEdit] = useState(null); // Lưu thông tin SV tìm được
  const [formData, setFormData] = useState({ name: '', age: '', email: '' }); // Lưu dữ liệu đang sửa trên form
  const [message, setMessage] = useState(''); // Hiển thị thông báo (lỗi, thành công)

  // Hàm xử lý khi người dùng nhấn nút "Tìm để sửa"
  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchId) return;

    setMessage('');
    setStudentToEdit(null);

    try {
      // SỬA LỖI 1: Gọi hàm API và nhận trực tiếp đối tượng student
      // Backend trả về: { studentId: 1, studentName: 'A' }
      // Chứ không phải: { student: { studentId: 1, ... } }
      const foundStudent = await api.getStudentById(searchId);
      
      if (foundStudent) {
        setStudentToEdit(foundStudent);
        // Điền thông tin tìm được vào form
        setFormData({
          name: foundStudent.studentName,
          age: foundStudent.studentAge,
          email: foundStudent.studentEmail,
        });
      }
      // Không cần else ở đây, vì nếu không tìm thấy, API sẽ ném lỗi 404 và nhảy vào catch
    } catch (err) {
      // SỬA LỖI 3: Cải thiện xử lý lỗi
      if (err.response && err.response.status === 404) {
        setMessage(`Không tìm thấy sinh viên với mã: ${searchId}`);
      } else {
        const errorMessage = err.response?.data?.error || err.message;
        setMessage(`Đã xảy ra lỗi khi tìm kiếm: ${errorMessage}`);
      }
    }
  };

  // Cập nhật state của form khi người dùng nhập liệu
  const handleFormChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Hàm xử lý khi người dùng nhấn "Lưu thay đổi"
  const handleUpdate = async (e) => {
    e.preventDefault();
    if (!studentToEdit) return;

    // SỬA LỖI 2: Tạo đối tượng payload với các key khớp với backend
    const studentDataToUpdate = {
      studentName: formData.name,
      studentAge: parseInt(formData.age, 10), // Đảm bảo tuổi là kiểu số
      studentEmail: formData.email,
    };

    try {
      await api.updateStudent(studentToEdit.studentId, studentDataToUpdate);
      setMessage('Cập nhật thông tin sinh viên thành công!');
      
      // CẢI TIẾN: Gọi hàm callback của component cha (nếu có)
      if (onUpdateSuccess) {
        onUpdateSuccess(studentToEdit.studentId);
      }

      // CẢI TIẾN 4: Reset form sau một khoảng trễ để người dùng đọc được thông báo
      setTimeout(() => {
        setStudentToEdit(null);
        setSearchId('');
        setMessage('');
      }, 2000); // 2 giây

    } catch (err) {
      const errorMessage = err.response?.data?.error || err.message;
      setMessage(`Lỗi khi cập nhật: ${errorMessage}`);
    }
  };

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