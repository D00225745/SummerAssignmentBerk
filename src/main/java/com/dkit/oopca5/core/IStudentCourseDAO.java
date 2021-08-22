package com.dkit.oopca5.core;

import com.dkit.oopca5.server.DAOExceptions;

public interface IStudentCourseDAO
{
    String displayCurrentChoice(int caoNumber) throws DAOExceptions;
    String updateCurrentChoice(String caoNumber, String choices) throws DAOExceptions;
}
