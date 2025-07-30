// File: backend/service/StudentService.java
package backend.service;

import backend.driver.DatabaseConnector;
import backend.entities.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    /**
     * Cập nhật thông tin của một sinh viên thông qua ID
     * @param studentId ID của sinh viên cần cập nhật
     * @param student đối tượng Student chứa thông tin mới
     */
    public boolean updateStudent(int studentId, Student student) {
        String sql = "UPDATE students SET student_name = ?, student_email = ? WHERE student_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStudentName());
            stmt.setString(2, student.getStudentEmail());
            stmt.setInt(3, studentId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

