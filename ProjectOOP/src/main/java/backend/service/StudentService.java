// File: backend/service/StudentService.java
package backend.service;

import backend.driver.DatabaseConnector;
import backend.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    /**
     * Lấy danh sách tất cả sinh viên trong hệ thống
     * @return danh sách Student
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(String.valueOf(rs.getInt("id")));
                student.setStudentName(rs.getString("name"));
                student.setStudentEmail(rs.getString("email"));
                // Gán thêm các trường khác nếu có
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    /**
     * Thêm một sinh viên mới vào hệ thống
     * @param student đối tượng Student cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, age, id, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStudentName());
            stmt.setInt(2, student.getStudentAge());
            stmt.setString(3, student.getStudentId());
            stmt.setString(4, student.getStudentEmail());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin của một sinh viên
     * @param student đối tượng Student cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, age = ?, email = ? WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStudentName());
            stmt.setInt(2, student.getStudentAge());
            stmt.setString(3, student.getStudentEmail());
            stmt.setString(4, student.getStudentId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa một sinh viên khỏi hệ thống
     * @param studentId ID của sinh viên cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean deleteStudent(String studentId) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

