package libraryManagementSystem;

import book.Book;
import book.BookService;
import databaseRepository.LibraryService;
import user.User;
import user.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibraryController {

    protected JFrame frame;
    private final BookService bookService;
    private final UserService userService;
    private final LibraryService libraryService;
    boolean status;
    int bookAmount;
    String userName;
    int userId;
    String email;
    int bookBorrowedId;

    public LibraryController(BookService bookService, UserService userService, LibraryService libraryService){
        this.bookService = bookService;
        this.userService = userService;
        this.libraryService = libraryService;
        frame = new JFrame();
    }

    public void createBook(){
        try{
            Book book = this.CollectAndCreateNewBook();
            this.bookService.addBookDB(book);
            System.out.println("Book created successfully");
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void viewBook() {
        try{
            int bookId = collectBookId();

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

    public void displayAllLibraryBooks() {
        try{
            String[] bookView = {"ID", "TITLE", "AUTHOR", "YEAR PUBLISHED", "GENRE", "DESCRIPTION", "STATUS",
                    "BOOK AMOUNT", "SPECIAL MARKS"};

            DefaultTableModel tableModel = new DefaultTableModel(bookView, 0);
            this.bookService.getSearchedBookDataDB().forEach( book -> tableModel.addRow(
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
                System.out.println("Book status updated successfully");
            }

            if (itemToUpdate.equals("Book Amount"))
                bookAmount = Integer.parseInt(JOptionPane.showInputDialog("Insert actual book amount: "));
                bookService.updateBookAmountDB(bookAmount, bookId);
                System.out.println("Book amount updated successfully");

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void deleteBook() {
        try{
            int bookId = collectBookId();
            bookService.deleteBookFromDB(bookId);
            System.out.println("Book deleted successfully");
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public int collectNewUserData() {
        try{
            User user = CollectAndCreateNewUser();
            this.userService.addUserDB(user);
            int userId = this.userService.returnNewUserID(user.getEmail(), user.getPassword());
            if(userId <=0){
                System.out.println("Please insert valid data");
                collectNewUserData();
            } else {
                System.out.println("Your Account created successfully!");
                return userId;
            }
        } catch ( Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
        return userId;
    }

    public int collectLogInData() {
        try{
            String email = JOptionPane.showInputDialog("Insert your e-mail: ");
            String password = JOptionPane.showInputDialog("Insert your password: ");

            int userId = this.userService.receiveUserLogInDataDB(email, password);
            if(userId <= 0){
                System.out.println("Please insert valid login data");
                collectLogInData();
            } else {
                System.out.println("Welcome to Library Management System!");
                return userId;
            }

        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
        return userId;
    }

    public void selectUserAccountDataForUpdate(int userId) {
        try{
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
                System.out.println("User information updated successfully");
            }

            if (itemToUpdate.equals("E-mail")){
                email = JOptionPane.showInputDialog("Insert new E-mail:");
                userService.updateUserEmailDB(email, userId, password);
                System.out.println("User information updated successfully");
            }
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void viewUser(int userId) {
        try{
            this.userService.getUserDataDB(userId);

            for (User usersPrint : this.userService.getUserDataDB(userId)){
                System.out.println(usersPrint);
                break;
            }
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void selectBookToBorrow(int userId) {
        try{
            int usersBorrowedBookAmount = userService.getUsersBorrowedBookAmount(userId);
            if (usersBorrowedBookAmount >= 2){
                System.out.println("Sorry, you can't borrow more than 2 books at the same time. Enjoy the reading!");
            } else {
                int bookId = collectBookId();
                bookBorrowedId = this.libraryService.checkIfBookIsTheSameAsSelected(userId, bookId);
                if (bookBorrowedId == 0){
                    int bookAmount = this.libraryService.receiveBookAmountDB(bookId);
                    if (bookAmount >= 1) {
                        bookAmount--;
                        this.libraryService.updateBorrowedBookDataDB(bookAmount, bookId);
                        this.libraryService.addBookToUserAccount(bookId, userId);
                        this.libraryService.confirmationBookBorrowed(bookId, userId);
                    }
                } else {
                    System.out.println("Selected book is not available. Please select different book");
                }
            }
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void selectBookToReturn(int userId) {
        try{
            int bookId = collectBookId();
            bookBorrowedId = this.libraryService.checkIfBookIsTheSameAsSelected(userId, bookId);
            if (bookBorrowedId == 0){
                System.out.println("Sorry, we don't have information about book with ID: " + bookId + " , please try again");
            } else {
                this.libraryService.removeBookFromUserAccount(bookId, userId);
                int bookAmount = this.libraryService.receiveBookAmountDB(bookId);
                bookAmount++;
                this.libraryService.updateBorrowedBookDataDB(bookAmount, bookId);
            }
            } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void viewAllBorrowedBooks(int userId) {
        try{
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
}