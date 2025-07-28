package backend.entities;

public class Borrow {

    private int recordId;
    private int studentId;
    private int bookId;


    public Borrow() {
        // Default constructor
    }

    public Borrow(int recordId, int studentId, int bookId) {
        this.recordId = recordId;
        this.studentId = studentId;
        this.bookId = bookId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
