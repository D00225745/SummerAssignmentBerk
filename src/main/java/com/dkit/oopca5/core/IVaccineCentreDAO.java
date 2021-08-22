package com.dkit.oopca5.core;

import com.dkit.oopca5.server.DAOExceptions;

public interface IVaccineCentreDAO
{
    String displayCentre(String courseID) throws DAOExceptions;
    String displayAllCentres() throws DAOExceptions;
    boolean doesCentreExist(String courseId) throws DAOExceptions;

}
