package backend.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import backend.api.BookApi;
import backend.driver.DatabaseConnector;

import io.javalin.Javalin;

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

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json"; // cú pháp mới
        }).start(7000);

        app.get("/books", BookApi.getAllBooks);
        app.post("/books/add/{bookId}/{bookName}/{bookQuantity}", BookApi.addBook);
        app.put("/books/{bookId}", BookApi.updateBook);
        app.delete("/books/{bookId}", BookApi.deleteBook);
    }
}
