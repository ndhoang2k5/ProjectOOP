import React from 'react';

function Input({ type = 'text', value, onChange, placeholder, name, className = 'form-input' }) {
  return (
    <input
      type={type}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      name={name}
      className={className}
    />
  );
}
export default Input;