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
}
