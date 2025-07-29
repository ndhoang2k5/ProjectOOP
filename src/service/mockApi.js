// --- Dữ liệu giả lập (Mock Data) ---
let mockBooks = [
  { id: 1, title: 'Lập trình Java cơ bản', author: 'John Doe', isbn: '978-1', quantityInStock: 5 },
  { id: 2, title: 'Cấu trúc dữ liệu và giải thuật', author: 'Jane Smith', isbn: '978-2', quantityInStock: 3 },
  { id: 3, title: 'ReactJS cho người mới bắt đầu', author: 'Peter Pan', isbn: '978-3', quantityInStock: 0 },
];

let mockStudents = [
  { id: 1, studentId: 'SV001', name: 'Nguyễn Văn A' },
  { id: 2, studentId: 'SV002', name: 'Trần Thị B' },
];

let mockBorrowings = [];

let nextBookId = 4;
let nextStudentId = 3;
let nextBorrowingId = 1;

// Hàm chờ mô phỏng độ trễ mạng
const delay = (ms) => new Promise(res => setTimeout(res, ms));

// USECASE: In ra thông tin sách, tìm kiếm sách trong kho
export const searchBooks = async (query) => {
  await delay(300);
  if (!query) {
    return [...mockBooks];
  }
  const lowerCaseQuery = query.toLowerCase();
  return mockBooks.filter(
    book => book.title.toLowerCase().includes(lowerCaseQuery) || book.author.toLowerCase().includes(lowerCaseQuery)
  );
};

// USECASE: Thêm sách
export const addBook = async (bookData) => {
  await delay(300);
  const newBook = { ...bookData, id: nextBookId++ };
  mockBooks.push(newBook);
  return newBook;
};

// USECASE: Tìm kiếm thông tin sinh viên và tình trạng mượn sách
export const getStudentStatus = async (studentId) => {
  await delay(300);
  const student = mockStudents.find(s => s.studentId === studentId);
  if (!student) {
    throw new Error('Không tìm thấy sinh viên');
  }
  const activeBorrows = mockBorrowings
    .filter(b => b.studentId === student.id && b.returnDate === null)
    .map(b => {
      const book = mockBooks.find(bk => bk.id === b.bookId);
      return { ...b, bookTitle: book ? book.title : 'Không rõ' };
    });
  return { student, activeBorrows };
};

// USECASE: Thêm sinh viên
export const addStudent = async (studentData) => {
  await delay(300);
  if (mockStudents.some(s => s.studentId === studentData.studentId)) {
    throw new Error('Mã sinh viên đã tồn tại');
  }
  const newStudent = { ...studentData, id: nextStudentId++ };
  mockStudents.push(newStudent);
  return newStudent;
}

// USECASE: Tạo mượn sách
export const createBorrowing = async (studentId, bookId) => {
  await delay(500);
  const student = mockStudents.find(s => s.studentId === studentId);
  const book = mockBooks.find(b => b.id === bookId);

  if (!student) throw new Error('Không tìm thấy sinh viên.');
  if (!book) throw new Error('Không tìm thấy sách.');
  if (book.quantityInStock <= 0) throw new Error('Sách đã hết trong kho.');

  book.quantityInStock -= 1;
  const newBorrowing = {
    id: nextBorrowingId++,
    studentId: student.id,
    bookId: book.id,
    borrowDate: new Date().toISOString().slice(0, 10),
    returnDate: null
  };
  mockBorrowings.push(newBorrowing);
  return newBorrowing;
};

// USECASE: Kết thúc mượn sách
export const endBorrowing = async (borrowingId) => {
  await delay(500);
  const borrowing = mockBorrowings.find(b => b.id === borrowingId);
  if (!borrowing) throw new Error('Không tìm thấy lượt mượn.');
  if (borrowing.returnDate) throw new Error('Sách này đã được trả.');

  const book = mockBooks.find(b => b.id === borrowing.bookId);
  if (book) {
    book.quantityInStock += 1;
  }
  borrowing.returnDate = new Date().toISOString().slice(0, 10);
  return borrowing;
};