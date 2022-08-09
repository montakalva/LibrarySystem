package databaseRepository;

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
        System.out.println("Book returned successfully");
    }

    public int checkIfBookIsTheSameAsSelected(int userId, int bookId) throws SQLException {
        String query = "SELECT id FROM bookManagementSystem WHERE userId = ? AND bookId = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, bookId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            return resultSet.getInt("id");
        }
      return 0;
    }

    public void confirmationBookBorrowed(int bookId, int userId) throws SQLException {
            String query = "SELECT DATE_FORMAT(actionAt, '%I%p %W') AS actionAt FROM bookManagementSystem WHERE bookId = ? AND userId = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                String borrowedAt = resultSet.getString("actionAt");
                String output = "You borrowed book successfully, %s";
                System.out.println(String.format(output, borrowedAt));

        }
    }
}

