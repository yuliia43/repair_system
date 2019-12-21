package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Yuliia Shcherbakova ON 05.12.2019
 * @project repair_system
 */
public interface PostMethodController {

    String doPost(HttpServletRequest req, HttpServletResponse resp);
}
