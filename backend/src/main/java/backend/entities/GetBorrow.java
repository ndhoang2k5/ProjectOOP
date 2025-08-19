package backend.entities;

public class GetBorrow extends Borrow {
    private String studentName;
    private String bookName;
    
    public GetBorrow() {
        // Default constructor
    }

    public GetBorrow(int recordId, int studentId, int bookId, String borrowDate, String returnDate, String studentName, String bookName) {
        super(recordId, studentId, bookId, borrowDate, returnDate);
        this.studentName = studentName;
        this.bookName = bookName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {   
        this.studentName = studentName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}