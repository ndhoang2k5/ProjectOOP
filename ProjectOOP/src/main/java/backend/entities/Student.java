package backend.entities;

public class Student {
    private String studentName;
    private int studentAge;
    private String studentId;
    private String studentEmail;

    public Student() {
        // Default constructor
    }

    public Student(String name, int age, String studentId, String email) {
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

}
