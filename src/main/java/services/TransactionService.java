package services;

import commonlyUsedStrings.ErrorMessage;
import connectionPool.ConnectionPoolHolder;
import enums.Status;
import models.Application;
import models.Feedback;
import org.apache.log4j.Logger;
import repositories.ApplicationsRepository;
import repositories.FeedbacksRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 10.12.2019
 * @project repair_system
 */
public class TransactionService {
    private static final TransactionService transactionService = new TransactionService();
    private FeedbacksRepository feedbacksRepository = new FeedbacksRepository();
    private ApplicationsRepository applicationsRepository = new ApplicationsRepository();
    private Logger logger = Logger.getLogger(TransactionService.class);

    private TransactionService() {
    }

    public static TransactionService getTransactionService() {
        return transactionService;
    }


    public void takeApplication(List<Feedback> feedbacks) throws SQLException {
        Connection connection = ConnectionPoolHolder.receiveConnection();
        if (!unsetAutoCommit(connection)) return;
        for (Feedback feedback :
                feedbacks) {
            if (!addFeedback(connection, feedback)) return;
            Application application = new Application().builder()
                    .applicationId(feedback.getApplicationId())
                    .status(Status.IN_PROCESS)
                    .build();
            if (!updateApplicationStatus(connection, application)) return;
        }
        if (!commitChanges(connection)) return;
        closeConnection(connection);
    }

    private boolean commitChanges(Connection connection) {
        try {
            connection.commit();
            logger.info("TakeApplication transaction commited");
        } catch (SQLException e) {
            logger.error(ErrorMessage.TRANSACTION_COMMIT_FAIL);
            setAutoCommit(connection);
            return false;
        }
        return true;
    }

    private boolean updateApplicationStatus(Connection connection, Application application) {
        try {
            applicationsRepository.updateStatus(application, connection);
        } catch (SQLException e) {
            logger.error(ErrorMessage.APPLICATION_UPDATING_FAIL);
            setAutoCommit(connection);
            return false;
        }
        return true;
    }

    private boolean addFeedback(Connection connection, Feedback feedback) {
        try {
            feedbacksRepository.add(feedback, connection);
        } catch (SQLException e) {
            logger.error(ErrorMessage.FEEDBACK_ADDING_FAIL);
            setAutoCommit(connection);
            return false;
        }
        return true;
    }

    private boolean unsetAutoCommit(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error(ErrorMessage.AUTO_COMMIT_FALSE_FAIL);
            closeConnection(connection);
            return false;
        }
        return true;
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(ErrorMessage.CLOSING_CONNECTION_FAIL);
        }
    }

    private void setAutoCommit(Connection connection) {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            logger.error(ErrorMessage.ROLLBACK_FAIL);
        } finally {
            closeConnection(connection);
        }
    }

    public void updateAllTransaction(List<Application> applications) throws SQLException {
        Connection connection = ConnectionPoolHolder.receiveConnection();
        if(!unsetAutoCommit(connection)) return;
        for (Application application :
                applications) {
            try {
                applicationsRepository.update(application);
            } catch (SQLException e) {
                logger.error(ErrorMessage.APPLICATION_UPDATING_FAIL);
                setAutoCommit(connection);
                return;
            }
        }
        if (!commitChanges(connection)) return;
        closeConnection(connection);
    }
}
