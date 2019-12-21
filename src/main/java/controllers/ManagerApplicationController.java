package controllers;

import commonlyUsedStrings.ErrorMessage;
import commonlyUsedStrings.PageLocation;
import converters.StringConverter;
import dtos.SecureUser;
import enums.Status;
import exceptionHandling.exceptions.IncorrectRoleException;
import exceptionHandling.exceptions.NotAuthorisedException;
import exceptionHandling.validators.AuthorisationValidator;
import exceptionHandling.validators.InputDataValidator;
import factories.StatusFactory;
import models.Application;
import org.apache.log4j.Logger;
import services.repositoryServices.ApplicationsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 09.12.2019
 * @project repair_system
 */
public class ManagerApplicationController implements GetMethodController, PostMethodController {
    private ApplicationsService applicationsService = ApplicationsService.getApplicationsService();
    private Logger logger = Logger.getLogger(ManagerApplicationController.class);

    @Override
    public String doGet(HttpServletRequest req) {
        SecureUser user = (SecureUser) req.getSession().getAttribute("user");
        try {
            if (AuthorisationValidator.managerAuthorised(user)) {
                List<Application> applications = applicationsService.getAllNotViewed();
                req.setAttribute("applications", applications);
            }
        } catch (NotAuthorisedException e) {
            return PageLocation.NOT_AUTHORISED;
        } catch (IncorrectRoleException e) {
            return PageLocation.INCORRECT_ROLE;
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
            return PageLocation.SQL_EXCEPTION;
        }
        return PageLocation.MANAGERS_APPLICATION_PAGE;
    }

    @Override
    public String doPost(HttpServletRequest req, HttpServletResponse resp) {
        SecureUser user = (SecureUser) req.getSession().getAttribute("user");
        boolean confirm = Boolean.parseBoolean(req.getParameter("confirm"));
        boolean reject = Boolean.parseBoolean(req.getParameter("reject"));
        int applicationId = Integer.parseInt(req.getParameter("applicationId"));
        String priceStr = req.getParameter("price");
        String details = req.getParameter("confirmDetails");
        Integer price = null;
        String statusString = null;
        if (confirm) {
            if (!InputDataValidator.detailsDataNotEmpty(priceStr)) {
                req.setAttribute("emptyFields", true);
                req.setAttribute("price", price);
                req.setAttribute("details", details);
                return doGet(req);
            } else {
                price = Integer.parseInt(priceStr);
                details = StringConverter.convertToUTF8(req.getParameter("confirmDetails"));
                statusString = "accepted";
            }
        } else if (reject) {
            if (!InputDataValidator.detailsDataNotEmpty(details)) {
                req.setAttribute("emptyFields", true);
                return doGet(req);
            } else {
                details = StringConverter.convertToUTF8(req.getParameter("rejectDetails"));
                statusString = "refused";
            }
        }

        Status status = StatusFactory.getStatus(statusString);
        Application application = new Application()
                .builder()
                .applicationId(applicationId)
                .price(price)
                .status(status)
                .managerId(user.getUserId())
                .managerDetails(details)
                .build();
        try {
            applicationsService.update(application);
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
            return PageLocation.SQL_EXCEPTION;
        }
        return doGet(req);
    }
}
