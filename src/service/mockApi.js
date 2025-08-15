import axios from 'axios';

// Backend của bạn không có tiền tố "/api"
const API_BASE_URL = 'http://localhost:7000';

const handleError = (error) => {
  console.error("Lỗi API:", error);
  if (error.response) {
    throw new Error(error.response.data.message || 'Lỗi từ server');
  }
  throw new Error('Không thể kết nối tới server.');
};

// ===================================================================
// --- API CHO SÁCH (Hoạt động với hạn chế) ---
// ===================================================================

/**
 * Lấy danh sách sách từ `GET /books`.
 * Hạn chế: Backend chưa hỗ trợ tìm kiếm, nên chức năng search sẽ không lọc.
 */
export const searchBooks = async (query) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/books`);
    return response.data;
  } catch (error) {
    if (error.response && error.response.status === 404) return [];
    handleError(error);
  }
};

/**
 * Thêm sách bằng cách gọi `POST /books/add/{id}/{name}/{qty}`.
 * Hạn chế: Sách thêm vào sẽ thiếu author, isbn và có ID tạm thời.
 */
export const addBook = async (bookData) => {
  const tempId = Date.now();
  const bookName = bookData.title;
  const bookQuantity = bookData.quantityInStock;
  const encodedBookName = encodeURIComponent(bookName);
  const url = `${API_BASE_URL}/books/add/${tempId}/${encodedBookName}/${bookQuantity}`;
  try {
    const response = await axios.post(url, {});
    return response.data;
  } catch (error) {
    handleError(error);
  }
};


// ===================================================================
// --- API CHO SINH VIÊN (Hoạt động một phần) ---
// ===================================================================

/**
 * Thêm sinh viên mới.
 * Hạn chế: Backend chỉ lưu `student_name` và `student_email`.
 * Mã sinh viên (`studentId`) mà người dùng nhập vào form sẽ bị MẤT.
 */
export const addStudent = async (studentData) => {
  try {
    // `studentData` từ form có: { name, studentId }
    // `StudentService.addStudent` lưu: `student_name`, `student_email`
    const payload = {
      studentName: studentData.name,
      studentEmail: null // Form không có email, nên ta gửi null
    };
    // Backend không đăng ký API này trong DatabaseConnector!
    // GIẢ SỬ bạn đã thêm: app.post("/students", StudentApi.addStudent);
    const response = await axios.post(`${API_BASE_URL}/students`, payload);
    return response.data;
  } catch (error) {
    // Nếu bạn chưa đăng ký API, lỗi sẽ xảy ra ở đây
    alert('Lỗi: API thêm sinh viên chưa được đăng ký trong DatabaseConnector.java');
    handleError(error);
  }
};

/**
 * Lấy trạng thái sinh viên.
 * Hạn chế LỚN: Backend không có API `getStudentStatus`.
 * GIẢI PHÁP THAY THẾ: Lấy TẤT CẢ sinh viên về rồi lọc ở frontend.
 * Cách này rất chậm và không thể lấy sách đang mượn.
 */
export const getStudentStatus = async (studentIdToFind) => {
  try {
    // GIẢ SỬ bạn đã thêm: app.get("/students", StudentApi.getAllStudents);
    const response = await axios.get(`${API_BASE_URL}/students`);
    const allStudents = response.data;

    // Tìm sinh viên trong danh sách trả về
    const foundStudent = allStudents.find(s => s.studentId === studentIdToFind);

    if (!foundStudent) {
      throw new Error('Không tìm thấy sinh viên');
    }

    // Vì không thể lấy sách đang mượn, ta trả về mảng rỗng
    return {
      student: foundStudent,
      activeBorrows: []
    };
  } catch (error) {
    alert('Lỗi: API lấy tất cả sinh viên chưa được đăng ký trong DatabaseConnector.java');
    handleError(error);
  }
};


// ===================================================================
// --- API CHO MƯỢN/TRẢ SÁCH (Hoàn toàn không hoạt động) ---
// ===================================================================

const notImplemented = (featureName) => {
  const message = `Chức năng "${featureName}" không thể hoạt động. ` +
                  `Backend chưa đăng ký API cho nó trong file DatabaseConnector.java.`;
  console.error(message);
  alert(message);
  throw new Error(message);
};

export const createBorrowing = async () => notImplemented("Tạo phiếu mượn");
export const endBorrowing = async () => notImplemented("Trả sách");