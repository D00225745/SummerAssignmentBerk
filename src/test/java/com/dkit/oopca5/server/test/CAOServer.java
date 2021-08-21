package com.dkit.oopca5.server.test;

/* The server package should contain all code to run the server. The server uses TCP sockets and thread per client.
 The server should connect to a MySql database to register clients, allow them to login and choose courses
 The server should listen for connections and once a connection is accepted it should spawn a new CAOClientHandler thread to deal with that connection. The server then returns to listening
 */

import com.dkit.oopca5.core.test.CAOService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CAOServer
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket listeningSocket = new ServerSocket(CAOService.PORT_NUM);

            //ThreadGroup
            ThreadGroup clientThreadGroup = new ThreadGroup("Client threads");
            //Placing more emphasis on accepting clients than processing current connections
            clientThreadGroup.setMaxPriority(Thread.currentThread().getPriority() - 1);

            boolean continueRunning = true;
            int threadCount = 0;

            while (continueRunning) {
                //Listening socket
                Socket dataSocket = listeningSocket.accept();
                threadCount++;
                System.out.println("The server now has accepted client number: " + threadCount);

                //Building the thread
                CAOClientHandler newClient = new CAOClientHandler(clientThreadGroup, dataSocket.getInetAddress() + "", dataSocket, threadCount);
                newClient.start();

                //Set up input and output streams
                OutputStream out = dataSocket.getOutputStream();
                //Decorator pattern
                PrintWriter output = new PrintWriter(new OutputStreamWriter(out));

                InputStream in = dataSocket.getInputStream();
                Scanner input = new Scanner(new InputStreamReader(in));

                String incomingMessage = "";
            }

            listeningSocket.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch(NoSuchElementException e)
        {
            System.out.println("Shutting down. new threads needed...");
            System.exit(1);
        }


    }


}


