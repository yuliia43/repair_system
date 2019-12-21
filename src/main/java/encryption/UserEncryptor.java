package encryption;

import models.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

/**
 * @author Yuliia Shcherbakova ON 14.12.2019
 * @project repair_system
 */
public class UserEncryptor implements Encryptor<User> {

    public static final UserEncryptor userEncryptor = new UserEncryptor();

    private UserEncryptor(){}

    @Override
    public void encrypt(User user) {
        String salt = SecureRandom.getSeed(50).toString();
        String password = salt.concat(user.getPassword());
        user.setSalt(salt);
        user.setPassword(DigestUtils.md5Hex(password));
    }

    @Override
    public boolean checkIfDecryptedEqualEncrypted(User user, String decrypted) {
        decrypted = user.getSalt().concat(decrypted);
        decrypted = DigestUtils.md5Hex(decrypted);
        return (decrypted.equals(user.getPassword()));
    }

    public static UserEncryptor getUserEncryptor() {
        return userEncryptor;
    }
}
