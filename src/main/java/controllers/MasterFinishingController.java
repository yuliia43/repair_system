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
import services.repositoryServices.ApplicationsService;
import services.repositoryServices.FeedbacksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 10.12.2019
 * @project repair_system
 */
public class MasterFinishingController implements GetMethodController, PostMethodController {
    private ApplicationsService applicationsService = ApplicationsService.getApplicationsService();
    private Logger logger = Logger.getLogger(MasterFinishingController.class);

    @Override
    public String doGet(HttpServletRequest req) {
        SecureUser user = (SecureUser) req.getSession().getAttribute("user");
        try {
            if (AuthorisationValidator.masterAuthorised(user)) {
                List<Application> applications = applicationsService
                        .getAllUnfinishedForMasterId(user.getUserId());
                req.setAttribute("applications", applications);
                return PageLocation.MASTERS_FINISHING_PAGE;
            }
        } catch (NotAuthorisedException e) {
            return PageLocation.NOT_AUTHORISED;
        } catch (IncorrectRoleException e) {
            return PageLocation.INCORRECT_ROLE;
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
            return PageLocation.SQL_EXCEPTION;
        }
        return PageLocation.MASTERS_FINISHING_PAGE;
    }

    @Override
    public String doPost(HttpServletRequest req, HttpServletResponse resp) {
        String[] applicationIds = req.getParameterValues("applicationId");
        if(!InputDataValidator.somethingIsChosen(applicationIds)){
            req.setAttribute("notChecked", true);
            return doGet(req);
        }
        else {
            try {
                applicationsService.updateAll(applicationIds);
            } catch (SQLException e) {
                logger.error(ErrorMessage.SQL_EXCEPTION);
                return PageLocation.SQL_EXCEPTION;
            }
        }
        return doGet(req);
    }
}
