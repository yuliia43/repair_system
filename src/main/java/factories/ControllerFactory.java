package factories;

import commonlyUsedStrings.PageLocation;
import controllers.*;
import exceptionHandling.exceptions.NotAuthorisedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuliia Shcherbakova ON 05.12.2019
 * @project repair_system
 */
public class ControllerFactory {
    private static Map<String, GetMethodController> getMethodControllerMap = new HashMap<>();
    private static Map<String, PostMethodController> postMethodControllerMap = new HashMap<>();

    private ControllerFactory() {
    }

    static {
        getMethodControllerMap.put("AuthorisationController", new AuthorisationController());
        getMethodControllerMap.put("UserPageController", new UserPageController());
        getMethodControllerMap.put("RegistrationController", new RegistrationController());
        getMethodControllerMap.put("MainController", new MainController());
        getMethodControllerMap.put("SignOutController", new SignOutController());
        getMethodControllerMap.put("MastersInfoController", new MastersInfoController());
        getMethodControllerMap.put("NewApplicationController", new NewApplicationController());
        getMethodControllerMap.put("UsersApplicationController", new UsersApplicationController());
        getMethodControllerMap.put("ManagerApplicationController", new ManagerApplicationController());
        getMethodControllerMap.put("MasterApplicationController", new MasterApplicationController());
        getMethodControllerMap.put("MasterFinishingController", new MasterFinishingController());

        postMethodControllerMap.put("AuthorisationController", new AuthorisationController());
        postMethodControllerMap.put("RegistrationController", new RegistrationController());
        postMethodControllerMap.put("NewApplicationController", new NewApplicationController());
        postMethodControllerMap.put("ManagerApplicationController", new ManagerApplicationController());
        postMethodControllerMap.put("MasterApplicationController", new MasterApplicationController());
        postMethodControllerMap.put("MasterFinishingController", new MasterFinishingController());
        postMethodControllerMap.put("UsersApplicationController", new UsersApplicationController());
    }

    public static String doGet(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        GetMethodController controller = null;
        switch (requestURI) {
            case ("/authorisation"): {
                controller = getMethodControllerMap.get("AuthorisationController");
                break;
            }
            case ("/userPage"): {
                controller = getMethodControllerMap.get("UserPageController");
                break;
            }
            case ("/mastersStats"): {
                controller = getMethodControllerMap.get("MastersInfoController");
                break;
            }
            case ("/registration"): {
                controller = getMethodControllerMap.get("RegistrationController");
                break;
            }
            case ("/signOut"): {
                controller = getMethodControllerMap.get("SignOutController");
                break;
            }
            case ("/newApplication"): {
                controller = getMethodControllerMap.get("NewApplicationController");
                break;
            }
            case ("/usersApplication"): {
                controller = getMethodControllerMap.get("UsersApplicationController");
                break;
            }
            case ("/managerApplication"): {
                controller = getMethodControllerMap.get("ManagerApplicationController");
                break;
            }
            case ("/masterNewApplication"): {
                controller = getMethodControllerMap.get("MasterApplicationController");
                break;
            }
            case ("/mastersApplications"): {
                controller = getMethodControllerMap.get("MasterFinishingController");
                break;
            }
            case ("/"): {
                controller = getMethodControllerMap.get("MainController");
                break;
            }
        }
        return (controller != null) ? controller.doGet(req) : PageLocation.PAGE_NOT_FOUND;
    }

    public static String doPost(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        PostMethodController controller = null;
        switch (requestURI) {
            case ("/authorisation"): {
                controller = postMethodControllerMap.get("AuthorisationController");
                break;
            }
            case ("/registration"): {
                controller = postMethodControllerMap.get("RegistrationController");
                break;
            }
            case ("/newApplication"): {
                controller = postMethodControllerMap.get("NewApplicationController");
                break;
            }
            case ("/managerApplication"): {
                controller = postMethodControllerMap.get("ManagerApplicationController");
                break;
            }
            case ("/masterNewApplication"): {
                controller = postMethodControllerMap.get("MasterApplicationController");
                break;
            }
            case ("/mastersApplications"): {
                controller = postMethodControllerMap.get("MasterFinishingController");
                break;
            }
            case ("/usersApplication"): {
                controller = postMethodControllerMap.get("UsersApplicationController");
                break;
            }
        }
        return (controller != null) ? controller.doPost(req, resp) : PageLocation.PAGE_NOT_FOUND;
    }
}
