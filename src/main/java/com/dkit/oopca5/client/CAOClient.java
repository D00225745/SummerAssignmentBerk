package com.dkit.oopca5.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

//This is the dto

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.server.MySqlUserDAO;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CAOClient
{
    static boolean loggedIn = false;
    static Scanner keyboard = new Scanner(System.in);
    static RegexChecker regexChecker = new RegexChecker();
    private static String loggedInUser = "";

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
                                message = registerUser();
                                //message = CAOService.REGISTER_COMMAND;


                                serverOut.println(message);

                                response = serverIn.nextLine();
                                if (response.equals(CAOService.SUCCESSFUL_REGISTER)) {
                                    System.out.println("Successfully registered");
                                } else if (response.equals(CAOService.FAILED_REGISTER)) {
                                    System.out.println(Colours.RED + "Failed to register. Email taken" + Colours.RESET);
                                } else {
                                    System.out.println("Test");
                                }

                                break;

                            case 2:
                                message = userLogin();

                                serverOut.println(message);

                                response = serverIn.nextLine();

                                if (response.split(CAOService.BREAKING_CHARACTER)[1].equals(CAOService.SUCCESSFUL_LOGIN)) {
                                    System.out.println("Successfully logged in.");
                                    loggedIn = true;
                                    loggedInUser = ( response.split(CAOService.BREAKING_CHARACTER)[0]);
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
					2. DISPLAY_VACCINE_CENTRES
					3. BOOK_VACCINE
					4. DISPLAY_VACCINE_APPOINTMENT
					5. UPDATE_VACCINE_APPOINTMENT
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
                                loggedInUser = "";
                                break;

                            case 2:
                                message = CAOService.DISPLAY_ALL_VACCENTRE;
                                serverOut.println(message);
                                response = serverIn.nextLine();
                                System.out.println("-----------------------------------------------------------------------------");
                                System.out.printf("%10s %30s", "LOCATION ID", "LOCATION NAME");
                                System.out.println();
                                System.out.println("-----------------------------------------------------------------------------");

                                String[] line = response.split( "&" );
                                for(int t= 0;t<line.length;t++){
                                    String[] sep = line[t].split( "%%" );
                                    System.out.format("%10s %30s",
                                            sep[0], sep[1]);
                                    System.out.println();
                                }
                                System.out.println("-----------------------------------------------------------------------------");


                                System.out.println(response);
                                break;

                            case 3:
                                message = bookVaccine(loggedInUser);
                                serverOut.println(message);
                                response = serverIn.nextLine();
                                System.out.println(response);
                                break;

                            case 4:
                                String toSend = CAOService.DISPLAY_CURRENT_APPOINTMENT+CAOService.BREAKING_CHARACTER+loggedInUser;
                                message = toSend;
                                serverOut.println(message);
                                response = serverIn.nextLine();

                                System.out.println("-----------------------------------------------------------------------------");
                                System.out.printf("%10s %30s %20s", "CENTER ID", "LOCATION NAME", "DATE TIME");
                                System.out.println();
                                System.out.println("-----------------------------------------------------------------------------");

                                String[] line4 = response.split( "&" );
                                for(int t= 0;t<line4.length;t++){
                                    String[] sep = line4[t].split( "%%" );
                                    System.out.format("%10s %30s %20s",
                                            sep[1], sep[2], sep[3]);
                                    System.out.println();
                                }
                                System.out.println("-----------------------------------------------------------------------------");


                                System.out.println(response);
                                break;

                            case 5:
                                message = updateVaccine(loggedInUser);

                                serverOut.println(message);
                                response = serverIn.nextLine();
                                System.out.println(response);
                                break;


                        }

                        ;
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



        System.out.println("Thank you for using the Vaccine Application.");
    }

    private static String updateCurrentChoices()
    {
        keyboard.nextLine();
        int counter = 1;
        boolean loop = true;
        String courseId = "";
        StringBuffer message = new StringBuffer(CAOService.UPDATE_CURRENT_APPOINTMENT);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(loggedInUser);
        message.append(CAOService.BREAKING_CHARACTER);

        System.out.println("Please insert your choice: ");
        courseId = keyboard.nextLine();

        message.append(courseId);

        return message.toString();

    }



    private static void displayLoggedInMenu()
    {
        System.out.println(Colours.RED + "0) To Quit" + Colours.RESET);
        System.out.println(Colours.BLUE + "1) Logout");
        System.out.println(Colours.BLUE + "2) Display All Vaccine Centres");
        System.out.println(Colours.BLUE + "3) Book Vaccine");
        System.out.println(Colours.BLUE + "4) Display Vaccie Appoinment");
        System.out.println(Colours.BLUE + "5) Update Vaccie Appoinmen" + Colours.RESET);
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

    private static String registerUser()
    {
        StringBuffer registeredUser = new StringBuffer(CAOService.REGISTER_COMMAND);
        registeredUser.append(CAOService.BREAKING_CHARACTER);
        int caoNumber = 1;
        String email = "a";
        String password = "a";

        System.out.println("Please enter your Email Id: ");
        while (!regexChecker.testEmail(email))
        {
            try
            {
                email = keyboard.next();
                if(!regexChecker.testEmail(email))
                {
                    System.out.println(Colours.RED + "Incorrect Email Adress, please make sure it is correct" + Colours.RESET);
                    keyboard.nextLine();
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println(Colours.RED + "Incorrect Email Adress, please make sure it is correct" + Colours.RESET);
                keyboard.nextLine();
            }
        }

        keyboard.nextLine();
        registeredUser.append(email);
        registeredUser.append(CAOService.BREAKING_CHARACTER);


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

        registeredUser.append(password);
        registeredUser.append(CAOService.BREAKING_CHARACTER);

        return registeredUser.toString();
    }

    private static String userLogin()
    {
        StringBuffer loginDetails = new StringBuffer(CAOService.LOGIN_COMMAND);
        loginDetails.append(CAOService.BREAKING_CHARACTER);
        String email= "1dwdwdw";
        String password = "a";

        System.out.println("Please enter your E-mail adress: ");
        while (!regexChecker.testEmail(email))
        {
            try
            {
                email = keyboard.next();
                if(!regexChecker.testEmail(email))
                {
                    System.out.println(Colours.RED + "Incorrect Email Adress, please make sure it is correct" + Colours.RESET);
                    keyboard.nextLine();
                }

            }
            catch (InputMismatchException e)
            {
                System.out.println(Colours.RED + "Incorrect Email Adress, please make sure it is correct" + Colours.RESET);
                keyboard.nextLine();
            }
        }

        keyboard.nextLine();
        loginDetails.append(email);
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

        loggedInUser = email;

        return loginDetails.toString();
    }


    private static String bookVaccine(String userId1)
    {
        StringBuffer bookDetails = new StringBuffer(CAOService.BOOK_VACCINE);
        bookDetails.append(CAOService.BREAKING_CHARACTER);
        int userId= Integer.parseInt(userId1);
        int centerId= 1;
        String dateTime = "";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        dateTime = (dtf.format(now));

        System.out.println("Please enter center id");

        try
        {
            centerId = Integer.parseInt(keyboard.next());

        }
        catch (InputMismatchException e)
        {
            System.out.println(Colours.RED + "Incorrect Location, please make sure it is correct" + Colours.RESET);
            keyboard.nextLine();
        }


        keyboard.nextLine();
        bookDetails.append(userId);
        bookDetails.append(CAOService.BREAKING_CHARACTER);

        bookDetails.append(centerId);
        bookDetails.append(CAOService.BREAKING_CHARACTER);

        bookDetails.append(dateTime);
        bookDetails.append(CAOService.BREAKING_CHARACTER);


        return bookDetails.toString();
    }


    private static String updateVaccine(String userId1)
    {
        StringBuffer bookDetails = new StringBuffer(CAOService.UPDATE_CURRENT_APPOINTMENT);
        bookDetails.append(CAOService.BREAKING_CHARACTER);
        int userId= Integer.parseInt(userId1);
        int centerId= 1;
        String dateTime = "";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        dateTime = (dtf.format(now));

        System.out.println("Please enter center id");

        try
        {
            centerId = Integer.parseInt(keyboard.next());

        }
        catch (InputMismatchException e)
        {
            System.out.println(Colours.RED + "Incorrect Location, please make sure it is correct" + Colours.RESET);
            keyboard.nextLine();
        }


        keyboard.nextLine();
        bookDetails.append(userId);
        bookDetails.append(CAOService.BREAKING_CHARACTER);
        bookDetails.append(centerId);
        bookDetails.append(CAOService.BREAKING_CHARACTER);
        bookDetails.append(dateTime);
        bookDetails.append(CAOService.BREAKING_CHARACTER);



        return bookDetails.toString();
    }



}