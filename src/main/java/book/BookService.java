package book;

import databaseRepository.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookService {

    Connection connection = new DatabaseManager().getConnection();

    public void addBookDB(Book book) throws SQLException {
        String query = "INSERT INTO books (bookName, author, yearPublished, genre, description, status, bookAmount, specialMarks) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, book.getBookName());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getYearPublished());
        preparedStatement.setString(4, book.getGenre());
        preparedStatement.setString(5, book.getDescription());
        preparedStatement.setBoolean(6, book.getStatus());
        preparedStatement.setInt(7, book.getBookAmount());
        preparedStatement.setString(8, book.getSpecialMarks());

        preparedStatement.execute();
    }

    public ArrayList<Book> getSearchedBookDataDB(int bookId) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        String query = "SELECT * FROM books WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, bookId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Book book = new Book();

            book.setBookId(resultSet.getInt("id"));
            book.setBookName(resultSet.getString("bookName"));
            book.setAuthor(resultSet.getString("author"));
            book.setYearPublished(resultSet.getString("yearPublished"));
            book.setGenre(resultSet.getString("genre"));
            book.setDescription(resultSet.getString("description"));
            book.setStatus(resultSet.getBoolean("status"));
            book.setBookAmount(resultSet.getInt("bookAmount"));
            book.setSpecialMarks(resultSet.getString("specialMarks"));

            books.add(book);
    }
        return books;
}

    public void updateBookStatusDB(boolean status, int bookId)  throws SQLException {
        String query = "UPDATE books SET status = ? WHERE id = ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setBoolean(1, status);
        preparedStatement.setInt(2, bookId);

        preparedStatement.execute();

    }

    public void updateBookAmountDB(int bookAmount, int bookId) throws SQLException {
        String query = "UPDATE books SET bookAmount = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, bookAmount);
        preparedStatement.setInt(2, bookId);

        preparedStatement.execute();
    }

    public void deleteBookFromDB(int bookId) throws  SQLException {
        String query = "DELETE FROM books WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, bookId);

        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Book with ID: " + bookId + " not found");
        }
    }
}

