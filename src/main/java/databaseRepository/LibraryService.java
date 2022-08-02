package databaseRepository;

import java.lang.ref.PhantomReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryService {

    Connection connection = new DatabaseManager().getConnection();
    int bookAmount;

    public void updateBorrowedBookDataDB(int bookAmount, int bookId) throws SQLException {
        String query = "UPDATE books SET bookAmount = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, bookAmount);
            preparedStatement.setInt(2, bookId);

            preparedStatement.execute();
    }

    public int receiveBookAmountDB(int bookId) throws SQLException {
        String query = "SELECT bookAmount FROM books WHERE id = " + bookId;

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            return bookAmount = resultSet.getInt("bookAmount");
        }
        return 0;
    }

    public void addBookToUserAccount(int bookId, int userId) throws SQLException {
        String query = "INSERT INTO bookManagementSystem (bookId, userId) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, bookId);
        preparedStatement.setInt(2, userId);

        preparedStatement.execute();
    }

    public void removeBookFromUserAccount(int bookId, int userId) throws SQLException {
        String query = "DELETE FROM bookManagementSystem WHERE bookId = ? AND userId = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, bookId);
        preparedStatement.setInt(2, userId);

        preparedStatement.execute();
    }
}
