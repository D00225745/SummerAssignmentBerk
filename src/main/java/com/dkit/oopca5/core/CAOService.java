package com.dkit.oopca5.core;

/* The CAOService class has constants to define all of the messages that are sent between the Client and Server
 */

public class CAOService
{
    public static final int PORT_NUM = 50021;
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
    public static final String DISPLAY_VACCENTRE = "DISPLAY VACCINE CENTRE";
    public static final String DISPLAY_ALL_VACCENTRE = "DISPLAY ALL VACCINE CENTRES";
    public static final String VACCENTRES_EMPTY = "VACCINE CENTRE TABLE EMPTY";
    public static final String DISPLAY_CURRENT_APPOINTMENT = "DISPLAY CURRENT APPOINTMENT";
    public static final String BOOK_VACCINE = "BOOK VACCINE";
    public static final String FAILED_BOOK_VACCINE = "FAILED_BOOK_VACCINE";
    public static final String SUCCESSFUL_BOOK_VACCINE = "SUCCESSFUL_BOOK_VACCINE";
    public static final String SUCCESSFUL_DISPLAY_APPOINMENT = "SUCCESSFUL_DISPLAY_APPOINMENT";

    public static final String UPDATE_CURRENT_APPOINTMENT = "UPDATE CURRENT APPOINTMENT";
    public static final String UPDATE_CURRENT_APPOINTMENT_SUCCESS = "UPDATE CURRENT APPOINTMENT SUCCESS";
    public static final String UPDATE_CURRENT_APPOINTMENT_FAILED = "UPDATE CURRENT APPOINTMENT FAILED";
    public static final String VACCENTRE_NOT_FOUND = "COURSE NOT FOUND";




    //Response Strings
    public static final String UNRECOGNISED = "UNKNOWN_COMMAND";
    public static final String SESSION_TERMINATED = "GOODBYE";
}
