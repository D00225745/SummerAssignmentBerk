package com.dkit.oopca5.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

//This is the dto

import com.dkit.oopca5.core.test.CAOService;
import com.dkit.oopca5.core.test.Colours;
import com.dkit.oopca5.server.MySqlStudentDAO;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CAOClient
{
    static boolean loggedIn = false;
    static Scanner keyboard = new Scanner(System.in);
    static RegexChecker regexChecker = new RegexChecker();
    private static int loggedInUser = 0;

    public static void main(String[] args) {
        try {
//            Socket dataSocket = new Socket(CAOService.HOSTNAME, CAOService.PORT_NUM);
//            OutputStream out = dataSocket.getOutputStream();
//
//            PrintWriter output = new PrintWriter(new OutputStreamWriter(out));
//
//            InputStream in = dataSocket.getInputStream();
//            Scanner input = new Scanner(new InputStreamReader(in));

            Socket dataSocket = new Socket(CAOService.HOSTNAME, CAOService.PORT_NUM);
            Scanner serverIn = new Scanner(dataSocket.getInputStream());
            PrintWriter serverOut = new PrintWriter(dataSocket.getOutputStream(), true);

            String message = "";

            while(!message.equals(CAOService.QUIT_MENU)) {

                while (!message.equals(CAOService.QUIT_MENU) && !loggedIn) {
                    displayMenu();
                    int choice = getNumber(keyboard, 0);
                    String response = "";

                    if (choice >= 0 && choice < 3) {
                        switch (choice) {
                            case 0:
                                message = CAOService.QUIT_MENU;
                                //send message
                                serverOut.println(message);


                                //Listen for response
                                response = serverIn.nextLine();
                                if (response.equals(CAOService.SESSION_TERMINATED)) {
                                    System.out.println("Thanks for using the TCP Combo Service");
                                    System.out.println("Session ended");
                                }
                                break;

                            case 1:
                                message = registerStudent();
                                //message = CAOService.REGISTER_COMMAND;


                                serverOut.println(message);

                                response = serverIn.nextLine();
                                if (response.equals(CAOService.SUCCESSFUL_REGISTER)) {
                                    System.out.println("Successfully registered");
                                } else if (response.equals(CAOService.FAILED_REGISTER)) {
                                    System.out.println(Colours.RED + "Failed to register. CAO Number taken" + Colours.RESET);
                                } else {
                                    System.out.println("Test");
                                }

                                break;

                            case 2:
                                message = studentLogin();

                                serverOut.println(message);

                                response = serverIn.nextLine();

                                if (response.equals(CAOService.SUCCESSFUL_LOGIN)) {
                                    System.out.println("Successfully logged in.");
                                    loggedIn = true;
                                } else if (response.equals(CAOService.FAILED_LOGIN)) {
                                    System.out.println(Colours.RED + "Sorry, student with those details not found." + Colours.RESET);
                                }

                        }

                        if (response.equals(CAOService.UNRECOGNISED)) {
                            System.out.println("Sorry, the request is not recognised. Please enter a number between 0 and 2");
                        }
                    } else {
                        System.out.println("Please select an option from the menu");
                    }

                }


                while (!message.equals(CAOService.QUIT_MENU) && loggedIn) {
                /*
                0. QUIT
                1. LOGOUT
                2. DISPLAY_COURSE
                3. DISPLAY_ALL_COURSES
                4. DISPLAY_CURRENT_CHOICES
                5. UPDATE_CURRENT_CHOICES
                 */

                    displayLoggedInMenu();

                    int choice = getNumber(keyboard, 1);
                    String response = "";

                    if (choice > -1 && choice < 6) {
                        switch (choice) {
                            case 0:
                                message = CAOService.QUIT_MENU;
                                break;

                            case 1:
                                System.out.println("Logging out...");
                                loggedIn = false;
                                loggedInUser = 0;
                                break;

                            case 2:
                                message = inputCourseId();
                                serverOut.println(message);
                                response = serverIn.nextLine();
                                System.out.println(response);
                                break;

                            case 3:
                                message = CAOService.DISPLAY_ALL_COURSE;
                                serverOut.println(message);
                                response = serverIn.nextLine();
                                System.out.println(response);
                                break;

                            case 4:
                                String toSend = CAOService.DISPLAY_CURRENT_CHOICE+CAOService.BREAKING_CHARACTER+loggedInUser;
                                message = toSend;
                                serverOut.println(message);
                                response = serverIn.nextLine();
                                System.out.println(response);
                                break;

                            case 5:
                                message = updateCurrentChoices();

                                serverOut.println(message);
                                response = serverIn.nextLine();
                                System.out.println(response);
                                break;

                        }
                    } else {
                        System.out.println("Please select an option from the menu");
                    }
                }
            }

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



        System.out.println("Thank you for using the CAO Application.");
    }

    private static String updateCurrentChoices()
    {
        keyboard.nextLine();
        int counter = 1;
        boolean loop = true;
        String courseId = "";
        StringBuffer message = new StringBuffer(CAOService.UPDATE_CURRENT_CHOICE);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(loggedInUser);
        message.append(CAOService.BREAKING_CHARACTER);

        System.out.println("Please insert your choice: ");
        courseId = keyboard.nextLine();

        message.append(courseId);

        return message.toString();

    }

    private static String inputCourseId()
    {
        keyboard.nextLine();
        StringBuffer courseId = new StringBuffer(CAOService.DISPLAY_COURSE);
        courseId.append(CAOService.BREAKING_CHARACTER);

        System.out.println("Please enter course id for course you want to display:");
        String course = keyboard.nextLine();

        courseId.append(course);

        return courseId.toString();
    }

    private static void displayLoggedInMenu()
    {
        System.out.println(Colours.RED + "0) To Quit" + Colours.RESET);
        System.out.println(Colours.BLUE + "1) Logout");
        System.out.println(Colours.BLUE + "2) Display Course");
        System.out.println(Colours.BLUE + "3) Display All Courses");
        System.out.println(Colours.BLUE + "4) Display Current Choices");
        System.out.println(Colours.BLUE + "5) Update Current Choices" + Colours.RESET);
    }


    private static void displayMenu()
    {
        System.out.println(Colours.RED + "0) To Quit" + Colours.RESET );
        System.out.println(Colours.BLUE + "1) Register");
        System.out.println(Colours.BLUE + "2) Login" + Colours.RESET );
    }

    private static int getNumber(Scanner keyboard, int menuNum)
    {
        System.out.println("Please enter your choice");

        boolean numberEntered = false;
        int number = 0;
        while(!numberEntered)
        {
            try
            {
                number = keyboard.nextInt();
                numberEntered = true;
            }
            catch (InputMismatchException e)
            {
                //First menu
                if(menuNum == 0)
                {
                    System.out.println(Colours.RED + "Please enter an integer (0-3)");
                }
                //Second menu
                else if(menuNum == 1)
                {
                    System.out.println(Colours.RED + "Please enter an integer (0-5)");
                }

                keyboard.nextLine();
            }
        }

        return number;
    }

    private static String registerStudent()
    {
        StringBuffer registeredStudent = new StringBuffer(CAOService.REGISTER_COMMAND);
        registeredStudent.append(CAOService.BREAKING_CHARACTER);
        int caoNumber = 1;
        String dateOfBirth = "a";
        String password = "a";

        System.out.println("Please enter your CAO Number (8 digits): ");
        while (!regexChecker.testCaoNumber(caoNumber))
        {
            try
            {
                caoNumber = keyboard.nextInt();
                if(!regexChecker.testCaoNumber(caoNumber))
                {
                    System.out.println(Colours.RED + "Incorrect CAO Number, please make sure it is 8 digits" + Colours.RESET);
                    keyboard.nextLine();
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println(Colours.RED + "Incorrect CAO Number, please make sure it is 8 digits" + Colours.RESET);
                keyboard.nextLine();
            }
        }

        keyboard.nextLine();
        registeredStudent.append(caoNumber);
        registeredStudent.append(CAOService.BREAKING_CHARACTER);

        System.out.println("\nPlease enter your date of birth (YYYY-MM-DD): ");
        while (!regexChecker.testDateOfBirth(dateOfBirth))
        {
            dateOfBirth = keyboard.nextLine();

            if(!regexChecker.testDateOfBirth(dateOfBirth))
            {
                System.out.println(Colours.RED + "Incorrect format, please make sure it is in the form YYYY-MM-DD" + Colours.RESET);
            }
        }

        registeredStudent.append(dateOfBirth);
        registeredStudent.append(CAOService.BREAKING_CHARACTER);

        System.out.println("\nPlease enter your password (minimum of 8 characters, maximum of 30 characters)");
        while (!regexChecker.testPassword(password))
        {
            password = keyboard.nextLine();

            if(!regexChecker.testPassword(password))
            {
                System.out.println(Colours.RED + "Incorrect format, please make sure it is between 8 and 30 characters" + Colours.RESET);
//                keyboard.nextLine();
            }
        }

        registeredStudent.append(password);
        registeredStudent.append(CAOService.BREAKING_CHARACTER);

        return registeredStudent.toString();
    }

    private static String studentLogin()
    {
        StringBuffer loginDetails = new StringBuffer(CAOService.LOGIN_COMMAND);
        loginDetails.append(CAOService.BREAKING_CHARACTER);
        int caoNumber = 1;
        String password = "a";

        System.out.println("Please enter your CAO Number (8 digits): ");
        while (!regexChecker.testCaoNumber(caoNumber))
        {
            try
            {
                caoNumber = keyboard.nextInt();
                if(!regexChecker.testCaoNumber(caoNumber))
                {
                    System.out.println(Colours.RED + "Incorrect CAO Number, please make sure it is 8 digits" + Colours.RESET);
                    keyboard.nextLine();
                }

            }
            catch (InputMismatchException e)
            {
                System.out.println(Colours.RED + "Incorrect CAO Number, please make sure it is 8 digits" + Colours.RESET);
                keyboard.nextLine();
            }
        }

        keyboard.nextLine();
        loginDetails.append(caoNumber);
        loginDetails.append(CAOService.BREAKING_CHARACTER);

        System.out.println("\nPlease enter your password (minimum of 8 characters, maximum of 30 characters)");
        while (!regexChecker.testPassword(password))
        {
            password = keyboard.nextLine();

            if(!regexChecker.testPassword(password))
            {
                System.out.println(Colours.RED + "Incorrect format, please make sure it is between 8 and 30 characters" + Colours.RESET);
            }
        }

        loginDetails.append(password);
        loginDetails.append(CAOService.BREAKING_CHARACTER);

        loggedInUser = caoNumber;

        return loginDetails.toString();
    }

}