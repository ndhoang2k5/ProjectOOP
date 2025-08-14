CREATE DATABASE IF NOT EXISTS lib;
USE lib;

-- 1. Authors
CREATE TABLE Authors (
    AuthorID INT PRIMARY KEY AUTO_INCREMENT,
    AuthorName VARCHAR(100) NOT NULL,
    Biography TEXT
);

-- 2. Books
CREATE TABLE Books (
    bookID INT PRIMARY KEY AUTO_INCREMENT,
    bookName VARCHAR(255) NOT NULL,
    bookQuantity INT NOT NULL CHECK (bookQuantity >= 0)
);

-- 3. BookAuthors (bảng trung gian)
CREATE TABLE BookAuthors (
    bookID INT NOT NULL,
    authorID INT NOT NULL,
    PRIMARY KEY (bookID, authorID)
);

-- 4. Students
CREATE TABLE student (
    studentID INT PRIMARY KEY AUTO_INCREMENT,
    studentName VARCHAR(100) NOT NULL,
    studentAge INT,
    studentEmail VARCHAR(100) UNIQUE
);

-- 5. BorrowRecords
CREATE TABLE BorrowRecords (
    recordId INT PRIMARY KEY AUTO_INCREMENT,
    studentId INT NOT NULL,
    bookId INT NOT NULL,
    borrowDate DATE NOT NULL,
    returnDate DATE
);

-- =====================================
-- Thêm khóa ngoại (FOREIGN KEY) sau
-- =====================================

-- Liên kết BookAuthors -> Books & Authors
ALTER TABLE BookAuthors
    ADD CONSTRAINT FK_BookAuthors_Book
        FOREIGN KEY (bookID) REFERENCES Books(bookID),
    ADD CONSTRAINT FK_BookAuthors_Author
        FOREIGN KEY (authorID) REFERENCES Authors(AuthorID);

-- Liên kết BorrowRecords -> student & Books
ALTER TABLE BorrowRecords
    ADD CONSTRAINT FK_BorrowRecords_Student
        FOREIGN KEY (studentID) REFERENCES student(studentID),
    ADD CONSTRAINT FK_BorrowRecords_Book
        FOREIGN KEY (bookID) REFERENCES Books(bookID);
