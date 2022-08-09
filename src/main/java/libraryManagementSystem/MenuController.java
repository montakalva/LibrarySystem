package libraryManagementSystem;

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

    public void showOptions(int userId){
        try {
            String selectedOption = displayMenuOptions();
            handleUserChoice(selectedOption, userId);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(frame, exception);
        }
        showOptions(userId);
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
                int userId = libraryController.collectNewUserData();
                showOptions(userId);
                break;
            case "Login at User Account":
                userId = libraryController.collectLogInData();
                showOptions(userId);
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

    private void handleUserChoice(String selectedOption, int userId) throws Exception {

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
                libraryController.viewUser(userId);
                break;
            case "Update my Account":
                libraryController.selectUserAccountDataForUpdate(userId);
                break;
            case "View my Borrowed books":
                libraryController.viewAllBorrowedBooks(userId);
                break;
            case "Borrow Book":
                libraryController.selectBookToBorrow(userId);
                break;
            case "Return Book":
                libraryController.selectBookToReturn(userId);
                break;
            case "Quite":
                System.exit(0);
                break;
            default:
                throw  new Exception ("Please choose valid option");
        }
    }
}
