package backend.api;

import backend.entities.Student;
import backend.service.StudentService;
import io.javalin.http.Handler;
import java.util.List;

public class StudentApi {
    
    /** 
     * Cập nhật thông tin của một sinh viên
     */
    public static Handler updateStudent = ctx -> {
        int studentId = Integer.parseInt(ctx.pathParam("studentId"));
        StudentService studentService = new StudentService();

        // Lấy dữ liệu mới từ body của request
        Student updatedData = ctx.bodyAsClass(Student.class);
        
        // SỬA LỖI: Gán studentId (kiểu int) cho đối tượng.
        // Dòng này đảm bảo đối tượng có ID chính xác trước khi trả về,
        // mặc dù service layer chỉ cần giá trị int.
        updatedData.setStudentId(studentId);

        boolean isUpdated = studentService.updateStudent(studentId, updatedData);
        if (isUpdated) {
            ctx.status(200).json(updatedData);  // Trả về đối tượng Student đã sửa
        } else {
            ctx.status(404).result("Student not found");
        }
    };

    /** 
     * Thêm một sinh viên mới vào cơ sở dữ liệu
     */
    public static Handler addStudent = ctx -> {
        StudentService studentService = new StudentService();
        Student newStudent = ctx.bodyAsClass(Student.class);

        // Lưu ý: Chúng ta không cần gán ID ở đây vì nó là AUTO_INCREMENT trong DB.
        // Service layer đã được sửa để không chèn ID.

        boolean isAdded = studentService.addStudent(newStudent);
        if (isAdded) {
            ctx.status(201).json(newStudent);  // Trả về đối tượng Student đã thêm
        } else {
            ctx.status(400).result("Failed to add student");
        }
    };

    /**
     * Xóa một sinh viên khỏi cơ sở dữ liệu
     */
    public static Handler deleteStudent = ctx -> {
        int studentId = Integer.parseInt(ctx.pathParam("studentId"));
        StudentService studentService = new StudentService();

        boolean isDeleted = studentService.deleteStudent(studentId);
        if (isDeleted) {
            ctx.status(204); // 204 No Content là mã trạng thái phù hợp cho việc xóa thành công
        } else {
            ctx.status(404).result("Student not found");
        }
    };

    /**
     * Lấy danh sách tất cả sinh viên
     */
    public static Handler getAllStudents = ctx -> {
        StudentService studentService = new StudentService();
        List<Student> students = studentService.getAllStudents();

        if (students != null) { // Không cần kiểm tra isEmpty, vì trả về mảng rỗng [] là hợp lệ
            ctx.status(200).json(students);
        } else {
            // Trường hợp này hiếm khi xảy ra trừ khi có lỗi SQL
            ctx.status(500).result("Error retrieving students");
        }
    };

    /**
     * Tìm kiếm học sinh theo tên
     */
    public static Handler searchStudentByName = ctx -> {
        // SỬA LỖI: Đổi từ queryParam thành pathParam để khớp với định nghĩa endpoint /student/{name}
        String name = ctx.pathParam("name");
        StudentService studentService = new StudentService();
        List<Student> students = studentService.searchStudentByName(name); 
        
        if (students != null) {
            ctx.status(200).json(students);
        } else {
             ctx.status(500).result("Error searching for students");
        }
    };

    public static Handler getStudentById = ctx -> {
    int studentId = Integer.parseInt(ctx.pathParam("studentId"));
    StudentService studentService = new StudentService();
    Student student = studentService.getStudentById(studentId);

    if (student != null) {
        ctx.status(200).json(student);
    } else {
        ctx.status(404).result("Student not found with ID: " + studentId);
    }
    };
}