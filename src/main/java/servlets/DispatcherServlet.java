package servlets;

import commonlyUsedStrings.ErrorMessage;
import commonlyUsedStrings.PageLocation;
import exceptionHandling.exceptions.NotAuthorisedException;
import factories.ControllerFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Yuliia Shcherbakova ON 05.12.2019
 * @project repair_system
 */
public class DispatcherServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(DispatcherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;

        page = ControllerFactory.doGet(req);
        /*try {
            page = ControllerFactory.doGet(req);
        } catch (NullPointerException e) {
            page = PageLocation.NULL_POINTER_EXCEPTION;
            logger.error(ErrorMessage.NULL_POINTER_EXCEPTION);
        }*/
        if (page != null)
            req.getRequestDispatcher(page)
                    .forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        page = ControllerFactory.doPost(req, resp);
        if (page != null)
            req.getRequestDispatcher(page)
                    .forward(req, resp);
    }
}
