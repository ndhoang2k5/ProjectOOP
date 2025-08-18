import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';

function FindBorrowForm({ onSearch }) {
    const [recordId, setRecordId] = useState('');
    const handleSubmit = (e) => {
        e.preventDefault();
        onSearch(parseInt(recordId));
    };
    return (
        <form onSubmit={handleSubmit}>
            <div className="form-group">
                <Input type="number" value={recordId} onChange={e => setRecordId(e.target.value)} placeholder="Nhập ID phiếu mượn..." required/>
                <Button type="submit">Tìm</Button>
            </div>
        </form>
    );
}
export default FindBorrowForm;