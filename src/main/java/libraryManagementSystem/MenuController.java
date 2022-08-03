package libraryManagementSystem;
/*
*   A library management system
      -  It should be possible to view, add, delete, update and remove books
      -  It should be possible to borrow books from library,
      -  It should be possible to borrow more than 1 copy of the same book from the library
      -  If a user already borrowed a book, it should not be possible to borrow another copy of the same book
      -  All books should be stored in Database
      -  It should be able to see the currently borrowed books by a single user
      -  It should be possible return borrowed books
      - All changes made in application should be recorded.
        E.g user borrowed book, 5pm Sunday
        Username returned book, 2 am Tuesday*/

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class MenuController {

    private final LibraryController libraryController;
    private  final JFrame frame;

    public MenuController (LibraryController libraryController){
        this.libraryController = libraryController;
        this.frame = libraryController.frame;
    }

    public void showOptions(){
        try{
            String selectedOption = displayMenuOptions();
            handleUserChoice(selectedOption);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception);
        }
        showOptions();
    }

    private String displayMenuOptions() {

        String[] menuOptions = {
                "1. Create Book",
                "2. View Book",
                "3. Update Book",
                "4. Delete Book",
                "5. Create User Account",
                "6. Login at User Account",
                "7. View User Account",
                "8. Update User Account",
                "9. View my Borrowed book",
                "10. Borrow Book",
                "11. Return Book",
                "0. Quite"};

        String selectedOption = (String) JOptionPane.showInputDialog(
            this.frame,
            "Select one of options",
            "Menu options",
            JOptionPane.QUESTION_MESSAGE,
            null,
            menuOptions,
            menuOptions[0]
        );
        return selectedOption;
    }

    private void handleUserChoice(String selectedOption) throws Exception {

        switch (selectedOption){
            case "1. Create Book":
               libraryController.createBook();
                break;
            case "2. View Book":
                libraryController.viewBook();
                break;
            case "3. Update Book":
                libraryController.updateBook();
                break;
            case "4. Delete Book":
                libraryController.deleteBook();
                break;
            case "5. Create User Account":
                libraryController.collectNewUserData();
                break;
            case "6. Login at User Account":
                libraryController.collectLogInData();
                break;
            case "7. View User Account":
                libraryController.viewUser();
                break;
            case "8. Update User Account":
                libraryController.selectUserAccountDataForUpdate();
                break;
            case "9. View my Borrowed book":
                libraryController.viewAllBorrowedBooks();
                break;
            case "10. Borrow Book":
                libraryController.selectBookToBorrow();
                break;
            case "11. Return Book":
                libraryController.selectBookToReturn();
                break;
            case "0. Quite":
                System.exit(0);
                break;
            default:
                throw  new Exception ("Please choose valid option");
        }
    }



            // BOOKS
    // add book
    // view book
    // update book
    // delete book

            // USER
    // create user
    // log in user
    // update user
    // view user
    // search book
    // borrow book
    // return book

            // Library System

    // Create user account
    // Log in for users
    // Search for book
    // see book status
    // see new books


}
