package controllers;

import commonlyUsedStrings.ErrorMessage;
import commonlyUsedStrings.PageLocation;
import dtos.SecureUser;
import exceptionHandling.exceptions.NotAuthorisedException;
import exceptionHandling.validators.AuthorisationValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class UserPageController implements GetMethodController {

    public String doGet(HttpServletRequest req) {
        SecureUser user = (SecureUser) req.getSession().getAttribute("user");
        try {
            if (AuthorisationValidator.anyRoleAuthorised(user))
                return PageLocation.USER_INFO;
        } catch (NotAuthorisedException e) {
            return PageLocation.NOT_AUTHORISED;
        }
        return null;
    }
}
