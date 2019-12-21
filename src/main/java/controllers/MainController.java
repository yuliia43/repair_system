package controllers;

import commonlyUsedStrings.PageLocation;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yuliia Shcherbakova ON 05.12.2019
 * @project repair_system
 */
public class MainController implements GetMethodController {
    public String doGet(HttpServletRequest req){
        return PageLocation.MAIN;
    }
}
