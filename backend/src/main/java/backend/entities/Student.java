package backend.entities;

public class Student {
    private String studentName;
    private int studentAge;
    private int studentId; // Sửa từ String thành int
    private String studentEmail;

    public Student() {
        // Default constructor
    }

    public Student(String name, int age, int studentId, String email) { // Sửa kiểu dữ liệu tham số
        this.studentName = name;
        this.studentAge = age;
        this.studentId = studentId;
        this.studentEmail = email;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public int getStudentId() { // Sửa kiểu trả về
        return studentId;
    }

    public void setStudentId(int studentId) { // Sửa kiểu tham số
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}