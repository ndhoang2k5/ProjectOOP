CREATE DATABASE lib;
USE lib;

-- 1. Books (thêm cột author trực tiếp)
CREATE TABLE Books (
    bookID INT PRIMARY KEY AUTO_INCREMENT,
    bookName VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL,          -- thêm cột tác giả
    bookQuantity INT NOT NULL CHECK (bookQuantity >= 0)
);

-- 2. Students (bỏ AUTO_INCREMENT ở studentID)
CREATE TABLE Students (
    studentID INT PRIMARY KEY,             -- không auto increment nữa
    studentName VARCHAR(100) NOT NULL,
    studentAge INT,
    studentEmail VARCHAR(100) UNIQUE
);

-- 3. BorrowRecords
CREATE TABLE BorrowRecords (
    recordId INT PRIMARY KEY AUTO_INCREMENT,
    studentId INT NOT NULL,
    bookId INT NOT NULL,
    borrowDate DATE NOT NULL,
    returnDate DATE,
    
    -- Khóa ngoại
    CONSTRAINT FK_BorrowRecords_Student FOREIGN KEY (studentID) REFERENCES Students(studentID),
    CONSTRAINT FK_BorrowRecords_Book FOREIGN KEY (bookID) REFERENCES Books(bookID)
);

-- =========================
-- 1. Insert Books
-- =========================
INSERT INTO Books (bookName, author, bookQuantity) VALUES
('Clean Code', 'Robert C. Martin', 10),
('Design Patterns', 'Erich Gamma', 7),
('Refactoring', 'Martin Fowler', 5),
('Java Concurrency in Practice', 'Brian Goetz', 8),
('Effective Java', 'Joshua Bloch', 12),
('The Pragmatic Programmer', 'Andrew Hunt', 9),
('Introduction to Algorithms', 'Thomas H. Cormen', 15),
('Database System Concepts', 'Abraham Silberschatz', 6),
('Head First Design Patterns', 'Eric Freeman', 11),
('Spring in Action', 'Craig Walls', 7),
('Algorithms', 'Robert Sedgewick', 8),
('Deep Learning', 'Ian Goodfellow', 10),
('Artificial Intelligence', 'Stuart Russell', 9),
('Operating System Concepts', 'Abraham Silberschatz', 10),
('Computer Networking', 'Andrew Tanenbaum', 12),
('Python Crash Course', 'Eric Matthes', 14),
('Eloquent JavaScript', 'Marijn Haverbeke', 10),
('Learning SQL', 'Alan Beaulieu', 13),
('C Programming Language', 'Brian Kernighan', 6),
('Code Complete', 'Steve McConnell', 9);

-- =========================
-- 2. Insert Students (30 students, ID nhập thủ công)
-- =========================
INSERT INTO Students (studentID, studentName, studentAge, studentEmail) VALUES
(1, 'Nguyen Van A', 20, 'a1@example.com'),
(2, 'Tran Van B', 21, 'b2@example.com'),
(3, 'Le Thi C', 19, 'c3@example.com'),
(4, 'Pham Van D', 22, 'd4@example.com'),
(5, 'Hoang Thi E', 20, 'e5@example.com'),
(6, 'Nguyen Van F', 23, 'f6@example.com'),
(7, 'Tran Van G', 24, 'g7@example.com'),
(8, 'Le Thi H', 21, 'h8@example.com'),
(9, 'Pham Van I', 20, 'i9@example.com'),
(10, 'Hoang Thi J', 22, 'j10@example.com'),
(11, 'Nguyen Van K', 21, 'k11@example.com'),
(12, 'Tran Van L', 19, 'l12@example.com'),
(13, 'Le Thi M', 23, 'm13@example.com'),
(14, 'Pham Van N', 20, 'n14@example.com'),
(15, 'Hoang Thi O', 21, 'o15@example.com'),
(16, 'Nguyen Van P', 24, 'p16@example.com'),
(17, 'Tran Van Q', 22, 'q17@example.com'),
(18, 'Le Thi R', 20, 'r18@example.com'),
(19, 'Pham Van S', 21, 's19@example.com'),
(20, 'Hoang Thi T', 19, 't20@example.com'),
(21, 'Nguyen Van U', 22, 'u21@example.com'),
(22, 'Tran Van V', 20, 'v22@example.com'),
(23, 'Le Thi W', 23, 'w23@example.com'),
(24, 'Pham Van X', 21, 'x24@example.com'),
(25, 'Hoang Thi Y', 22, 'y25@example.com'),
(26, 'Nguyen Van Z', 19, 'z26@example.com'),
(27, 'Tran Van AA', 20, 'aa27@example.com'),
(28, 'Le Thi BB', 21, 'bb28@example.com'),
(29, 'Pham Van CC', 23, 'cc29@example.com'),
(30, 'Hoang Thi DD', 22, 'dd30@example.com');

-- =========================
-- 3. Insert BorrowRecords (50 records)
-- =========================
INSERT INTO BorrowRecords (studentId, bookId, borrowDate, returnDate) VALUES
(1, 1, '2025-01-10', '2025-01-20'),
(2, 2, '2025-01-11', '2025-01-21'),
(3, 3, '2025-01-12', NULL),
(4, 4, '2025-01-13', '2025-01-25'),
(5, 5, '2025-01-14', NULL),
(6, 6, '2025-01-15', '2025-01-28'),
(7, 7, '2025-01-16', NULL),
(8, 8, '2025-01-17', '2025-01-29'),
(9, 9, '2025-01-18', NULL),
(10, 10, '2025-01-19', '2025-01-30'),
(11, 11, '2025-02-01', NULL),
(12, 12, '2025-02-02', '2025-02-15'),
(13, 13, '2025-02-03', NULL),
(14, 14, '2025-02-04', '2025-02-18'),
(15, 15, '2025-02-05', NULL),
(16, 16, '2025-02-06', '2025-02-19'),
(17, 17, '2025-02-07', NULL),
(18, 18, '2025-02-08', '2025-02-20'),
(19, 19, '2025-02-09', NULL),
(20, 20, '2025-02-10', '2025-02-21'),
(21, 1, '2025-02-11', NULL),
(22, 2, '2025-02-12', '2025-02-22'),
(23, 3, '2025-02-13', NULL),
(24, 4, '2025-02-14', '2025-02-23'),
(25, 5, '2025-02-15', NULL),
(26, 6, '2025-02-16', '2025-02-24'),
(27, 7, '2025-02-17', NULL),
(28, 8, '2025-02-18', '2025-02-25'),
(29, 9, '2025-02-19', NULL),
(30, 10, '2025-02-20', '2025-02-26'),
(1, 11, '2025-02-21', NULL),
(2, 12, '2025-02-22', '2025-02-27'),
(3, 13, '2025-02-23', NULL),
(4, 14, '2025-02-24', '2025-02-28'),
(5, 15, '2025-02-25', NULL),
(6, 16, '2025-02-26', '2025-03-01'),
(7, 17, '2025-02-27', NULL),
(8, 18, '2025-02-28', '2025-03-02'),
(9, 19, '2025-03-01', NULL),
(10, 20, '2025-03-02', '2025-03-05'),
(11, 1, '2025-03-03', NULL),
(12, 2, '2025-03-04', '2025-03-06'),
(13, 3, '2025-03-05', NULL),
(14, 4, '2025-03-06', '2025-03-07'),
(15, 5, '2025-03-07', NULL),
(16, 6, '2025-03-08', '2025-03-09'),
(17, 7, '2025-03-09', NULL),
(18, 8, '2025-03-10', '2025-03-11');
