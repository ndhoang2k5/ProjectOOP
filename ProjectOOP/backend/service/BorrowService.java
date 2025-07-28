package backend.service;

import backend.driver.DatabaseConnector;
import backend.entities.Borrow;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowService {

    /**
     * lấy danh sách tất các phiếu mượn trong hệ thống
     * @return danh sách Borrow
     */
    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String sql = "SELECT * FROM borrows";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Borrow borrow = new Borrow();
                borrow.setRecordId(rs.getInt("record_id"));
                borrow.setStudentId(rs.getInt("student_id"));
                borrow.setBookId(rs.getInt("book_id"));
                // Gán thêm các trường khác nếu có
                borrows.add(borrow);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

    /** 
     * thêm một phiếu mượn mới vào hệ thống
     * @param borrow đối tượng Borrow cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean addBorrow(Borrow borrow) {
        String sql = "INSERT INTO borrows (record_id, student_id, book_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, borrow.getRecordId());
            stmt.setInt(2, borrow.getStudentId());
            stmt.setInt(3, borrow.getBookId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa một phiếu mượn theo recordId
     * @param recordId ID của phiếu mượn cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean deleteBorrow(int recordId) {
        String sql = "DELETE FROM borrows WHERE record_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recordId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin của một phiếu mượn
     * @param borrow đối tượng Borrow cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean updateBorrow(Borrow borrow) {
        String sql = "UPDATE borrows SET student_id = ?, book_id = ? WHERE record_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, borrow.getStudentId());
            stmt.setInt(2, borrow.getBookId());
            stmt.setInt(3, borrow.getRecordId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}