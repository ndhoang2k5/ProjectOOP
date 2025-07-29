import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';

function StudentSearch({ onSearch }) {
  const [studentId, setStudentId] = useState('');
  const handleSubmit = (e) => { e.preventDefault(); onSearch(studentId); };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <Input placeholder="Nhập mã sinh viên (VD: SV001)" value={studentId} onChange={(e) => setStudentId(e.target.value)} />
        <Button type="submit">Tìm</Button>
      </div>
    </form>
  );
}
export default StudentSearch;