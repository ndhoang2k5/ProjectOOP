package backend.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import backend.api.BookApi;
import backend.api.BorrowApi;
import backend.api.StudentApi;
import backend.entities.Book;
import backend.entities.Borrow;
import backend.entities.Student;
import io.javalin.Javalin;

    public class DatabaseConnector {
        private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/lib?useSSL=false&serverTimezone=UTC";
        private static final String JDBC_USER = "root";
        private static final String JDBC_PASSWORD = "ngocnam2005";

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
            config.http.defaultContentType = "application/json";
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        });

        app.get("/books", BookApi.getAllBooks);
        app.post("/books", BookApi.addBook);
        app.put("/books/{bookId}", BookApi.updateBook);
        app.delete("/books/{bookId}", BookApi.deleteBook);


        app.get("/students", StudentApi.getAllStudents);
        app.get("/students/{studentId}", StudentApi.getStudentById);
        app.post("/students", StudentApi.addStudent);
        app.put("/students/{studentId}", StudentApi.updateStudent);
        app.delete("/students/{studentId}", StudentApi.deleteStudent);
        app.get("/students/search/{name}", StudentApi.searchStudentByName);


        // === API MƯỢN SÁCH (giữ nguyên) ===
        app.get("/borrows", BorrowApi.getBorrowRecordById);
        app.post("/borrows/add/{recordId}/{studentId}/{bookId}/{borrowDate}/{returnDate}", BorrowApi.createBorrowRecord);
        app.put("/borrows/{recordId}", BorrowApi.updateBorrowRecord);
        app.delete("/borrows/{recordId}", BorrowApi.deleteBorrowRecord);

        // Chạy server
        app.start(7000);
    }
}
