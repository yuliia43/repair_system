package exceptionHandling.validators;

import dtos.SecureUser;
import enums.Role;
import exceptionHandling.exceptions.IncorrectRoleException;
import exceptionHandling.exceptions.NotAuthorisedException;
import factories.RolesFactory;
import models.User;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class AuthorisationValidator {

    private AuthorisationValidator() {
    }

    public static boolean anyRoleAuthorised(SecureUser user) throws NotAuthorisedException {
        if (user != null) return true;
        throw new NotAuthorisedException();
    }

    public static boolean userAuthorised(SecureUser user) throws NotAuthorisedException, IncorrectRoleException {
        anyRoleAuthorised(user);
        if (RolesFactory.getRole(user.getRole()) == Role.USER) return true;
        throw new IncorrectRoleException();
    }

    public static boolean managerAuthorised(SecureUser user) throws NotAuthorisedException, IncorrectRoleException {
        anyRoleAuthorised(user);
        if (RolesFactory.getRole(user.getRole()) == Role.MANAGER) return true;
        throw new IncorrectRoleException();
    }

    public static boolean masterAuthorised(SecureUser user) throws NotAuthorisedException, IncorrectRoleException {
        anyRoleAuthorised(user);
        if (RolesFactory.getRole(user.getRole()) == Role.MASTER) return true;
        throw new IncorrectRoleException();
    }

}
