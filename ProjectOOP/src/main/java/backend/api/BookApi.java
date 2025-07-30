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
     * api lấy tất cả sách
     * @return danh sách sách
     */
    private static List<String> books = new ArrayList<>();
    public static void getAllBooks() {
    }
    
}
