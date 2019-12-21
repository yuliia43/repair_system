package controllers;

import commonlyUsedStrings.ErrorMessage;
import commonlyUsedStrings.PageLocation;
import dtos.SecureUser;
import exceptionHandling.exceptions.IncorrectRoleException;
import exceptionHandling.exceptions.NotAuthorisedException;
import exceptionHandling.validators.AuthorisationValidator;
import models.Application;
import models.Feedback;
import org.apache.log4j.Logger;
import services.repositoryServices.ApplicationsService;
import services.repositoryServices.FeedbacksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 09.12.2019
 * @project repair_system
 */
public class UsersApplicationController implements GetMethodController, PostMethodController {
    private ApplicationsService applicationsService = ApplicationsService.getApplicationsService();
    private FeedbacksService feedbacksService = FeedbacksService.getFeedbacksService();
    private static final Logger logger = Logger.getLogger(RegistrationController.class);

    @Override
    public String doGet(HttpServletRequest req) {
        SecureUser user = (SecureUser) req.getSession().getAttribute("user");
        try {
            if (AuthorisationValidator.userAuthorised(user)) {
                List<Application> applications = applicationsService.getAllByUserId(user.getUserId());
                req.setAttribute("applications", applications);
                return PageLocation.USERS_APPLICATION_PAGE;
            }
        } catch (NotAuthorisedException e) {
            return PageLocation.NOT_AUTHORISED;
        } catch (IncorrectRoleException e) {
            return PageLocation.INCORRECT_ROLE;
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
            return PageLocation.SQL_EXCEPTION;
        }
        return PageLocation.USERS_APPLICATION_PAGE;
    }

    @Override
    public String doPost(HttpServletRequest req, HttpServletResponse resp) {
        int feedbackId = Integer.parseInt(req.getParameter("feedbackId"));
        String feedback = req.getParameter("feedback");
        Feedback feedbackObj = new Feedback().builder()
                .feedbackId(feedbackId)
                .feedback(feedback)
                .build();
        try {
            feedbacksService.setFeedback(feedbackObj);
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
            return PageLocation.SQL_EXCEPTION;
        }
        return doGet(req);
    }
}
