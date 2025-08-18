import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';

function StudentForm({ onSubmit }) {
  const initialData = { name: '', studentId: '' };
  const [student, setStudent] = useState(initialData);

  const handleChange = (e) => setStudent({ ...student, [e.target.name]: e.target.value });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(student);
    setStudent(initialData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <Input name="name" value={student.name} onChange={handleChange} placeholder="Họ và tên" required />
        <Input name="studentId" value={student.studentId} onChange={handleChange} placeholder="Mã sinh viên" required />
      </div>
      <Button type="submit">Thêm sinh viên</Button>
    </form>
  );
}
export default StudentForm;