package repositories;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class UsersRepository implements Repository<User> {

    @Override
    public void add(User item) throws SQLException {
        Connection connection = receiveConnection();
        add(item, connection);
        connection.close();
    }

    @Override
    public void add(User item, Connection connection) throws SQLException {
        String sqlAdd = "INSERT INTO Users (first_name, last_name, `role`, email,`password`, salt) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlAdd);
        preparedStatement.setString(1, item.getFirstName());
        preparedStatement.setString(2, item.getLastName());
        preparedStatement.setString(3, item.getRole());
        preparedStatement.setString(4, item.getEmail());
        preparedStatement.setString(5, item.getPassword());
        preparedStatement.setString(6, item.getSalt());
        preparedStatement.execute();
    }

    @Override
    public void update(User item) throws SQLException {
        Connection connection = receiveConnection();
        update(item, connection);
        connection.close();
    }

    @Override
    public void update(User item, Connection connection) throws SQLException {
        String sqlUpdate = "UPDATE Users " +
                "SET first_name = ?, last_name = ?, role = ?, email = ?, `password` = ?, salt = ? " +
                "WHERE user_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
        preparedStatement.setString(1, item.getFirstName());
        preparedStatement.setString(2, item.getLastName());
        preparedStatement.setString(3, item.getRole());
        preparedStatement.setString(4, item.getEmail());
        preparedStatement.setString(5, item.getPassword());
        preparedStatement.setString(6, item.getSalt());
        preparedStatement.setInt(7, item.getUserId());
        preparedStatement.execute();
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sqlSelect = "SELECT * FROM Users;";
        return query(sqlSelect);
    }

    @Override
    public User getOneById(int id) throws SQLException {
        String sqlSelect = "SELECT * FROM Users WHERE user_id = ?;";
        Connection connection = receiveConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> items = getItems(resultSet);
        connection.close();
        if (items.size() != 0) return items.get(0);
        else return null;
    }

    @Override
    public List<User> getItems(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            int userId = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String role = resultSet.getString(4);
            String email = resultSet.getString(5);
            String password = resultSet.getString(6);
            String salt = resultSet.getString(7);

            User user = new User().builder()
                    .userId(userId)
                    .firstName(firstName)
                    .lastName(lastName)
                    .role(role)
                    .email(email)
                    .password(password)
                    .salt(salt)
                    .build();

            users.add(user);
        }
        return users;
    }

    public User getUserByEmail(String email) throws SQLException {
        String sqlSelect = "SELECT * FROM Users WHERE email= ?;";
        Connection connection = receiveConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = getItems(resultSet);
        connection.close();
        if (users.size() != 0)
            return users.get(0);
        else return null;
    }

}
