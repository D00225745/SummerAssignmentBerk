package com.dkit.oopca5.server;

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
    private static MySqlUserDAO userDatabase = new MySqlUserDAO();
    private static MySqlVaccineCentreDAO vaccineCenterDatabase = new MySqlVaccineCentreDAO();
    private static MySqlVaccineAppointmentDAO vaccineAppointment = new MySqlVaccineAppointmentDAO();

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

                    //int userId = Integer.parseInt(components[1]);
                    String email = components[1];
                    String password = components[2];

                    //Student newStudent = new Student(caoNumber, dateOfBirth, password);

                    response = userDatabase.registerUser( email, password);

                }
                else if(components[0].equals(CAOService.LOGIN_COMMAND))
                {
                    String email = components[1];
                    String password = components[2];

                    if(userDatabase.login(email, password)>0)
                    {
                        response = userDatabase.login(email, password) + "%%" +CAOService.SUCCESSFUL_LOGIN;
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
                else if(components[0].equals(CAOService.DISPLAY_ALL_VACCENTRE))
                {
                    response = vaccineCenterDatabase.displayAllVaccineCenters();
                    if (response == CAOService.VACCENTRES_EMPTY)
                    {
                        response = (Colours.RED + "Couldn't find any vaccine center to display." + Colours.RESET);
                    }
                }
                else if(components[0].equals(CAOService.BOOK_VACCINE))
                {
                    response = vaccineAppointment.bookVaccineAppointmentChoice(Integer.parseInt(components[1]), Integer.parseInt(components[2]), components[3]);
                    if (response == CAOService.FAILED_BOOK_VACCINE)
                    {
                        response = (Colours.RED + "Couldn't find any vaccine center to display." + Colours.RESET);
                    }
                }
                else if(components[0].equals(CAOService.DISPLAY_CURRENT_APPOINTMENT))
                {
                    String userId = components[1];
                    response = vaccineAppointment.displayVaccineAppointmentChoice(userId);

                    if(response.equals(CAOService.VACCENTRES_EMPTY))
                    {
                        response = (Colours.RED + "Couldn't find any Vaccine Centres to display." + Colours.RESET);
                    }

                }
                else if (components[0].equals(CAOService.UPDATE_CURRENT_APPOINTMENT))
                {
                    System.out.println("s1");
                    response = vaccineAppointment.updateVaccineAppointmentChoice(Integer.parseInt(components[1]), Integer.parseInt(components[2]), components[3]);

                    if (response == CAOService.UPDATE_CURRENT_APPOINTMENT_FAILED)
                    {
                        response = (Colours.RED + "Vaccine Appointment does not exist." + Colours.RESET);
                    }
                }

                else
                {
                    response = CAOService.UNRECOGNISED;
                }
                //Send back response
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
                System.out.println("Unable to disconnect \n" + e.getMessage());
                System.exit(1);
            }
        }
    }




}
