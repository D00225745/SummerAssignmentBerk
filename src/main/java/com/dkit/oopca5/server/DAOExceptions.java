package com.dkit.oopca5.server;

import java.sql.SQLException;

public class DAOExceptions extends SQLException
{
    public DAOExceptions()
    {

    }

    public DAOExceptions(String aMessage)
    {
        super(aMessage);
    }
}
