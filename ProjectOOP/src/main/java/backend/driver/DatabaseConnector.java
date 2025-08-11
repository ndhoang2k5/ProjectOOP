package backend.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/lib?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Nguyenduyhoang2005";

    static {
        try {
            // Nạp driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy MySQL Driver!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public static void insertBook(int bookId, String bookName, int bookQuantity) {
        String sql = "INSERT INTO books (bookId, bookName, bookQuantity) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            stmt.setString(2, bookName);
            stmt.setInt(3, bookQuantity);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Thêm sách thành công!");
            }

        } catch (SQLException e) {
            System.err.println("Kết nối hoặc thêm sách thất bại!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        insertBook(1, "Nguyễn Văn A", 2020);
        insertBook(2, "Trần Thị B", 2021);
        insertBook(3, "Lê Văn C", 2019);
    }
}
