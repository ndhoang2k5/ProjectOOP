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
    BookID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(255) NOT NULL,
    YearPublished YEAR,
    Quantity INT DEFAULT 1,
    Description TEXT
);

-- Tạo bảng BookAuthors
CREATE TABLE BookAuthors (
    BookID INT,
    AuthorID INT,
    PRIMARY KEY (BookID, AuthorID)
);

-- Tạo bảng Members
CREATE TABLE Members (
    MemberID INT PRIMARY KEY AUTO_INCREMENT,
    FullName VARCHAR(100) NOT NULL,
    DateOfBirth DATE,
    Address VARCHAR(255),
    Phone VARCHAR(20),
    Email VARCHAR(100)
);


-- Tạo bảng BorrowRecords (không dùng CURRENT_DATE)
CREATE TABLE BorrowRecords (
    RecordID INT PRIMARY KEY AUTO_INCREMENT,
    MemberID INT,
    BookID INT,
    BorrowDate DATE,
    DueDate DATE,
    ReturnDate DATE,
    LibrarianID INT
);

-- Khóa ngoại cho BookAuthors
ALTER TABLE BookAuthors
    ADD CONSTRAINT FK_BookAuthors_Book FOREIGN KEY (BookID) REFERENCES Books(BookID),
    ADD CONSTRAINT FK_BookAuthors_Author FOREIGN KEY (AuthorID) REFERENCES Authors(AuthorID);

-- Khóa ngoại cho BorrowRecords
ALTER TABLE BorrowRecords
    ADD CONSTRAINT FK_BorrowRecords_Member FOREIGN KEY (MemberID) REFERENCES Members(MemberID),
    ADD CONSTRAINT FK_BorrowRecords_Book FOREIGN KEY (BookID) REFERENCES Books(BookID),
