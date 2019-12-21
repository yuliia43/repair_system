package encryption;

/**
 * @author Yuliia Shcherbakova ON 14.12.2019
 * @project repair_system
 */
public interface Encryptor<T> {
    void encrypt(T item);
    boolean checkIfDecryptedEqualEncrypted(T item, String decrypted);
}
