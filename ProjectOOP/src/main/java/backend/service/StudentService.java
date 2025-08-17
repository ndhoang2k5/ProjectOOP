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
        String sql = "UPDATE students SET studentName = ?, studentAge = ?,  studentEmail = ? WHERE studentID = ?";

        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStudentName());
            stmt.setInt(2, student.getStudentAge());
            stmt.setString(3, student.getStudentEmail());
            stmt.setInt(4, studentId);

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
        String sql = "INSERT INTO students (studentId, studentName, studentAge, studentEmail) VALUES (? ,?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, student.getStudentId());
            stmt.setString(2, student.getStudentName());
            stmt.setInt(3, student.getStudentAge());
            stmt.setString(4, student.getStudentEmail());

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
        // Sửa tên bảng và tên cột
        String sql = "DELETE FROM students WHERE studentId = ?";

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
        // Sửa tên bảng
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                // Sửa tên cột và sử dụng đúng phương thức getInt
                student.setStudentId(rs.getInt("studentID"));
                student.setStudentName(rs.getString("studentName"));
                student.setStudentAge(rs.getInt("studentAge"));
                student.setStudentEmail(rs.getString("studentEmail"));
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Tìm kiếm 1 học sinh theo tên
     * @param studentName Tên của sinh viên cần tìm kiếm
     * @return Danh sách sinh viên có tên trùng khớp
     */
    public List<Student> searchStudentByName(String studentName) {
        List<Student> students = new ArrayList<>();
        // Sửa tên bảng và tên cột
        String sql = "SELECT * FROM students WHERE studentName LIKE ?";
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + studentName + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("studentId"));
                student.setStudentName(rs.getString("studentName"));
                student.setStudentAge(rs.getInt("studentAge"));
                student.setStudentEmail(rs.getString("studentEmail"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Lấy thông tin một sinh viên bằng ID
     * @param studentId ID của sinh viên cần tìm
     * @return đối tượng Student nếu tìm thấy, ngược lại trả về null
     */
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE studentID = ?";
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("studentId"));
                student.setStudentName(rs.getString("studentName"));
                student.setStudentAge(rs.getInt("studentAge"));
                student.setStudentEmail(rs.getString("studentEmail"));
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}