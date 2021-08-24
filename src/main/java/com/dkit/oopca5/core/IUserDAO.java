package com.dkit.oopca5.core;

import com.dkit.oopca5.server.DAOExceptions;

public interface IUserDAO
{
    String registerUser(String email, String password ) throws DAOExceptions;
    boolean checkIfUserIdTaken(String email);
    int login(String email, String password);
}
