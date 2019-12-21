package controllers;

import commonlyUsedStrings.PageLocation;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class SignOutController implements GetMethodController {

    public String doGet(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        return PageLocation.AUTHORISATION_PAGE;
    }
}
