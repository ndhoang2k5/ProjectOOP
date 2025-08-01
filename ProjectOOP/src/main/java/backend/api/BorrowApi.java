package backend.api;

import backend.entities.Borrow;
import backend.service.BorrowService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class BorrowApi {
    /**
     * tạo một bản ghi mượn sách mới
     * @param borrow đối tượng Borrow chứa thông tin mượn sách
     * @return true nếu tạo thành công, false nếu thất bại
     */
    public static Handler createBorrowRecord = ctx -> {
        Borrow borrow = ctx.bodyAsClass(Borrow.class);
        BorrowService borrowService = new BorrowService();
        boolean isCreated = borrowService.createBorrowRecord(borrow);
        if (isCreated) {
            ctx.status(201).json(borrow);  // trả về đối tượng Borrow đã tạo
        } else {
            ctx.status(500).result("Failed to create borrow record");
        }
    };
}
