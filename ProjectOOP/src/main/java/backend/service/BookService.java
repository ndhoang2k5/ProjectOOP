package src.main.java.backend.service;

import src.main.java.backend.driver.DatabaseConnector;
import src.main.java.backend.entities.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    /** 
     * Lấy danh sách tất cả sách trong hệ thống
     * @return danh sách Book
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setBookQuantity(rs.getInt("book_quantity"));
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * Thêm một cuốn sách mới vào hệ thống
     * @param book đối tượng Book cần thêm
     */
    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (book_id, book_name, book_quantity) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, book.getBookId());
            stmt.setString(2, book.getBookName());
            stmt.setInt(3, book.getBookQuantity());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xoa một cuốn sách khỏi hệ thống
     * @param bookId ID của cuốn sách cần xóa
     */
    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin của một cuốn sách
     * @param book đối tượng Book cần cập nhật
     */
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET book_name = ?, book_quantity = ? WHERE book_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getBookName());
            stmt.setInt(2, book.getBookQuantity());
            stmt.setInt(3, book.getBookId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
