package libraryManagementSystem;

import book.Book;
import book.BookService;
import databaseRepository.LibraryService;
import user.User;
import user.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class LibraryController {

    protected JFrame frame;
    private final BookService bookService;
    private final UserService userService;
    private final LibraryService libraryService;
    ArrayList<Book> books = new ArrayList<>();
    boolean status;
    int bookAmount;
    String userName;
    int userId;
    String email;

    public LibraryController(BookService bookService, UserService userService, LibraryService libraryService){
        this.bookService = bookService;
        this.userService = userService;
        this.libraryService = libraryService;
        frame = new JFrame();
    }

    public void createBook(){
        try{
            this.bookService.addBookDB(this.CollectAndCreateNewBook());
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void viewBook() {
        try{
            int bookId = collectBookId();
            this.bookService.getSearchedBookDataDB(bookId);

            String[] bookView = {"ID", "TITLE", "AUTHOR", "YEAR PUBLISHED", "GENRE", "DESCRIPTION", "STATUS",
                    "BOOK AMOUNT", "SPECIAL MARKS"};
            DefaultTableModel tableModel = new DefaultTableModel(bookView, 0);
            this.bookService.getSearchedBookDataDB(bookId).forEach( book -> tableModel.addRow(
                    new String[]{
                            String.valueOf(book.getBookId()),
                            book.getBookName(),
                            book.getAuthor(),
                            book.getYearPublished(),
                            book.getGenre(),
                            book.getDescription(),
                            String.valueOf(book.getStatus()),
                            String.valueOf(book.getBookAmount()),
                            book.getSpecialMarks()
                    }
            ));
            displayTable(tableModel);

        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    private void displayTable(DefaultTableModel tableModel){
        JTable table = new JTable(tableModel);

        frame.setLayout(new BorderLayout());
        frame.setSize(1500, 200);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void updateBook() {
        try{
            int bookId = collectBookId();
            String[] updateOptions = {"Status", "Book Amount"};
            String itemToUpdate = (String) JOptionPane.showInputDialog(
                    this.frame,
                    "Select update item",
                    "Update book",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    updateOptions,
                    updateOptions[0]
            );
            if (itemToUpdate.equals("Status")) {
                status = JOptionPane.showConfirmDialog(frame, "Is book available? ") == JOptionPane.YES_OPTION;
                bookService.updateBookStatusDB(status, bookId);
            }

            if (itemToUpdate.equals("Book Amount"))
                bookAmount = Integer.parseInt(JOptionPane.showInputDialog("Insert actual book amount: "));
                bookService.updateBookAmountDB(bookAmount, bookId);

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void deleteBook() {
        try{
            int bookId = collectBookId();
            bookService.deleteBookFromDB(bookId);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void collectNewUserData() {
        try{
            this.userService.addUserDB(CollectAndCreateNewUser());
        } catch ( Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void collectLogInData() {
        try{
            String email = JOptionPane.showInputDialog("Insert your e-mail: ");
            String password = JOptionPane.showInputDialog("Insert your password: ");

            String userName = this.userService.receiveUserLogInDataDB(email, password);
            System.out.println("Welcome to Library Management System, " + userName);

        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void selectUserAccountDataForUpdate() {
        try{
            int userId = collectUserId();
            String password = JOptionPane.showInputDialog("Insert your password: ");

            String[] updateOptions = {"User Name", "E-mail"};
            String itemToUpdate = (String) JOptionPane.showInputDialog(
                    this.frame,
                    "Select update item",
                    "Update User Account",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    updateOptions,
                    updateOptions[0]
            );

            if (itemToUpdate.equals("User Name")){
                userName = JOptionPane.showInputDialog("Insert new Name Surname: ");
                userService.updateUserNameDB(userName, userId, password);
            }

            if (itemToUpdate.equals("E-mail")){
                email = JOptionPane.showInputDialog("Insert new E-mail:");
                userService.updateUserEmailDB(email, userId, password);

            }

        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void viewUser() {
        try{
            int userId = collectUserId();
            this.userService.getUserDataDB(userId);

            for (User usersPrint : this.userService.getUserDataDB(userId)){
                System.out.println(usersPrint);
            }
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void selectBookToBorrow() {
       int bookBorrowedId;
        try{
            int userId = collectUserId();
            int bookId = collectBookId();
            bookBorrowedId = this.libraryService.checkIfBookIsSameAsBorrowed(userId, bookId);
            if (bookBorrowedId == 0){
            int bookAmount = this.libraryService.receiveBookAmountDB(bookId);
            if (bookAmount > 1) {
                bookAmount--;
                this.libraryService.updateBorrowedBookDataDB(bookAmount, bookId);
                this.libraryService.addBookToUserAccount(bookId, userId);
            }
            } else {
                System.out.println("Selected book is not available. Please select different book");
            }
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void selectBookToReturn() {
        try{
            int userId = collectUserId();
            int bookId = collectBookId();
            int bookAmount = this.libraryService.receiveBookAmountDB(bookId);
                bookAmount++;
                this.libraryService.updateBorrowedBookDataDB(bookAmount, bookId);
                this.libraryService.removeBookFromUserAccount(bookId, userId);
            } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void viewAllBorrowedBooks() {
        try{
            int userId = collectUserId();
            this.userService.getUserBorrowedBookDataDB(userId);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public Book CollectAndCreateNewBook(){
        Book book = new Book();
        book.setBookName(JOptionPane.showInputDialog(frame, "Insert book Title: "));
        book.setAuthor(JOptionPane.showInputDialog(frame, "Insert book Author: "));
        book.setYearPublished(JOptionPane.showInputDialog(frame, "Insert year of book publishing: "));
        book.setGenre(JOptionPane.showInputDialog(frame, "Insert book Genre: "));
        book.setDescription(JOptionPane.showInputDialog(frame, "Insert book Description: "));
        book.setStatus(JOptionPane.showConfirmDialog(frame, "Is book available? ") == JOptionPane.YES_OPTION);
        book.setBookAmount(Integer.parseInt(JOptionPane.showInputDialog(frame, "Insert amount of books: ")));
        book.setSpecialMarks(JOptionPane.showInputDialog(frame, "Insert special marks: "));
        return book;
    }

    public int collectBookId(){
        int bookId = Integer.parseInt(JOptionPane.showInputDialog("Insert ID of book you are looking for: "));
        return bookId;
    }

    public User CollectAndCreateNewUser(){
        User user = new User();
        user.setUserName(JOptionPane.showInputDialog("Insert Name Surname: "));
        user.setPassword(JOptionPane.showInputDialog("Insert chosen password: "));
        user.setAge(Integer.parseInt(JOptionPane.showInputDialog("Insert your current age: ")));
        user.setEmail(JOptionPane.showInputDialog("Insert your e-mail: "));
        user.setPhoneNumber(JOptionPane.showInputDialog("Insert your phone number: "));
        user.setSpecialMarks(JOptionPane.showInputDialog("Insert additional information if necessary: "));
        return user;
    }

    public int collectUserId(){
        int userId = Integer.parseInt(JOptionPane.showInputDialog("Insert ID of your account: "));
        return userId;
    }

    public void decreesBookAmount(int bookAmount) {
        if (bookAmount > 1) { // create separated method
            bookAmount--;
        }
    }
}