package libraryManagementSystem;

import book.Book;
import book.BookService;
import databaseRepository.LibraryService;
import user.User;
import user.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.security.Timestamp;
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
            Book book = new Book();
            book.setBookName(JOptionPane.showInputDialog(frame, "Insert book Title: "));
            book.setAuthor(JOptionPane.showInputDialog(frame, "Insert book Author: "));
            book.setYearPublished(JOptionPane.showInputDialog(frame, "Insert year of book publishing: "));
            book.setGenre(JOptionPane.showInputDialog(frame, "Insert book Genre: "));
            book.setDescription(JOptionPane.showInputDialog(frame, "Insert book Description: "));
            book.setStatus(JOptionPane.showConfirmDialog(frame, "Is book available? ") == JOptionPane.YES_OPTION);
            book.setBookAmount(Integer.parseInt(JOptionPane.showInputDialog(frame, "Insert amount of books: ")));
            book.setSpecialMarks(JOptionPane.showInputDialog(frame, "Insert special marks: "));

            this.bookService.addBookDB(book);

        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void viewBook() {
        try{
            int bookId = Integer.parseInt(JOptionPane.showInputDialog("Insert searched book ID: "));
            this.bookService.getSearchedBookDataDB(bookId);
            for (Book booksPrint : this.bookService.getSearchedBookDataDB(bookId)){
                System.out.println(booksPrint);
            }
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }

    /*    String[] bookView = {"ID", "NAME", "AUTHOR", "YEAR PUBLISHED", "GENRE", "DESCRIPTION", "STATUS",
                            "BOOK AMOUNT", "SPECIAL MARKS"};

        DefaultTableModel tableModel = new DefaultTableModel(bookView, 0);
                this.books.forEach( book -> tableModel.addRow(
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
        displayTable(tableModel);*/
    }

    private void displayTable(DefaultTableModel tableModel){
        JTable table = new JTable(tableModel);

        frame.setLayout(new BorderLayout());
        frame.setSize(1500, 600);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void updateBook() {
        try{
            int bookId = Integer.parseInt(JOptionPane.showInputDialog("Insert ID of book to update: "));
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
            int bookId = Integer.parseInt(JOptionPane.showInputDialog("Insert ID of book to delete"));
            bookService.deleteBookFromDB(bookId);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void collectNewUserData() {
        try{
            User user = new User();
            user.setUserName(JOptionPane.showInputDialog("Insert Name Surname: "));
            user.setPassword(JOptionPane.showInputDialog("Insert chosen password: "));
            user.setAge(Integer.parseInt(JOptionPane.showInputDialog("Insert your current age: ")));
            user.setEmail(JOptionPane.showInputDialog("Insert your e-mail: "));
            user.setPhoneNumber(JOptionPane.showInputDialog("Insert your phone number: "));
            user.setSpecialMarks(JOptionPane.showInputDialog("Insert additional information if necessary: "));

            this.userService.addUserDB(user);

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
            int userId = Integer.parseInt(JOptionPane.showInputDialog("Insert ID of your account: "));
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
            int userId = Integer.parseInt(JOptionPane.showInputDialog("Insert your user ID: "));
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
            int userId = Integer.parseInt(JOptionPane.showInputDialog("Insert your Account ID: ")); // check is id valid for user, book
            int bookId = Integer.parseInt(JOptionPane.showInputDialog("Insert ID of book to borrow: "));
            bookBorrowedId = this.libraryService.checkIfBookIsSameAsBorrowed(userId, bookId);
            if (bookBorrowedId == 0){
            int bookAmount = this.libraryService.receiveBookAmountDB(bookId);
            if (bookAmount > 1) { // create separated method
                bookAmount--;
                this.libraryService.updateBorrowedBookDataDB(bookAmount, bookId);
                this.libraryService.addBookToUserAccount(bookId, userId);
            }
            } else {
                System.out.println("Selected book is not available. Please select other book");
            }
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }

    public void selectBookToReturn() {
        try{
            int userId = Integer.parseInt(JOptionPane.showInputDialog("Insert your Account ID: "));
            int bookId = Integer.parseInt(JOptionPane.showInputDialog("Insert ID of book to return "));
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
            int userId = Integer.parseInt(JOptionPane.showInputDialog("Insert your Account ID: "));
            this.userService.getUserBorrowedBookDataDB(userId);
            /*
            *             for (User usersPrint : this.userService.getUserDataDB(userId)){
                System.out.println(usersPrint);
            }*/
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }
}