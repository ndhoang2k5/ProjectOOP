package backend.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import backend.api.BookApi;
import backend.api.BorrowApi;
import backend.api.StudentApi;
import io.javalin.Javalin;

public class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/lib?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1109"; // Đảm bảo mật khẩu này đúng

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Không tìm thấy MySQL Driver!");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.bundledPlugins.enableCors(cors -> cors.addRule(it -> it.anyHost()));
        });

        // API Sách
        app.get("/books", BookApi.getAllBooks);
        app.post("/books", BookApi.addBook);
        app.put("/books/{bookId}", BookApi.updateBook);
        app.delete("/books/{bookId}", BookApi.deleteBook);

        // API Sinh viên
        app.get("/students", StudentApi.getAllStudents);
        app.get("/students/{studentId}", StudentApi.getStudentById);
        app.post("/students", StudentApi.addStudent);
        app.put("/students/{studentId}", StudentApi.updateStudent);
        app.delete("/students/{studentId}", StudentApi.deleteStudent);
        app.get("/students/search/{name}", StudentApi.searchStudentByName);

        // API Mượn sách
        app.get("/borrows/{recordId}", BorrowApi.getBorrowRecordById);
        app.post("/borrows", BorrowApi.createBorrowRecord);
        app.put("/borrows/{recordId}/return", BorrowApi.returnBook);
        app.put("/borrows/{recordId}", BorrowApi.updateBorrowRecord);
        app.delete("/borrows/{recordId}", BorrowApi.deleteBorrowRecord);

        app.start(7000);
    }
}