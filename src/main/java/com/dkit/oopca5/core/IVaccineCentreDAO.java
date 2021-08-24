package com.dkit.oopca5.core;

import com.dkit.oopca5.server.DAOExceptions;

public interface IVaccineCentreDAO
{
    String displayVaccineCentre(int centreId ) throws DAOExceptions;
    String displayAllVaccineCentres() throws DAOExceptions;
    boolean doesVaccineCentreExist(int centreId) throws DAOExceptions;

}
