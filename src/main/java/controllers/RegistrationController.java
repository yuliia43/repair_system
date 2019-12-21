package controllers;

import commonlyUsedStrings.ErrorMessage;
import commonlyUsedStrings.PageLocation;
import converters.StringConverter;
import encryption.UserEncryptor;
import exceptionHandling.validators.InputDataValidator;
import exceptionHandling.validators.MatchingValidator;
import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import services.repositoryServices.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class RegistrationController implements GetMethodController, PostMethodController {
    private static final UsersService service = UsersService.getUsersService();
    private static final Logger logger = Logger.getLogger(RegistrationController.class);

    public String doGet(HttpServletRequest req) {
        return PageLocation.REGISTRATION_PAGE;
    }

    public String doPost(HttpServletRequest req, HttpServletResponse resp) {
        String firstName = StringConverter.convertToUTF8(req.getParameter("firstName"));
        String lastName = StringConverter.convertToUTF8(req.getParameter("lastName"));
        String role = req.getParameter("role");
        String email = StringConverter.convertToUTF8(req.getParameter("email"));
        String password = StringConverter.convertToUTF8(req.getParameter("password"));
        String password2 = StringConverter.convertToUTF8(req.getParameter("passwordConfirmation"));
        if (!InputDataValidator.registrationDataNotEmpty(firstName, lastName, role, email,
                password, password2)) {
            req.setAttribute("emptyFields", true);
            setAttributes(req, firstName, lastName, role, email);
            return PageLocation.REGISTRATION_PAGE;
        }
        if (!InputDataValidator.registrationPasswordsEqual(password, password2)) {
            req.setAttribute("passwordsNotEqual", true);
            setAttributes(req, firstName, lastName, role, email);
            return PageLocation.REGISTRATION_PAGE;
        }
        if (!MatchingValidator.nameMatches(firstName)) {
            req.setAttribute("fnWrong", true);
            setAttributes(req, firstName, lastName, role, email);
            return PageLocation.REGISTRATION_PAGE;
        }
        if (!MatchingValidator.nameMatches(lastName)) {
            req.setAttribute("lnWrong", true);
            setAttributes(req, firstName, lastName, role, email);
            return PageLocation.REGISTRATION_PAGE;
        }
        if (!MatchingValidator.emailMatches(email)) {
            req.setAttribute("emailWrong", true);
            setAttributes(req, firstName, lastName, role, email);
            return PageLocation.REGISTRATION_PAGE;
        }
        try {
            User user =
                    new User()
                            .builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .role(role)
                            .email(email)
                            .password(password)
                            .build();
            UserEncryptor.getUserEncryptor().encrypt(user);
            service.add(user);
            resp.sendRedirect("/authorisation");
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
            return PageLocation.SQL_EXCEPTION;
        } catch (IOException e) {
            logger.error(ErrorMessage.REDIRECT_FAIL);
            return PageLocation.SQL_EXCEPTION;
        }
        return null;
    }

    private static void setAttributes(HttpServletRequest req, String firstName, String lastName, String role, String email) {
        req.setAttribute("firstName", firstName);
        req.setAttribute("lastName", lastName);
        req.setAttribute("role", role);
        req.setAttribute("email", email);
    }

}

