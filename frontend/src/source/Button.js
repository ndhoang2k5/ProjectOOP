import React from 'react';

function Button({ children, onClick, type = 'button', variant = 'primary' }) {
  return (
    <button type={type} className={`btn btn-${variant}`} onClick={onClick}>
      {children}
    </button>
  );
}
export default Button;