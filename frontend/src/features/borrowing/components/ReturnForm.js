import React, { useState } from 'react';
import Input from '../../../components/common/Input';
import Button from '../../../components/common/Button';

function ReturnForm({ onSubmit }) {
    const [returnId, setReturnId] = useState('');
    const handleSubmit = e => {
        e.preventDefault();
        onSubmit(parseInt(returnId));
        setReturnId('');
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="form-group">
                <Input type="number" value={returnId} onChange={e => setReturnId(e.target.value)} placeholder="Nhập ID lượt mượn để trả" required/>
            </div>
            <Button type="submit">Xác nhận trả sách</Button>
        </form>
    );
}
export default ReturnForm;