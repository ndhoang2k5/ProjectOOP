package backend.service;

import backend.driver.DatabaseConnector;
import backend.entities.Borrow;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowService {
     /**
     * Tạo một bản ghi mượn sách mới và giảm số lượng sách trong kho
     * @param borrow đối tượng Borrow chứa thông tin mượn sách
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean createBorrowRecord(Borrow borrow) {
        String insertBorrowSql = 
            "INSERT INTO borrows (borrow_id, student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?, ?)";
        String updateBookSql = 
            "UPDATE books SET bookQuantity = bookQuantity - 1 WHERE bookId = ? AND bookQuantity > 0";

        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            // 1. Thêm bản ghi mượn
            try (PreparedStatement insertStmt = conn.prepareStatement(insertBorrowSql)) {
                insertStmt.setInt(1, borrow.getRecordId());
                insertStmt.setInt(2, borrow.getStudentId());
                insertStmt.setInt(3, borrow.getBookId());
                insertStmt.setString(4, borrow.getBorrowDate());
                insertStmt.setString(5, borrow.getReturnDate());
                int borrowInserted = insertStmt.executeUpdate();

                if (borrowInserted == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // 2. Giảm số lượng sách
            try (PreparedStatement updateStmt = conn.prepareStatement(updateBookSql)) {
                updateStmt.setInt(1, borrow.getBookId());
                int bookUpdated = updateStmt.executeUpdate();

                if (bookUpdated == 0) { // Không còn sách để mượn
                    conn.rollback();
                    return false;
                }
            }

            // 3. Commit nếu cả hai bước thành công
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 
     * Xóa một bản ghi mượn sách 
     * @param recordId ID của bản ghi mượn sách cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean deleteBorrowRecord(int recordId) {
        String sql = "DELETE FROM borrows WHERE borrow_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recordId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy một bản ghi mượn sách theo ID
     * @param recordId ID của bản ghi mượn sách cần lấy
     * @return đối tượng Borrow nếu tìm thấy, null nếu không tìm thấy
     */
    public Borrow getBorrowRecordById(int recordId) {
        String sql = "SELECT * FROM borrows WHERE borrow_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recordId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Borrow(
                    rs.getInt("borrow_id"),
                    rs.getInt("student_id"),
                    rs.getInt("book_id"),
                    rs.getString("borrow_date"),
                    rs.getString("return_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Chỉnh sửa một bản ghi dựa trên ID
     * @param recordId ID của bản ghi cần chỉnh sửa
     * @param updatedBorrow đối tượng Borrow chứa thông tin cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean updateBorrowRecord(int recordId, Borrow updatedBorrow) {
        String sql = "UPDATE borrows SET student_id = ?, book_id = ?, borrow_date = ?, return_date = ? WHERE borrow_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, updatedBorrow.getStudentId());
            pstmt.setInt(2, updatedBorrow.getBookId());
            pstmt.setString(3, updatedBorrow.getBorrowDate());
            pstmt.setString(4, updatedBorrow.getReturnDate());
            pstmt.setInt(5, recordId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}