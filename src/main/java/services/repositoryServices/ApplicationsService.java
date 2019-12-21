package services.repositoryServices;

import commonlyUsedStrings.ErrorMessage;
import enums.Status;
import models.Application;
import models.Feedback;
import models.User;
import org.apache.log4j.Logger;
import repositories.ApplicationsRepository;
import repositories.FeedbacksRepository;
import services.TransactionService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 08.12.2019
 * @project repair_system
 */
public class ApplicationsService extends RepositoryService<Application> {

    private static final ApplicationsService APPLICATIONS_SERVICE = new ApplicationsService();
    private static Logger logger = Logger.getLogger(ApplicationsService.class);
    private FeedbacksService feedbackService = FeedbacksService.getFeedbacksService();
    private UsersService usersService = UsersService.getUsersService();

    private ApplicationsService() {
        super(new ApplicationsRepository());
    }

    public static ApplicationsService getApplicationsService() {
        return APPLICATIONS_SERVICE;
    }

    public List<Application> getAllByUserId(int userId) throws SQLException {
        List<Application> applications = ((ApplicationsRepository) this.repository).getAllByUserId(userId);
        applications.stream()
                .forEach((application -> {
                    if (application.getStatus().equals("in_process")
                            || application.getStatus().equals("finished")) {
                        setFeedback(application);
                    }
                    setManager(application);
                }));
        return applications;
    }

    public void setManager(Application application) {
        try {
            User manager = usersService.getOneById(application.getManagerId());
            application.setManager(manager);
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
        }
    }

    public void setFeedback(Application application) {
        try {
            Feedback feedback = feedbackService
                    .getAllByApplicationId(application.getApplicationId());
            application.setFeedback(feedback);
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
        }
    }

    public List<Application> getAllNotViewed() throws SQLException {
        return ((ApplicationsRepository) this.repository).getAllNotViewed();
    }

    public List<Application> getAllAccepted() throws SQLException {
        return ((ApplicationsRepository) this.repository).getAllAccepted();
    }

    public void updateAll(String[] applicationIds) throws SQLException {
        List<Application> applications = new ArrayList<>();
        for (String applicationId : applicationIds) {
            Application application = repository.getOneById(Integer.valueOf(applicationId));
            application.setStatus(Status.FINISHED);
            applications.add(application);
        }
        TransactionService.getTransactionService().updateAllTransaction(applications);
    }

    public List<Application> getAllMastersStats() throws SQLException {
        List<Application> stats = ((ApplicationsRepository) this.repository).getAllMastersStats();
        stats.stream()
                .forEach((application -> {
                    setFeedback(application);
                }));
        return stats;
    }

    public List<Application> getAllUnfinishedForMasterId(int userId) throws SQLException {
        List<Application> applications = ((ApplicationsRepository) repository)
                .getAllUnfinishedForMasterId(userId);
        applications.stream()
                .forEach((application -> {
                    setFeedback(application);
                    setManager(application);
                }));
        return applications;
    }
}
