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
import models.Application;
import org.apache.log4j.Logger;
import services.repositoryServices.ApplicationsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Yuliia Shcherbakova ON 09.12.2019
 * @project repair_system
 */
public class NewApplicationController implements GetMethodController, PostMethodController {
    private Logger logger = Logger.getLogger(NewApplicationController.class);

    @Override
    public String doGet(HttpServletRequest req) {
        SecureUser user = (SecureUser) req.getSession().getAttribute("user");
        try {
            if (AuthorisationValidator.userAuthorised(user))
                return PageLocation.NEW_APPLICATION_PAGE;
        } catch (NotAuthorisedException e) {
            return PageLocation.NOT_AUTHORISED;
        } catch (IncorrectRoleException e) {
            return PageLocation.INCORRECT_ROLE;
        }
        return PageLocation.NEW_APPLICATION_PAGE;
    }

    @Override
    public String doPost(HttpServletRequest req, HttpServletResponse resp) {
        String details = StringConverter.convertToUTF8(req.getParameter("details"));
        SecureUser user = (SecureUser) req.getSession().getAttribute("user");
        if(InputDataValidator.detailsDataNotEmpty(details)){
            ApplicationsService service = ApplicationsService.getApplicationsService();
            try {
                service.add(new Application()
                        .builder()
                        .userId(user.getUserId())
                        .repairDetails(details)
                        .status(Status.CREATED)
                        .build());
                resp.sendRedirect("/usersApplication");
            } catch (SQLException e) {
                logger.error(ErrorMessage.SQL_EXCEPTION);
                return PageLocation.SQL_EXCEPTION;
            } catch (IOException e) {
                logger.error(ErrorMessage.REDIRECT_FAIL);
                return PageLocation.SQL_EXCEPTION;
            }
            return null;
        }
        else {
            req.setAttribute("emptyFields", true);
            return PageLocation.NEW_APPLICATION_PAGE;
        }
    }
}
