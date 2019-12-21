package services.repositoryServices;

import models.User;
import repositories.UsersRepository;

import java.sql.SQLException;

/**
 * @author Yuliia Shcherbakova ON 22.07.2019
 * @project publishing
 */
public class UsersService extends RepositoryService<User> {

    private static final UsersService USERS_SERVICE = new UsersService();

    private UsersService() {
        super(new UsersRepository());
    }

    public static UsersService getUsersService() {
        return USERS_SERVICE;
    }

    public User getUserByEmail(String email) throws SQLException {
        return ((UsersRepository)repository).
                getUserByEmail(email);
    }

}
