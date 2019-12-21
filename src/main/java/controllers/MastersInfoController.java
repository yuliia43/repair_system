package controllers;

import commonlyUsedStrings.ErrorMessage;
import commonlyUsedStrings.PageLocation;
import exceptionHandling.exceptions.NotAuthorisedException;
import models.Application;
import models.Feedback;
import org.apache.log4j.Logger;
import services.repositoryServices.ApplicationsService;
import services.repositoryServices.FeedbacksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 08.12.2019
 * @project repair_system
 */
public class MastersInfoController implements GetMethodController {
    private ApplicationsService applicationsService = ApplicationsService.getApplicationsService();
    private Logger logger = Logger.getLogger(MastersInfoController.class);

    @Override
    public String doGet(HttpServletRequest req) {
        try {
            List<Application> applications = applicationsService.getAllMastersStats();
            req.setAttribute("applications", applications);
        } catch (SQLException e) {
            logger.error(ErrorMessage.SQL_EXCEPTION);
            return PageLocation.SQL_EXCEPTION;
        }
        return PageLocation.MASTERS_STATS_PAGE;
    }

}
