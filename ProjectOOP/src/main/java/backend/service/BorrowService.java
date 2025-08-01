package backend.service;

import backend.driver.DatabaseConnector;
import backend.entities.Borrow;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowService {
    /**
     * tạo một bản ghi mượn sách mới
     * @param borrow đối tượng Borrow chứa thông tin mượn sách
     * @return true nếu tạo thành công, false nếu thất bại
     */
    public boolean createBorrowRecord(Borrow borrow) {
        String sql = "INSERT INTO borrows (borrow_id, student_id, book_id, borrow_date, return_date) VALUES (?,?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, borrow.getRecordId());
            pstmt.setInt(2, borrow.getStudentId());
            pstmt.setInt(3, borrow.getBookId());
            pstmt.setString(4, borrow.getBorrowDate());
            pstmt.setString(5, borrow.getReturnDate());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
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
}