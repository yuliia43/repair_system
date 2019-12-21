package controllers;

import commonlyUsedStrings.ErrorMessage;
import commonlyUsedStrings.PageLocation;
import dtos.SecureUser;
import exceptionHandling.exceptions.IncorrectRoleException;
import exceptionHandling.exceptions.NotAuthorisedException;
import exceptionHandling.validators.AuthorisationValidator;
import exceptionHandling.validators.InputDataValidator;
import models.Application;
import models.Feedback;
import org.apache.log4j.Logger;
import services.TransactionService;
import services.repositoryServices.ApplicationsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 09.12.2019
 * @project repair_system
 */
public class MasterApplicationController implements GetMethodController, PostMethodController {
    private ApplicationsService applicationsService = ApplicationsService.getApplicationsService();
    private Logger logger = Logger.getLogger(MasterApplicationController.class);

    @Override
    public String doGet(HttpServletRequest req) {
        SecureUser user = (SecureUser) req.getSession().getAttribute("user");
        try {
            if (AuthorisationValidator.masterAuthorised(user)) {
                List<Application> applications = applicationsService.getAllAccepted();
                req.setAttribute("applications", applications);
                return PageLocation.MASTERS_APPLICATION_PAGE;
            }
        } catch (NotAuthorisedException e) {
            return PageLocation.NOT_AUTHORISED;
        } catch (IncorrectRoleException e) {
            return PageLocation.INCORRECT_ROLE;
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
            return PageLocation.SQL_EXCEPTION;
        }
        return PageLocation.MASTERS_APPLICATION_PAGE;
    }

    @Override
    public String doPost(HttpServletRequest req, HttpServletResponse resp) {
        SecureUser user = (SecureUser) req.getSession().getAttribute("user");
        String[] applicationIds = req.getParameterValues("applicationId");
        if(!InputDataValidator.somethingIsChosen(applicationIds)){
            req.setAttribute("notChecked", true);
            return doGet(req);
        }
        else {
            List<Feedback> feedbacks = new ArrayList<>();
            for (String applicationId : applicationIds) {
                feedbacks.add(new Feedback().builder()
                        .applicationId(Integer.valueOf(applicationId))
                        .masterId(user.getUserId())
                        .build());
            }
            TransactionService service = TransactionService.getTransactionService();
            try {
                service.takeApplication(feedbacks);
            } catch (SQLException e) {
                logger.error(ErrorMessage.SQL_EXCEPTION);
                return PageLocation.SQL_EXCEPTION;
            }
            return doGet(req);
        }
    }
}
