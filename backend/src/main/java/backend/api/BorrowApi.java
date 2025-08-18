// src/main/java/backend/api/BorrowApi.java
package backend.api;

import backend.entities.Borrow;
import backend.service.BorrowService;
import io.javalin.http.Handler;
import java.util.Map;

public class BorrowApi {
    private static final BorrowService borrowService = new BorrowService();

    /** SỬA LẠI: Trả về đối tượng borrow trực tiếp, không bọc trong JSON khác */
    public static Handler createBorrowRecord = ctx -> {
        Borrow borrowRequest = ctx.bodyAsClass(Borrow.class);
        Borrow newRecord = borrowService.createBorrowRecord(borrowRequest);
        if (newRecord != null) {
            ctx.status(201).json(newRecord); // TRẢ VỀ TRỰC TIẾP
        } else {
            ctx.status(400).json(Map.of("error", "Không thể tạo phiếu mượn. Sách đã hết hoặc ID không hợp lệ."));
        }
    };
    
    /** HANDLER MỚI: Để xử lý việc trả sách */
    public static Handler returnBook = ctx -> {
        int recordId = Integer.parseInt(ctx.pathParam("recordId"));
        Borrow updatedRecord = borrowService.returnBook(recordId);
        if (updatedRecord != null) {
            ctx.status(200).json(updatedRecord);
        } else {
            ctx.status(404).json(Map.of("error", "Không tìm thấy phiếu mượn hợp lệ để trả."));
        }
    };

    // Các handler còn lại giữ nguyên logic nhưng nên trả về JSON lỗi cho thống nhất
    public static Handler getBorrowRecordById = ctx -> {
        int recordId = Integer.parseInt(ctx.pathParam("recordId"));
        Borrow borrow = borrowService.getBorrowRecordById(recordId);
        if (borrow != null) {
            ctx.json(borrow);
        } else {
            ctx.status(404).json(Map.of("error", "Không tìm thấy phiếu mượn"));
        }
    };

    public static Handler updateBorrowRecord = ctx -> {
        int recordId = Integer.parseInt(ctx.pathParam("recordId"));
        Borrow updatedData = ctx.bodyAsClass(Borrow.class);
        if (borrowService.updateBorrowRecord(recordId, updatedData)) {
            ctx.json(updatedData);
        } else {
            ctx.status(404).json(Map.of("error", "Không tìm thấy phiếu mượn"));
        }
    };

    public static Handler deleteBorrowRecord = ctx -> {
        int recordId = Integer.parseInt(ctx.pathParam("recordId"));
        if (borrowService.deleteBorrowRecord(recordId)) {
            ctx.status(204);
        } else {
            ctx.status(404).json(Map.of("error", "Không tìm thấy phiếu mượn"));
        }
    };
}