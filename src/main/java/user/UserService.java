package user;

import book.Book;
import databaseRepository.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public String receiveUserLogInDataDB(String email, String password) throws Exception {
        String userName;
        String query = "SELECT userName FROM users WHERE email = ? AND password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
         return userName = resultSet.getString("userName");
        }
        return "User not found";
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
}

