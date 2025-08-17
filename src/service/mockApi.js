// File: ../../service/mockApi.js

import axios from 'axios';

const API_BASE_URL = 'http://localhost:7000';

// Hàm xử lý lỗi chung
const handleError = (error, featureName = "không xác định") => {
  console.error(`Lỗi API khi thực hiện chức năng "${featureName}":`, error);
  if (error.response) {
    alert(`Lỗi từ server (${error.response.status}): ${error.response.data}`);
  } else if (error.request) {
    alert('Lỗi: Không thể kết nối tới server. Vui lòng kiểm tra lại backend có đang chạy không.');
  } else {
    alert(`Lỗi không xác định khi ${featureName}.`);
  }
  throw error;
};


// ===================================================================
// --- API CHO SÁCH ---
// ===================================================================

/**
 * [GIỮ NGUYÊN TÊN HÀM]
 * Tên hàm là `searchBooks` nhưng logic bên trong sẽ gọi API lấy TẤT CẢ sách,
 * vì backend chưa hỗ trợ tìm kiếm.
 */
export const searchBooks = async (query) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/books`);
    return response.data;
  } catch (error) {
    if (error.response && error.response.status === 404) return [];
    handleError(error, "tìm kiếm sách");
  }
};


/**
 * [GIỮ NGUYÊN TÊN HÀM]
 */
export const addBook = async (bookData) => {
  try {
    // Gửi yêu cầu POST đến `/api/books` (hoặc `/books` nếu bạn không dùng /api)
    // Toàn bộ thông tin sách nằm trong body của request dưới dạng JSON
    const response = await axios.post(`${API_BASE_URL}/books`, bookData);
    return response.data;
  } catch (error) {
    handleError(error, "thêm sách");
  }
};

/**
 * Hàm này sẽ xóa một cuốn sách theo ID.
 * @param {*} bookId 
 * @returns 
 */
export const deleteBook = async (bookId) => {
  try {
    const response = await axios.delete(`${API_BASE_URL}/books/${bookId}`);
    return response.data;
  } catch (error) {
    handleError(error, "xóa sách");
  }
};


// ===================================================================
// --- API CHO SINH VIÊN ---
// ===================================================================

/**
 * [GIỮ NGUYÊN TÊN HÀM]
 * Tên hàm là `getStudentStatus` nhưng logic bên trong đã được sửa lại
 * để gọi thẳng đến API getStudentById của backend.
 */
export const getStudentStatus = async (studentId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/students/${studentId}`);
    return {
      student: response.data,
      activeBorrows: [] // Tạm thời vì chưa có API mượn sách
    };
  } catch (error) {
    if (error.response && error.response.status === 404) {
      // alert() đã bị loại bỏ, component sẽ tự xử lý thông báo
      console.warn(`Không tìm thấy sinh viên với mã: ${studentId}`);
      return null;
    }
    handleError(error, `tìm sinh viên mã ${studentId}`);
  }
};

/**
 * Thêm một sinh viên mới.
 * (ĐÂY LÀ PHIÊN BẢN ĐÚNG, CHỈ CÓ MỘT)
 */
export const addStudent = async (studentData) => {
  try {
    const payload = {
      studentId: parseInt(studentData.studentId, 10) || 0,
      studentName: studentData.name,
      studentAge: parseInt(studentData.age, 10) || 0,
      studentEmail: studentData.email || null
    };
    const response = await axios.post(`${API_BASE_URL}/students`, payload);
    return response.data; // Không có alert ở đây
  } catch (error) {
    handleError(error, "thêm sinh viên");
  }
};

/**
 * Cập nhật thông tin của một sinh viên dựa trên ID.
 */
export const updateStudent = async (studentId, studentData) => {
  try {
    const payload = {
      studentName: studentData.name,
      studentAge: parseInt(studentData.age, 10),
      studentEmail: studentData.email
    };
    const response = await axios.put(`${API_BASE_URL}/students/${studentId}`, payload);
    return response.data; // Không có alert ở đây
  } catch (error) {
    handleError(error, `cập nhật sinh viên mã ${studentId}`);
  }
};


// ===================================================================
// --- API CHO MƯỢN/TRẢ SÁCH ---
// ===================================================================

// ... (phần đầu file giữ nguyên) ...

// ===================================================================
// --- API CHO MƯỢN/TRẢ SÁCH ---
// ===================================================================

export const createBorrowing = async (studentId, bookId) => {
  try {
    const payload = {
      studentId: parseInt(studentId, 10),
      bookId: parseInt(bookId, 10),
    };
    // URL chuẩn: POST /borrows
    const response = await axios.post(`${API_BASE_URL}/borrows`, payload);
    return response.data;
  } catch (error) {
    handleError(error, "tạo phiếu mượn");
  }
};

export const returnBook = async (borrowingId) => {
  try {
    // URL chuẩn: PUT /borrows/{id}/return
    const response = await axios.put(`${API_BASE_URL}/borrows/${borrowingId}/return`);
    return response.data;
  } catch (error) {
    handleError(error, "trả sách");
  }
};