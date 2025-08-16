package backend.entities;

public class Book {
    private int bookId;
    private String bookName;
    private String author;
    private int bookQuantity;   

    public Book() {
        // Default constructor
    }

    public Book(int bookId, String bookName, String author, int bookQuantity) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.bookQuantity = bookQuantity;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}