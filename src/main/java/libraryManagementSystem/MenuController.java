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
      -  All changes made in application should be recorded.
        E.g user borrowed book, 5pm Sunday
        Username returned book, 2 am Tuesday*/

import javax.swing.*;

public class MenuController {

    private final LibraryController libraryController;
    private  final JFrame frame;

    public MenuController (LibraryController libraryController){
        this.libraryController = libraryController;
        this.frame = libraryController.frame;
    }

    public void startApplication(){
        try{
            String logInOption = showLoginOptions();
            handleUserLogInChoice(logInOption);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception);
        }
    }

    public void showOptions(){
        try {
            String selectedOption = displayMenuOptions();
            handleUserChoice(selectedOption);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception);
        }
        showOptions();
    }

    public String showLoginOptions() {
        JOptionPane.showMessageDialog(this.frame, "Welcome to Library Management System!");

        String[] appAccessOptions = {
                "Create User Account",
                "Login at User Account",
                "Continue without Account",
                "Quite"
        };

       String logInOption = (String) JOptionPane.showInputDialog(
                this.frame,
                "Select one of options",
                "Login options",
                JOptionPane.QUESTION_MESSAGE,
                null,
                appAccessOptions,
                appAccessOptions[0]
                );

       return logInOption;
    }

    private void handleUserLogInChoice(String logInOption) throws Exception {

        switch (logInOption) {
            case "Create User Account":
                libraryController.collectNewUserData();
                showOptions();
                break;
            case "Login at User Account":
                libraryController.collectLogInData();
                showOptions();
                break;
            case "Continue without Account":
                libraryController.displayAllLibraryBooks();
                break;
            case "0. Quite":
                System.exit(0);
                break;
            default:
                throw new Exception("Please choose valid option");
        }
    }

    private String displayMenuOptions() {

        String[] menuOptions = {
                "Create Book",
                "View Book",
                "View All Library books",
                "Update Book",
                "Delete Book",
                "View my Account",
                "Update my Account",
                "View my Borrowed books",
                "Borrow Book",
                "Return Book",
                "Quite"
        };

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
            case "Create Book":
               libraryController.createBook();
                break;
            case "View Book":
                libraryController.viewBook();
                break;
            case "View All Library books":
                libraryController.displayAllLibraryBooks();
                break;
            case "Update Book":
                libraryController.updateBook();
                break;
            case "Delete Book":
                libraryController.deleteBook();
                break;
            case "View my Account":
                libraryController.viewUser();
                break;
            case "Update my Account":
                libraryController.selectUserAccountDataForUpdate();
                break;
            case "View my Borrowed books":
                libraryController.viewAllBorrowedBooks();
                break;
            case "Borrow Book":
                libraryController.selectBookToBorrow();
                break;
            case "Return Book":
                libraryController.selectBookToReturn();
                break;
            case "Quite":
                System.exit(0);
                break;
            default:
                throw  new Exception ("Please choose valid option");
        }
    }
}
