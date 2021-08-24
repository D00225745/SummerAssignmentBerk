package com.dkit.oopca5.client.App;

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.server.DAOExceptions;
import com.dkit.oopca5.server.MySqlVaccineCentreDAO;
import com.dkit.oopca5.server.MySqlUserDAO;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest
{
    @Test
    public void testRegister() throws DAOExceptions {
        MySqlUserDAO studentDAO = new MySqlUserDAO();

        String result = studentDAO.registerUser("2000wwww9", "secret1234");

        assertEquals(CAOService.FAILED_REGISTER, result);
    }

    @Test
    public void testRegisterShouldFail() throws DAOExceptions
    {
        MySqlUserDAO studentDAO = new MySqlUserDAO();

        String result = studentDAO.registerUser("2001wwwww31", "passw0rd");
        assertFalse(result.equals(CAOService.SUCCESSFUL_REGISTER));
    }


    @Test
    public void testDisplayAllVaccineCentres() throws DAOExceptions {
        MySqlVaccineCentreDAO courseDAO = new MySqlVaccineCentreDAO();
        String wrongResult = "0";

        if (courseDAO.displayAllVaccineCenters() != wrongResult)
        {
            assertTrue(true);
        }
        else
        {
            assertTrue(false);
        }
    }

    @Test
    public void courseDoesNotExist() throws DAOExceptions
    {
        MySqlVaccineCentreDAO courseDAO = new MySqlVaccineCentreDAO();
        int centerId = 123;

        boolean doesExist = courseDAO.doesVaccineCentreExist(centerId);

        assertFalse(doesExist);
    }


}
