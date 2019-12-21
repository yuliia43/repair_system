package controllers;

import commonlyUsedStrings.ErrorMessage;
import commonlyUsedStrings.PageLocation;
import dtos.SecureUser;
import exceptionHandling.validators.InputDataValidator;
import facade.UserFacade;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class AuthorisationController implements GetMethodController, PostMethodController {
    private static UserFacade userFacade = UserFacade.getUserFacade();
    private static Logger logger = Logger.getLogger(AuthorisationController.class);

    public String doGet(HttpServletRequest req) {
        return PageLocation.AUTHORISATION_PAGE;
    }

    public String doPost(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if(!InputDataValidator.authorisationDataNotEmpty(email, password)) {
            req.setAttribute("emptyField", true);
            return PageLocation.AUTHORISATION_PAGE;
        }
        SecureUser user = null;
        try {
            user = userFacade.checkAuthorizationInfo(email, password);
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
            return PageLocation.SQL_EXCEPTION;
        }
        if(user != null){
            req.getSession()
                    .setAttribute("user", user);
            logger.info("Authorisation success: " + user.getFirstName() + " " + user.getLastName());
            try {
                resp.sendRedirect("/userPage");
            } catch (IOException e) {
                logger.error(ErrorMessage.REDIRECT_FAIL);
                return PageLocation.SQL_EXCEPTION;
            }
            return null;
        }
        else{
            req.setAttribute("fail", true);
            return PageLocation.AUTHORISATION_PAGE;
        }
    }
}
