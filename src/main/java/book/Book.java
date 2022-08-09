package book;

public class Book {

    private int bookId;
    private String bookName;
    private String author;
    private String yearPublished;
    private String genre;
    private String description;
    private boolean status;
    private int bookAmount;
    private String specialMarks;
    private String borrowedAt;
    private String returnedAt;

    public Book() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getBookAmount() {
        return bookAmount;
    }

    public void setBookAmount(int bookAmount) {
        this.bookAmount = bookAmount;
    }

    public String getSpecialMarks() {
        return specialMarks;
    }

    public void setSpecialMarks(String specialMarks) {
        this.specialMarks = specialMarks;
    }

    @Override
    public String toString() {
        return "BookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", yearPublished='" + yearPublished + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", bookAmount='" + bookAmount + '\'' +
                ", specialMarks='" + specialMarks + '\'' +
                ", borrowedAt=" + borrowedAt;

    }
}
