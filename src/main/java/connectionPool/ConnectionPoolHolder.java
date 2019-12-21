package connectionPool;

import commonlyUsedStrings.ErrorMessage;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class ConnectionPoolHolder {

    private static ConnectionPoolHolder connectionPool;
    private static BasicDataSource basicDataSource;
    private static final Logger logger = Logger.getLogger(ConnectionPoolHolder.class);

    private ConnectionPoolHolder(){

    }

    public static Connection receiveConnection() throws SQLException {
        if(connectionPool == null){
            synchronized (ConnectionPoolHolder.class) {
                if(connectionPool == null){
                    Properties properties = new Properties();
                    try(FileInputStream fileInputStream =
                                new FileInputStream("src\\main\\resources\\database.properties")){
                        properties.load(fileInputStream);
                        String driverClassName = properties.getProperty("driverClassName");
                        Class.forName(driverClassName);
                        basicDataSource = new BasicDataSource();
                        basicDataSource.setDriverClassName(driverClassName);
                        basicDataSource.setUrl(properties.getProperty("url"));
                        basicDataSource.setUsername(properties.getProperty("username"));
                        basicDataSource.setPassword(properties.getProperty("password"));
                        basicDataSource.setMinIdle(Integer.valueOf(properties.getProperty("minIdle")));
                        basicDataSource.setMaxIdle(Integer.valueOf(properties.getProperty("maxIdle")));
                        basicDataSource.setMaxTotal(Integer.valueOf(properties.getProperty("maxTotal")));
                        basicDataSource.setInitialSize(Integer.valueOf(properties.getProperty("initialSize")));
                        connectionPool = new ConnectionPoolHolder();
                        logger.info("Connection Pool created!");
                    } catch (FileNotFoundException e) {
                        logger.error(ErrorMessage.JDBC_FILE_NOT_FOUND);
                        throw new SQLException();
                    } catch (IOException e) {
                        logger.error(ErrorMessage.JDBC_IO_EXCEPTION);
                        throw new SQLException();
                    } catch (ClassNotFoundException e) {
                        logger.error(ErrorMessage.JDBC_DRIVER_NOT_FOUND);
                        throw new SQLException();
                    }
                }
            }
        }
        try {
            Connection connection = basicDataSource.getConnection();
            logger.info("Connection received!");
            return connection;
        } catch (SQLException e) {
            logger.error("Connection not received! SQL error!");
            throw e;
        }
    }

}
