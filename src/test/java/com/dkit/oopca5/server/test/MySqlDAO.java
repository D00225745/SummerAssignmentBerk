package com.dkit.oopca5.server.test;

/*
All of the database functionality should be here. You will need a DAO for each table that you are interacting with in the database
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDAO
{
    public Connection getConnection() throws DAOExceptions
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/SummerAssignmentBerk";
        String username = "root";
        String password = "";

        Connection con = null;
        try
        {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);

        }
        catch(SQLException se)
        {
            System.out.println("Connection failed " + se.getMessage());
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Class not found " + cnfe.getMessage());
        }
        System.out.println("Connected successfully");
        return con;
    }

    public void freeConnection(Connection con) throws DAOExceptions
    {
        try{
            if(con != null)
            {
                con.close();
                con = null;
            }
        }
        catch (SQLException se)
        {
            System.out.println("Failed to free the connection " + se.getMessage());
            System.exit(1);
        }
    }
}
