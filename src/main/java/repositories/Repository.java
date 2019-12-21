package repositories;

import connectionPool.ConnectionPoolHolder;
import models.Application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public interface Repository<T> {
    void add(T item) throws SQLException;

    void add(T item, Connection connection) throws SQLException;

    void update(T item) throws SQLException;

    void update(T item, Connection connection) throws SQLException;

    List<T> getAll() throws SQLException;
    T getOneById(int id) throws SQLException;
    List<T> getItems(ResultSet resultSet) throws SQLException;

    default List<T> query(String query) throws SQLException {
        Connection connection = receiveConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<T> items = getItems(resultSet);
        connection.close();
        return items;
    }

    default Connection receiveConnection() throws SQLException {
        return ConnectionPoolHolder.receiveConnection();
    }

}
