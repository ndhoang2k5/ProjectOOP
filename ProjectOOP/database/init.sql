create database if not exists library;
use library;



-- Tạo bảng Authors
CREATE TABLE Authors (
    AuthorID INT PRIMARY KEY AUTO_INCREMENT,
    AuthorName VARCHAR(100) NOT NULL,
    Biography TEXT
);


-- Tạo bảng Books
CREATE TABLE Books (
    bookID INT PRIMARY KEY AUTO_INCREMENT,
    bookName VARCHAR(255) NOT NULL,
    bookQuantity INT NOT NULL
);

-- Tạo bảng BookAuthors
CREATE TABLE BookAuthors (
    bookID int not null primary key,
    bookName varchar(255) not null,
    authorID int not null
);

-- Tạo bảng Members
CREATE TABLE student (
    studentID INT PRIMARY KEY AUTO_INCREMENT,
    studentName VARCHAR(100) NOT NULL,
    studentAge INT,
    studentEmail VARCHAR(100) UNIQUE
);


-- Tạo bảng BorrowRecords (không dùng CURRENT_DATE)
CREATE TABLE BorrowRecords (
    recordId INT PRIMARY KEY AUTO_INCREMENT,
    studentId INT NOT NULL,
    bookId INT NOT NULL,
    borrowDate DATE NOT NULL,
    returnDate DATE
);

-- Khóa ngoại cho BookAuthors
ALTER TABLE BookAuthors
    ADD CONSTRAINT FK_BookAuthors_Book FOREIGN KEY (BookID) REFERENCES Books(BookID),
    ADD CONSTRAINT FK_BookAuthors_Author FOREIGN KEY (AuthorID) REFERENCES Authors(AuthorID);

-- Khóa ngoại cho BorrowRecords
ALTER TABLE BorrowRecords
    ADD CONSTRAINT FK_BorrowRecords_Member FOREIGN KEY (studentID) REFERENCES student(studentID),
    ADD CONSTRAINT FK_BorrowRecords_Book FOREIGN KEY (BookID) REFERENCES Books(BookID)
