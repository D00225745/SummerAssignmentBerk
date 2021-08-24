package com.dkit.oopca5.core;

import com.dkit.oopca5.server.DAOExceptions;

public interface IVaccineCentreDAO
{
    String displayVaccineCenter(int centreId ) throws DAOExceptions;
    String displayAllVaccineCenters() throws DAOExceptions;
    boolean doesVaccineCenterExist(int centreId) throws DAOExceptions;

}
