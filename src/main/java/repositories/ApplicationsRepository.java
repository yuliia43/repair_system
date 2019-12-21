package repositories;


import enums.Status;
import factories.StatusFactory;
import models.Application;
import models.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class ApplicationsRepository implements Repository<Application> {

    @Override
    public void add(Application item) throws SQLException {
        Connection connection = receiveConnection();
        add(item, connection);
        connection.close();
    }

    @Override
    public void add(Application item, Connection connection) throws SQLException {
        String sqlAdd = "INSERT INTO Applications(user_id, repair_details, `status`) " +
                "VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlAdd);
        preparedStatement.setInt(1, item.getUserId());
        preparedStatement.setString(2, item.getRepairDetails());
        preparedStatement.setString(3, item.getStatus());
        preparedStatement.execute();
    }

    @Override
    public void update(Application item) throws SQLException {
        Connection connection = receiveConnection();
        update(item, connection);
        connection.close();
    }

    @Override
    public void update(Application item, Connection connection) throws SQLException {
        String sqlUpdate = "UPDATE Applications " +
                "SET `status` = ?, manager_id = ?, price = ?,  manager_details = ? " +
                "WHERE application_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
        preparedStatement.setString(1, item.getStatus());
        Integer managerId = item.getManagerId();
        if (managerId != null)
            preparedStatement.setInt(2, managerId);
        else
            preparedStatement.setNull(2, Types.INTEGER);
        Integer price = item.getPrice();
        if (price != null)
            preparedStatement.setInt(3, price);
        else
            preparedStatement.setNull(3, Types.INTEGER);
            preparedStatement.setString(4, item.getManagerDetails());
        preparedStatement.setInt(5, item.getApplicationId());
        preparedStatement.execute();
    }

    public void updateStatus(Application item, Connection connection) throws SQLException {
        String sqlUpdate = "UPDATE Applications " +
                "SET `status` = ? " +
                "WHERE application_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
        preparedStatement.setString(1, item.getStatus());
        preparedStatement.setInt(2, item.getApplicationId());
        preparedStatement.execute();
    }

    @Override
    public List<Application> getAll() throws SQLException {
        String sqlSelect = "SELECT * FROM Applications;";
        return query(sqlSelect);
    }

    @Override
    public Application getOneById(int id) throws SQLException {
        String sqlSelect = "SELECT * FROM Applications WHERE application_id = ?;";
        Connection connection = receiveConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Application> items = getItems(resultSet);
        connection.close();
        if (items.size() != 0) return items.get(0);
        else return null;
    }

    @Override
    public List<Application> getItems(ResultSet resultSet) throws SQLException {
        List<Application> applications = new ArrayList<>();
        while (resultSet.next()) {
            int applicationId = resultSet.getInt(1);
            int userId = resultSet.getInt(2);
            String repairDetails = resultSet.getString(3);
            String statusString = resultSet.getString(4);
            int managerId = resultSet.getInt(5);
            Integer price = resultSet.getInt(6);
            String managerDetails = resultSet.getString(7);
            Status status = StatusFactory.getStatus(statusString);

            Application application = new Application().builder()
                    .applicationId(applicationId)
                    .userId(userId)
                    .repairDetails(repairDetails)
                    .status(status)
                    .managerId(managerId)
                    .price(price)
                    .managerDetails(managerDetails)
                    .build();

            applications.add(application);
        }
        return applications;
    }

    public List<Application> getAllByUserId(int userId) throws SQLException {
        String sqlSelect = "SELECT * FROM Applications WHERE user_id = ?;";
        Connection connection = receiveConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Application> items = getItems(resultSet);
        connection.close();
        return items;
    }

    public List<Application> getAllNotViewed() throws SQLException {
        String sqlSelect = "select * from applications where `status`='created';";
        return query(sqlSelect);
    }

    public List<Application> getAllAccepted() throws SQLException {
        String sqlSelect = "select * from applications where `status`='accepted';";
        return query(sqlSelect);
    }

    public List<Application> getAllMastersStats() throws SQLException {
        String sqlSelect = "select * from applications where " +
                "`status`='in_process' or `status`='finished';";
        return query(sqlSelect);
    }

    public List<Application> getAllUnfinishedForMasterId(int userId) throws SQLException {
        String sqlSelect = "SELECT  applications.* FROM applications " +
                "join feedbacks on applications.application_id = feedbacks.application_id " +
                "where master_id = ? and applications.status = 'in_process';";
        Connection connection = receiveConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Application> items = getItems(resultSet);
        connection.close();
        return items;

    }
}
