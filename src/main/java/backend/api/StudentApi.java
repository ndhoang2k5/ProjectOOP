package backend.api;

import backend.entities.Student;
import backend.service.StudentService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class StudentApi {
    
    /** 
     * Cập nhật thông tin của một sinh viên
     * @param studentId ID của sinh viên cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public static Handler upadateStudent = ctx -> {
        int studentId = Integer.parseInt(ctx.pathParam("studentId"));
        StudentService studentService = new StudentService();

        Student updatedData = ctx.bodyAsClass(Student.class);
        updatedData.setStudentId(String.valueOf(studentId));

        boolean isUpdated = studentService.updateStudent(studentId, updatedData);
        if (isUpdated) {
            ctx.status(200).json(updatedData);  // trả về đối tượng Student đã sửa
        } else {
            ctx.status(404).result("Student not found");
        }
    };

    /** 
     * Thêm một sinh viên mới vào cơ sở dữ liệu
     * @param student đối tượng Student chứa thông tin sinh viên mới
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public static Handler addStudent = ctx -> {
        Student student = ctx.bodyAsClass(Student.class);
        StudentService studentService = new StudentService();

        boolean isAdded = studentService.addStudent(student);
        if (isAdded) {
            ctx.status(201).json(student);  // trả về đối tượng Student đã thêm
        } else {
            ctx.status(500).result("Failed to add student");
        }
    };

    /**
     * Xóa một sinh viên khỏi cơ sở dữ liệu
     * @param studentId ID của sinh viên cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public static Handler deleteStudent = ctx -> {
        int studentId = Integer.parseInt(ctx.pathParam("studentId"));
        StudentService studentService = new StudentService();

        boolean isDeleted = studentService.deleteStudent(studentId);
        if (isDeleted) {
            ctx.status(204).result("Student deleted successfully");
        } else {
            ctx.status(404).result("Student not found");
        }
    };

    /**
     * Lấy danh sách tất cả sinh viên
     * @return danh sách sinh viên
     */
    public static Handler getAllStudents = ctx -> {
        StudentService studentService = new StudentService();
        List<Student> students = studentService.getAllStudents();

        if (students != null && !students.isEmpty()) {
            ctx.status(200).json(students);
        } else {
            ctx.status(404).result("No students found");
        }
    };

}
