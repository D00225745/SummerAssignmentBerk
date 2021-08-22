package com.dkit.oopca5.server.test;

/*
The CAOClientHandler will run as a thread. It should listen for messages from the Client and respond to them.There should be one CAOClientHandler per Client.
 */

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CAOClientHandler extends Thread
{
    private Socket dataSocket;
    private Scanner input;
    private PrintWriter output;
    private int number;
    private static MySqlUsereDAO userDatabase = new MySqlUserDAO();
    private static MySqlVaccineCentreDAO vaccineCentreDatabase = new MySqlVaccineCentreDAO();
    private static MySqlVaccineAppointmentDAO vaccineAppointmentDatabase = new MySqlVaccineAppointmentDAO();

    public CAOClientHandler(ThreadGroup group, String name, Socket dataSocket, int number)
    {
        super(group, name);
        try
        {
            this.dataSocket = dataSocket;
            this.number = number;
            input = new Scanner(new InputStreamReader(this.dataSocket.getInputStream()));
            output = new PrintWriter(this.dataSocket.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        String incomingMessage = "";
        String response;

        try
        {
            //Loop while client doesn't want to end the session
            while (!incomingMessage.equals(CAOService.QUIT_MENU))
            {
                response = null;

                //Taking input from the client
                incomingMessage = input.nextLine();
                System.out.println("Received message: " + incomingMessage);

                //Breaking message into components
                String[] components = incomingMessage.split(CAOService.BREAKING_CHARACTER);

                if(components[0].equals(CAOService.REGISTER_COMMAND))
                {
                    //System.out.println("TEST");
                    System.out.println("Client #" + number + " details:");
                    for(int i = 1; i < components.length; i++)
                    {
                        System.out.println(components[i]);
                    }

                    int caoNumber = Integer.parseInt(components[1]);
                    String dateOfBirth = components[2];
                    String password = components[3];

                    //User newUser = new Student(caoNumber, dateOfBirth, password);

                    response = userDatabase.registerUser(caoNumber, dateOfBirth, password);

                }
                else if(components[0].equals(CAOService.LOGIN_COMMAND))
                {
                    int caoNumber = Integer.parseInt(components[1]);
                    String password = components[2];

                    if(userDatabase.login(caoNumber, password))
                    {
                        response = CAOService.SUCCESSFUL_LOGIN;
                    }
                    else
                    {
                        response = CAOService.FAILED_LOGIN;
                    }

                }
                else if(components[0].equals(CAOService.QUIT_MENU))
                {
                    response = CAOService.SESSION_TERMINATED;
                }
                else if(components[0].equals(CAOService.DISPLAY_VACCENTRE))
                {
                    String vaccineCentreId = components[1];

                    boolean vaccineCentreExists = vaccineCentreDatabase.doesVaccineCentreExist(vaccineCentreId);
                    if (vaccineCentreExists)
                    {
                        response = vaccineCentreDatabase.displayVaccineCentreExist(vaccineCentreId);
                    }
                    else
                    {
                        response = (Colours.RED + "Vaccine Centre does not exist..." + Colours.RESET);
                    }
                }
                else if(components[0].equals(CAOService.DISPLAY_ALL_VACCENTRE))
                {
                    response = vaccineCentreDatabase.displayAllVaccineCentres();
                    if (response == CAOService.VACCENTRES_EMPTY)
                    {
                        response = (Colours.RED + "Couldn't find any courses to display." + Colours.RESET);
                    }
                }
                else if(components[0].equals(CAOService.DISPLAY_CURRENT_APPOINTMENT))
                {
                    int caoNumber = Integer.parseInt(components[1]);
                    response = vaccineAppointmentDatabase.displayCurrentAppointment(caoNumber);

                    if(response.equals(CAOService.VACCENTRES_EMPTY))
                    {
                        response = (Colours.RED + "Couldn't find any Vaccine Centres to display." + Colours.RESET);
                    }

                }
                else if (components[0].equals(CAOService.UPDATE_CURRENT_APPOINTMENT))
                {
                    String caoNumber = components[1];
                    String courseId = components[2];

                    boolean courseExists = vaccineCentreDatabase.doesVaccineCentreExist(courseId);
                    if (courseExists)
                    {
                        response = vaccineAppointmentDatabase.updateCurrentAppointment(caoNumber, courseId);
                    }
                    else
                    {
                        response = (Colours.RED + "Appointment does not exist." + Colours.RESET);
                    }
                }

                else
                {
                    response = CAOService.UNRECOGNISED;
                }

                //SendS back response
                output.println(response);
                output.flush();
            }
        }
        catch (NoSuchElementException | DAOExceptions e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                System.out.println("\nClosing connection with client #" + number);
                dataSocket.close();
            }
            catch (IOException e)
            {
                System.out.println("Unable to disconnect... \n" + e.getMessage());
                System.exit(1);
            }
        }
    }




}
