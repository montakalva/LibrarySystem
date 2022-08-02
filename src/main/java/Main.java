/*      A library management system
        It should be possible to view, add, delete, update and remove books
        It should be possible to borrow books from library,
        It should be possible to borrow more than 1 copy of the same book from the library
        If a user already borrowed a book, it should not be possible to borrow another copy of the same book
        All books should be stored in Database
        It should be able to see the currently borrowed books by a single user
        It should be possible return borrowed books
        All changes made in application should be recorded.
        E.g user borrowed book, 5pm Sunday
        Username returned book, 2 am Tuesday*/

import book.BookService;
import databaseRepository.LibraryService;
import libraryManagementSystem.LibraryController;
import libraryManagementSystem.MenuController;
import user.UserService;

public class Main {
    public static void main(String[] args) {

        LibraryController libraryController = new LibraryController(
                new BookService(),
                new UserService(),
                new LibraryService()
        );

        MenuController menuController = new MenuController(libraryController);
        menuController.showOptions();

    }
}
