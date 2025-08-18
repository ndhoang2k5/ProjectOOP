import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';

function BorrowingForm({ onSubmit }) {
    const [data, setData] = useState({ studentId: '', bookId: '' });
    const handleChange = e => setData({...data, [e.target.name]: e.target.value});
    const handleSubmit = e => {
        e.preventDefault();
        onSubmit(data.studentId, parseInt(data.bookId));
        setData({ studentId: '', bookId: '' });
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="form-group">
                <Input name="studentId" value={data.studentId} onChange={handleChange} placeholder="Mã sinh viên" required/>
                <Input name="bookId" type="number" value={data.bookId} onChange={handleChange} placeholder="ID Sách" required/>
            </div>
            <Button type="submit">Tạo phiếu mượn</Button>
        </form>
    );
}
export default BorrowingForm;