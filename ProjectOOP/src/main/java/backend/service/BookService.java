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

    /**
     * Thêm một cuốn sách mới vào cơ sở dữ liệu
     * @param book đối tượng Book chứa thông tin cuốn sách mới
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (book_name, book_quantity) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getBookName());
            stmt.setInt(2, book.getBookQuantity());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 
     * Xóa một cuốn sách khỏi cơ sở dữ liệu
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
     * Lấy danh sách tất cả các cuốn sách từ cơ sở dữ liệu
     * @return danh sách các đối tượng Book
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

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
}
