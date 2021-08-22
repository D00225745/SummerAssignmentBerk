package com.dkit.oopca5.core;

import com.dkit.oopca5.server.DAOExceptions;

public interface IVaccineAppointmentDAO
{
    String displayCurrentAppointment(int caoNumber) throws DAOExceptions;
    String updateCurrentAppointment(String caoNumber, String choices) throws DAOExceptions;
}
