package commonlyUsedStrings;

/**
 * @author Yuliia Shcherbakova ON 05.12.2019
 * @project repair_system
 */
public class ErrorMessage {
    public static final String SQL_EXCEPTION = "SQLException occurred!";
    public static final String NULL_POINTER_EXCEPTION = "NullPointerException occurred!";
    public static final String AUTO_COMMIT_FALSE_FAIL = "Error while unsetting auto commit";
    public static final String FEEDBACK_ADDING_FAIL = "Error while adding feedback";
    public static final String APPLICATION_ADDING_FAIL = "Error while adding application";
    public static final String USER_ADDING_FAIL = "Error while adding user";
    public static final String FEEDBACK_UPDATING_FAIL = "Error while updating feedback";
    public static final String APPLICATION_UPDATING_FAIL = "Error while updating application";
    public static final String USER_UPDATING_FAIL = "Error while updating user";
    public static final String TRANSACTION_COMMIT_FAIL = "Transaction commit failed";
    public static final String ROLLBACK_FAIL = "Error while rollbacking and setting auto commit";
    public static final String CLOSING_CONNECTION_FAIL = "Error while closing connection";
    public static final String REDIRECT_FAIL = "Error while redirection";
    public static final String JDBC_IO_EXCEPTION = "Connection pool not created! I/O Exception!";
    public static final String JDBC_FILE_NOT_FOUND = "Connection pool not created! File not found!";
    public static final String JDBC_DRIVER_NOT_FOUND = "Driver not found!";

    private ErrorMessage() {
    }
}
