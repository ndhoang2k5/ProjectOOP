package backend.entities;

public class TextBook extends Book {
    private String subject;
    private String publisher;

    public TextBook() {
        // Default constructor
    }

    public TextBook(int bookId, String bookName, String author, int bookQuantity, String subject, String publisher) {
        super(bookId, bookName, author, bookQuantity);
        this.subject = subject;
        this.publisher = publisher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Subject: " + subject);
        System.out.println("Publisher: " + publisher);
    }
}
