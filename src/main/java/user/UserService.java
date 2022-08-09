package user;

import databaseRepository.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;

public class UserService {

    Connection connection = new DatabaseManager().getConnection();
    ArrayList<User> userList = new ArrayList<>();

    public void addUserDB(User user) throws SQLException {
        String query = "INSERT INTO users (userName, password, age, email, phoneNumber, specialMarks) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPhoneNumber());
        preparedStatement.setString(6, user.getSpecialMarks());

        preparedStatement.execute();
    }

    public int receiveUserLogInDataDB(String email, String password) throws Exception {
        int userId;
        String query = "SELECT id FROM users WHERE email = ? AND password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
        userId = resultSet.getInt("id");
        return userId;
        } else {
            return 0;
        }
    }

    public void updateUserNameDB(String userName, int userId, String password) throws SQLException {
        String query = "UPDATE users SET userName = ? WHERE id = ? AND password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userName);
        preparedStatement.setInt(2, userId);
        preparedStatement.setString(3, password);

        preparedStatement.execute();
    }

    public void updateUserEmailDB(String newEmail, int userId, String password) throws SQLException {
        String query = "UPDATE users SET email = ? WHERE id = ? AND password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newEmail);
        preparedStatement.setInt(2, userId);
        preparedStatement.setString(3, password);

        preparedStatement.execute();
    }

    public ArrayList<User> getUserDataDB(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            User user = new User();

            user.setUserId(resultSet.getInt("id"));
            user.setUserName(resultSet.getString("userName"));
            user.setPassword(resultSet.getString("password"));
            user.setAge(resultSet.getInt("age"));
            user.setEmail(resultSet.getString("email"));
            user.setPhoneNumber(resultSet.getString("phoneNumber"));
            user.setSpecialMarks(resultSet.getString("specialMarks"));

            userList.add(user);
        }
        return userList;
    }

    public void getUserBorrowedBookDataDB(int userId) throws SQLException {
        String query = "SELECT userName, userId, bookId, bookName, author, DATE_FORMAT(actionAt, '%I%p %W') AS actionAt FROM bookManagementSystem\n" +
                "INNER JOIN books\n" +
                "INNER JOIN users\n" +
                "ON users.id = bookManagementSystem.userId\n" +
                "AND books.id = bookManagementSystem.bookId\n" +
                "WHERE users.id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Borrowed books: \n" );

        while (resultSet.next()){
            String userName = resultSet.getString("userName");
            userId = resultSet.getInt("userId");
            int bookId = resultSet.getInt("bookId");
            String bookName = resultSet.getString("bookName");
            String author = resultSet.getString("author");
            String borrowedAt = resultSet.getString("actionAt");
            String output = "User | %s \t | User ID | %d \t Book ID | %d \t Book Title | %s \t Author | %s \t Borrowed At | %s";
            System.out.println(String.format(output, userName, userId, bookId, bookName, author, borrowedAt));
        }
    }

    public int getUsersBorrowedBookAmount(int userId) throws SQLException {
        int usersBorrowedBookAmount = 0;

        String query = "SELECT COUNT(id) FROM bookManagementSystem WHERE userId = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            usersBorrowedBookAmount = resultSet.getInt("count(id)");
        }
        return usersBorrowedBookAmount;
    }

    public int returnNewUserID(String email, String password) throws SQLException {
        int userId;

        String query = "SELECT id FROM users WHERE email = ? and password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
           return userId = resultSet.getInt("id");
        } else {
            return 0;
        }
    }
}

