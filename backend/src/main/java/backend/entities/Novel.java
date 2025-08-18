package backend.entities;

public class Novel extends Book {
    private String genre;
    private String language;

    public Novel() {
        // Default constructor
    }

    public Novel(int bookId, String bookName, String author, int bookQuantity, String genre, String language) {
        super(bookId, bookName, author, bookQuantity);
        this.genre = genre;
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Genre: " + genre);
        System.out.println("Language: " + language);
    }
}
