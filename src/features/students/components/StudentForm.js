import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';

function StudentForm({ onSubmit }) {
  // 1. Cập nhật trạng thái ban đầu để bao gồm age và email
  const initialData = { name: '', studentId: '', age: '', email: '' };
  const [student, setStudent] = useState(initialData);

  // Hàm handleChange không cần thay đổi, nó hoạt động với mọi trường nhập liệu
  const handleChange = (e) => setStudent({ ...student, [e.target.name]: e.target.value });

  const handleSubmit = (e) => {
    e.preventDefault();
    // Khi submit, đối tượng student giờ đã có cả age và email
    onSubmit(student);
    setStudent(initialData); // Reset tất cả các trường
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <Input 
          name="name" 
          value={student.name} 
          onChange={handleChange} 
          placeholder="Họ và tên" 
          required 
        />
        <Input
          name ="studentId"
          value={student.studentId}
          onChange={handleChange}
          placeholder="Mã sinh viên"
          required
        />

        {/* 2. Thêm trường nhập liệu cho Tuổi */}
        <Input 
          type="number" 
          name="age" 
          value={student.age} 
          onChange={handleChange} 
          placeholder="Tuổi"
          required 
        />
        {/* 3. Thêm trường nhập liệu cho Email */}
        <Input 
          type="email" 
          name="email" 
          value={student.email} 
          onChange={handleChange} 
          placeholder="Email" 
          required
        />
      </div>
      <Button type="submit">Thêm sinh viên</Button>
    </form>
  );
}

export default StudentForm;
