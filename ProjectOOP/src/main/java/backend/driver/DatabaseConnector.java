package backend.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import backend.api.BookApi;
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
            // Set default Content-Type
            config.http.defaultContentType = "application/json";

            // Bật CORS cho tất cả origin (Javalin 6.x)
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost()); // Cho phép mọi domain
            });
        });

        // Định nghĩa các API endpoint
        app.get("/books", BookApi.getAllBooks);
        app.post("/books/add/{bookId}/{bookName}/{bookQuantity}", BookApi.addBook);
        app.put("/books/{bookId}", BookApi.updateBook);
        app.delete("/books/{bookId}", BookApi.deleteBook);

        // Chạy server
        app.start(7000);
    }
}
