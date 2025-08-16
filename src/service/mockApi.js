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
      alert(`Không tìm thấy sinh viên với mã: ${studentId}`);
      return null;
    }
    handleError(error, `tìm sinh viên mã ${studentId}`);
  }
};

/**
 * [GIỮ NGUYÊN TÊN HÀM]
 */
export const addStudent = async (studentData) => {
  try {
    const payload = {
      studentName: studentData.name,
      studentAge: studentData.age || 0,
      studentEmail: studentData.email || null
    };
    const response = await axios.post(`${API_BASE_URL}/students`, payload);
    alert("Thêm sinh viên thành công!");
    return response.data;
  } catch (error) {
    handleError(error, "thêm sinh viên");
  }
};


// ===================================================================
// --- API CHO MƯỢN/TRẢ SÁCH ---
// ===================================================================

/**
 * [THÊM LẠI HÀM BỊ THIẾU]
 * Thêm lại hàm này để không bị lỗi biên dịch.
 * Logic bên trong chỉ hiện thông báo vì backend chưa hỗ trợ.
 */
export const createBorrowing = async () => {
  const message = `Chức năng "Tạo phiếu mượn" chưa được cài đặt ở backend.`;
  console.error(message);
  alert(message);
};

/**
 * [THÊM LẠI HÀM BỊ THIẾU]
 * Thêm lại hàm này để không bị lỗi biên dịch.
 */
export const endBorrowing = async () => {
  const message = `Chức năng "Trả sách" chưa được cài đặt ở backend.`;
  console.error(message);
  alert(message);
};
