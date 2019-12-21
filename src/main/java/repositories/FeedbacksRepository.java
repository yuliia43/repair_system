package repositories;

import models.Application;
import models.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 08.12.2019
 * @project repair_system
 */
public class FeedbacksRepository<T> implements Repository<Feedback> {
    @Override
    public void add(Feedback item) throws SQLException {
        Connection connection = receiveConnection();
        add(item, connection);
        connection.close();
    }

    @Override
    public void add(Feedback item, Connection connection) throws SQLException {
        String sqlAdd = "INSERT INTO Feedbacks(application_id, master_id, feedback) " +
                "VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlAdd);
        preparedStatement.setInt(1, item.getApplicationId());
        preparedStatement.setInt(2, item.getMasterId());
        preparedStatement.setString(3, item.getFeedback());
        preparedStatement.execute();
    }


    @Override
    public void update(Feedback item) throws SQLException {
        Connection connection = receiveConnection();
        update(item, connection);
        connection.close();
    }

    @Override
    public void update(Feedback item, Connection connection) throws SQLException {
        String sqlUpdate = "UPDATE Feedbacks " +
                "SET feedback = ? " +
                "WHERE feedback_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
        preparedStatement.setString(1, item.getFeedback());
        preparedStatement.setInt(2, item.getFeedbackId());
        preparedStatement.execute();
    }

    @Override
    public List<Feedback> getAll() throws SQLException {
        String sqlSelect = "SELECT * FROM Feedbacks;";
        return query(sqlSelect);
    }

    @Override
    public Feedback getOneById(int id) throws SQLException {
        String sqlSelect = "SELECT * FROM feedbacks WHERE feedback_id = ?;";
        Connection connection = receiveConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Feedback> items = getItems(resultSet);
        connection.close();
        if (items.size() != 0) return items.get(0);
        else return null;
    }

    @Override
    public List<Feedback> getItems(ResultSet resultSet) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        while (resultSet.next()) {
            int feedbackId = resultSet.getInt(1);
            int applicationId = resultSet.getInt(2);
            int masterId = resultSet.getInt(3);
            String feedbackString = resultSet.getString(4);

            Feedback feedback = new Feedback().builder()
                    .feedbackId(feedbackId)
                    .applicationId(applicationId)
                    .masterId(masterId)
                    .feedback(feedbackString)
                    .build();

            feedbacks.add(feedback);
        }
        return feedbacks;
    }

    public Feedback getOneByApplicationId(int applicationId) throws SQLException {
        String sqlSelect = "SELECT * FROM feedbacks WHERE application_id = ?;";
        Connection connection = receiveConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setInt(1, applicationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Feedback> items = getItems(resultSet);
        connection.close();
        if (items.size() != 0) return items.get(0);
        else return null;
    }


}
