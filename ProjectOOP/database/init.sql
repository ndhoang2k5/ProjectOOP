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
