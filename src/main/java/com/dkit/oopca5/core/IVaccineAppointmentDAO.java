package com.dkit.oopca5.core;

import com.dkit.oopca5.server.DAOExceptions;

public interface IVaccineAppointmentDAO
{
    String bookVaccineAppointmentChoice(int user_id,int center_id,String datetime) throws DAOExceptions;
    String displayVaccineAppointmentChoice(String email) throws DAOExceptions;
    String updateVaccineAppointmentChoice(int user_id,int center_id,String datetime) throws DAOExceptions;
}
