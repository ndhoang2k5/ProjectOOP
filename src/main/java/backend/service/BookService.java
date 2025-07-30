package backend.service;

import backend.driver.DatabaseConnector;
import backend.entities.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    /**
     * Cập nhật thông tin của một cuốn sách thông qua ID
     * @param bookId ID của cuốn sách cần cập nhật
     * @param book đối tượng Book chứa thông tin mới
     */
    public boolean updateBook(int bookId, Book book) {
        String sql = "UPDATE books SET book_name = ?, book_quantity = ? WHERE book_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getBookName());
            stmt.setInt(2, book.getBookQuantity());
            stmt.setInt(3, bookId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    

}
