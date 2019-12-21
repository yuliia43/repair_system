package facade;

import dtos.SecureUser;
import encryption.UserEncryptor;
import factories.RolesFactory;
import models.User;
import services.repositoryServices.UsersService;

import java.sql.SQLException;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class UserFacade {
    private static final UserFacade userFacade = new UserFacade();
    private final UsersService usersService = UsersService.getUsersService();

    private UserFacade() {
    }

    public static UserFacade getUserFacade() {
        return userFacade;
    }

    private SecureUser getSecureUserDto(User user) {
        if(user == null)
            return null;
        SecureUser secureUser = new SecureUser();
        secureUser.setUserId(user.getUserId());
        secureUser.setFirstName(user.getFirstName());
        secureUser.setLastName(user.getLastName());
        secureUser.setRole(RolesFactory.getRole(user.getRole()));
        secureUser.setEmail(user.getEmail());
        return secureUser;
    }

    public SecureUser checkAuthorizationInfo(String email, String password) throws SQLException {
        User user = usersService.getUserByEmail(email);
        if(user == null) return null;
        else {
            boolean authorised = UserEncryptor
                    .getUserEncryptor().checkIfDecryptedEqualEncrypted(user, password);
            if (!authorised)
                return null;
            return getSecureUserDto(user);
        }
    }

    public SecureUser getOneById(int id) throws SQLException {
        User user = usersService.getOneById(id);
        return getSecureUserDto(user);
    }

}
