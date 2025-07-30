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

    /** 
     * Thêm một sinh viên mới vào cơ sở dữ liệu
     * @param student đối tượng Student chứa thông tin sinh viên mới
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (student_name, student_email) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStudentName());
            stmt.setString(2, student.getStudentEmail());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa một sinh viên khỏi cơ sở dữ liệu
     * @param studentId ID của sinh viên cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy danh sách tất cả sinh viên
     * @return danh sách các sinh viên
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentEmail(rs.getString("student_email"));
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

