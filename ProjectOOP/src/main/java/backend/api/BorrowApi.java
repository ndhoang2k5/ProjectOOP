package backend.api;

import backend.entities.Borrow;
import backend.service.BorrowService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class BorrowApi {
    /**
     * tạo một bản ghi mượn sách mới
     * @param borrow đối tượng Borrow chứa thông tin mượn sách
     * @return true nếu tạo thành công, false nếu thất bại
     */
    public static Handler createBorrowRecord = ctx -> {
        try {
            // Parse dữ liệu JSON từ request body sang object Borrow
            Borrow borrow = ctx.bodyAsClass(Borrow.class);

            BorrowService borrowService = new BorrowService();
            boolean isCreated = borrowService.createBorrowRecord(borrow);

            if (isCreated) {
                ctx.status(201).json(Map.of(
                    "success", true,
                    "message", "Tạo bản ghi mượn sách thành công",
                    "data", borrow
                ));
            } else {
                ctx.status(400).json(Map.of(
                    "success", false,
                    "message", "Không thể tạo bản ghi mượn sách (có thể do sách đã hết)"
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of(
                "success", false,
                "message", "Lỗi server khi tạo bản ghi mượn sách"
            ));
        }
    };


    /** 
     * Xóa một bản ghi mượn sách
     * @param recordId ID của bản ghi mượn sách cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public static Handler deleteBorrowRecord = ctx -> {
        int recordId = Integer.parseInt(ctx.pathParam("recordId"));
        BorrowService borrowService = new BorrowService();
        boolean isDeleted = borrowService.deleteBorrowRecord(recordId);
        if (isDeleted) {
            ctx.status(204).result("Borrow record deleted successfully");
        } else {
            ctx.status(404).result("Borrow record not found");
        }
    };



    /**
     * lấy một bản ghi mượn sách theo ID
     * @param recordId ID của bản ghi mượn sách cần lấy
     * @return đối tượng Borrow nếu tìm thấy, null nếu không tìm thấy
     */
    public static Handler getBorrowRecordById = ctx -> {
        int recordId = Integer.parseInt(ctx.pathParam("recordId"));
        BorrowService borrowService = new BorrowService();
        Borrow borrow = borrowService.getBorrowRecordById(recordId);
        if (borrow != null) {
            ctx.status(200).json(borrow);  // trả về đối tượng Borrow tìm thấy
        } else {
            ctx.status(404).result("Borrow record not found");
        }
    };


    
    /**
     * Chỉnh sửa một bản ghi dựa trên ID
     * @param recordId ID của bản ghi cần chỉnh sửa
     * @return true nếu chỉnh sửa thành công, false nếu thất bại
     */
    public static Handler updateBorrowRecord = ctx -> {
        int recordId = Integer.parseInt(ctx.pathParam("recordId"));
        BorrowService borrowService = new BorrowService();
        Borrow updatedData = ctx.bodyAsClass(Borrow.class);
        updatedData.setRecordId(recordId);
        boolean isUpdated = borrowService.updateBorrowRecord(recordId, updatedData);
        if (isUpdated) {
            ctx.status(200).json(updatedData);  // trả về đối tượng Borrow đã sửa
        } else {
            ctx.status(404).result("Borrow record not found");
        }
    };
}