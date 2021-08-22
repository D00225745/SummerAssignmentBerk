package com.dkit.oopca5.core;

import com.dkit.oopca5.server.DAOExceptions;

public interface IUserDAO
{
    String registerUser(int caoNumber, String dateOfBirth, String password) throws DAOExceptions;
    boolean checkIfCAONumberTaken(int caoNumber);
    boolean login(int caoNumber, String password);
}
