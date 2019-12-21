package controllers;

import exceptionHandling.exceptions.NotAuthorisedException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author Yuliia Shcherbakova ON 05.12.2019
 * @project repair_system
 */
public interface GetMethodController {

    String doGet(HttpServletRequest req);
}
