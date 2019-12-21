package services.repositoryServices;

import commonlyUsedStrings.ErrorMessage;
import models.Feedback;
import models.User;
import org.apache.log4j.Logger;
import repositories.FeedbacksRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 09.12.2019
 * @project repair_system
 */
public class FeedbacksService extends RepositoryService<Feedback> {

    private static final FeedbacksService FEEDBACKS_SERVICE = new FeedbacksService();
    private static Logger logger = Logger.getLogger(FeedbacksService.class);
    private UsersService usersService = UsersService.getUsersService();

    private FeedbacksService() {
        super(new FeedbacksRepository());
    }

    public static FeedbacksService getFeedbacksService() {
        return FEEDBACKS_SERVICE;
    }

    @Override
    public List<Feedback> getAll() throws SQLException {
        List<Feedback> feedbacks = super.getAll();
        addMasterObjectsToFeedbacks(feedbacks);
        return feedbacks;
    }

    public Feedback getAllByApplicationId(int applicationId) throws SQLException {
        Feedback feedbacks = ((FeedbacksRepository) repository).getOneByApplicationId(applicationId);
        setMaster(feedbacks);
        return feedbacks;
    }

    private void addMasterObjectsToFeedbacks(List<Feedback> feedbacks) {
        feedbacks.stream()
                .forEach(feedback -> {
                    setMaster(feedback);
                });
    }



    private void setMaster(Feedback feedback) {
        try {
            User user = usersService.getOneById(feedback.getMasterId());
            feedback.setMaster(user);
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
        }
    }


    public void setFeedback(Feedback feedback) throws SQLException {
        ((FeedbacksRepository) repository).update(feedback);
    }
}
