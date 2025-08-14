package backend.api;

import backend.entities.Book;
import backend.service.BookService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class BookApi {
    /**
     * Cập nhật thông tin của một cuốn sách 
     * @param bookId ID của cuốn sách cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public static Handler updateBook = ctx -> {
        int bookId = Integer.parseInt(ctx.pathParam("bookId"));
        BookService bookService = new BookService();

        Book updatedData = ctx.bodyAsClass(Book.class);
        updatedData.setBookId(bookId);

        boolean isUpdated = bookService.updateBook(bookId, updatedData);
        if (isUpdated) {
            ctx.status(200).json(updatedData);  // trả về đối tượng Book đã sửa
        } else {
            ctx.status(404).result("Book not found");
        }
    };

    /** 
     * thêm một cuốn sách mới vào cơ sử dữ liệu
     * @param book đối tượng Book chứa thông tin cuốn sách mới
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public static Handler addBook = ctx -> {
        Book book = ctx.bodyAsClass(Book.class);
        BookService bookService = new BookService();

        boolean isAdded = bookService.addBook(book);
        if (isAdded) {
            ctx.status(201).json(book);  // trả về đối tượng Book đã thêm
        } else {
            ctx.status(500).result("Failed to add book");
        }
    };

    /** 
     * Xóa một cuốn sách khỏi cơ sở dữ liệu
     * @param bookId ID của cuốn sách cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public static Handler deleteBook = ctx -> {
        int bookId = Integer.parseInt(ctx.pathParam("bookId"));
        BookService bookService = new BookService();

        boolean isDeleted = bookService.deleteBook(bookId);
        if (isDeleted) {
            ctx.status(204).result("Book deleted successfully");
        } else {
            ctx.status(404).result("Book not found");
        }
    };

    /**
     * Lấy danh sách tất cả các cuốn sách
     * @return danh sách các cuốn sách
     */
    public static Handler getAllBooks = ctx -> {
        BookService bookService = new BookService();
        List<Book> books = bookService.getAllBooks();

        if (books.isEmpty()) {
            ctx.status(404).result("No books found");
        } else {
            ctx.json(books);
        }
    };

    /**
     * Tìm một cuốn sách bằng tên
     * @param bookName Tên của cuốn sách cần tìm
     * @return danh sách các cuốn sách có tên tương ứng
     */
    public static Handler findBookByName = ctx -> {
        String bookName = ctx.queryParam("bookName");
        BookService bookService = new BookService();
        List<Book> books = bookService.findBookByName(bookName);
        if (books.isEmpty()) {
            ctx.status(404).result("No books found with the name: " + bookName);
        } else {
            ctx.json(books);
        }
    };
}
