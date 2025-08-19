// src/main/java/backend/service/BorrowService.java
package backend.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import backend.driver.DatabaseConnector;
import backend.entities.Borrow;
import backend.entities.GetBorrow;

public class BorrowService {

    /**
     * SỬA LẠI HOÀN TOÀN:
     * - Dùng đúng tên bảng/cột.
     * - Trả về đối tượng Borrow.
     * - Xử lý đúng AUTO_INCREMENT.
     * - Thêm logic transaction an toàn.
     */
    public Borrow createBorrowRecord(Borrow borrow) {
        String insertSql = "INSERT INTO BorrowRecords (studentId, bookId, borrowDate) VALUES (?, ?, ?)";
        String updateSql = "UPDATE Books SET bookQuantity = bookQuantity - 1 WHERE bookID = ? AND bookQuantity > 0";
        Connection conn = null;

        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, borrow.getBookId());
                if (updateStmt.executeUpdate() == 0) {
                    conn.rollback();
                    return null;
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, borrow.getStudentId());
                insertStmt.setInt(2, borrow.getBookId());
                insertStmt.setDate(3, Date.valueOf(LocalDate.now()));
                insertStmt.executeUpdate();
                
                try (ResultSet rs = insertStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        borrow.setRecordId(rs.getInt(1));
                    }
                }
            }
            
            conn.commit();
            borrow.setBorrowDate(LocalDate.now().toString());
            return borrow;

        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return null;
        }
    }

    /**
     * THÊM HÀM MỚI: Logic trả sách hoàn chỉnh.
     */
    public Borrow returnBook(int recordId) {
        String findSql = "SELECT bookId FROM BorrowRecords WHERE recordId = ? AND returnDate IS NULL";
        String updateBorrowSql = "UPDATE BorrowRecords SET returnDate = ? WHERE recordId = ?";
        String updateBookSql = "UPDATE Books SET bookQuantity = bookQuantity + 1 WHERE bookID = ?";
        Connection conn = null;

        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);

            int bookId = -1;
            try (PreparedStatement findStmt = conn.prepareStatement(findSql)) {
                findStmt.setInt(1, recordId);
                ResultSet rs = findStmt.executeQuery();
                if (rs.next()) {
                    bookId = rs.getInt("bookId");
                } else {
                    conn.rollback();
                    return null;
                }
            }
            
            try (PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSql)) {
                updateBookStmt.setInt(1, bookId);
                updateBookStmt.executeUpdate();
            }

            try (PreparedStatement updateBorrowStmt = conn.prepareStatement(updateBorrowSql)) {
                updateBorrowStmt.setDate(1, Date.valueOf(LocalDate.now()));
                updateBorrowStmt.setInt(2, recordId);
                updateBorrowStmt.executeUpdate();
            }
            
            conn.commit();
            return getBorrowRecordById(recordId);

        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return null;
        }
    }
    
    // Sửa lại các hàm cũ cho đúng tên bảng/cột
    public GetBorrow getBorrowRecordById(int recordId) {
        String sql = "SELECT " +
                        "br.recordId, " +
                        "b.bookID, " +
                        "b.bookName, " +
                        "s.studentID, " +
                        "s.studentName, " +
                        "br.borrowDate, " +
                        "br.returnDate " +
                    "FROM BorrowRecords br " +
                    "JOIN Books b ON br.bookId = b.bookID " +
                    "JOIN Students s ON br.studentId = s.studentID " +
                    "WHERE br.recordId = ?";

        try (Connection conn = DatabaseConnector.getConnection(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, recordId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                GetBorrow b = new GetBorrow();   // ✅ đổi Borrow thành GetBorrow
                b.setRecordId(rs.getInt("recordId"));
                b.setBookId(rs.getInt("bookID"));
                b.setBookName(rs.getString("bookName"));      // OK
                b.setStudentId(rs.getInt("studentID"));
                b.setStudentName(rs.getString("studentName"));// OK
                b.setBorrowDate(rs.getString("borrowDate"));
                b.setReturnDate(rs.getString("returnDate"));
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updateBorrowRecord(int recordId, Borrow updatedBorrow) {
        String sql = "UPDATE BorrowRecords SET studentId = ?, bookId = ?, borrowDate = ?, returnDate = ? WHERE recordId = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, updatedBorrow.getStudentId());
            pstmt.setInt(2, updatedBorrow.getBookId());
            pstmt.setString(3, updatedBorrow.getBorrowDate());
            pstmt.setString(4, updatedBorrow.getReturnDate());
            pstmt.setInt(5, recordId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    
    public boolean deleteBorrowRecord(int recordId) {
        String sql = "DELETE FROM BorrowRecords WHERE recordId = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recordId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}