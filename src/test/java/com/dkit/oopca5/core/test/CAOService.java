package com.dkit.oopca5.core.test;

/* The CAOService class has constants to define all of the messages that are sent between the Client and Server
 */

public class CAOService
{
    public static final int PORT_NUM = 50025;
    public static final String HOSTNAME = "localhost";

    public static final String BREAKING_CHARACTER = "%%";

    //Command strings
    public static final String QUIT_MENU = "QUIT";
    public static final String REGISTER_COMMAND = "REGISTER";
    public static final String SUCCESSFUL_REGISTER = "REGISTERED";
    public static final String FAILED_REGISTER = "REGISTER FAILED";
    public static final String LOGIN_COMMAND = "LOGIN";
    public static final String SUCCESSFUL_LOGIN = "LOGGED IN";
    public static final String FAILED_LOGIN = "LOGIN FAILED";
    public static final String LOGOUT = "LOGOUT";
    public static final String DISPLAY_COURSE = "DISPLAY COURSE";
    public static final String DISPLAY_ALL_COURSE = "DISPLAY ALL COURSES";
    public static final String COURSES_EMPTY = "COURSE TABLE EMPTY";
    public static final String DISPLAY_CURRENT_CHOICE = "DISPLAY CURRENT CHOICE";
    public static final String UPDATE_CURRENT_CHOICE = "UPDATE CURRENT CHOICE";
    public static final String UPDATE_CURRENT_CHOICE_SUCCESS = "UPDATE CURRENT CHOICE SUCCESS";
    public static final String UPDATE_CURRENT_CHOICE_FAILED = "UPDATE CURRENT CHOICE FAILED";
    public static final String COURSE_NOT_FOUND = "COURSE NOT FOUND";



    //Response Strings
    public static final String UNRECOGNISED = "UNKNOWN_COMMAND";
    public static final String SESSION_TERMINATED = "GOODBYE";
}