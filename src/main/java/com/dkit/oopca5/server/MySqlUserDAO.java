package com.dkit.oopca5.server;

//mysql -uroot -p

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.IUserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlUserDAO extends MySqlDAO implements IUserDAO {

    @Override
    public String registerUser(int caoNumber, String dateOfBirth, String password) throws DAOExceptions
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";

        try {
            con = this.getConnection();
            String query = "insert into user values(?, ?, ?)";
            ps = con.prepareStatement(query);


            ps.setInt(1, caoNumber);
            ps.setString(2, dateOfBirth);
            ps.setString(3, password);

            if(!checkIfCAONumberTaken(caoNumber)) {
                ps.executeUpdate();
                System.out.println("Executed update");
                result = CAOService.SUCCESSFUL_REGISTER;
            }
            else
            {
                result = CAOService.FAILED_REGISTER;
            }

        }
        catch (SQLException se)
        {
            throw new DAOExceptions(se.getMessage());
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return result;
    }

    @Override
    public boolean checkIfCAONumberTaken(int caoNumber) {
        boolean userExists = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = this.getConnection();

            String query = "select * from user where cao_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(caoNumber));

            rs = ps.executeQuery();

            if (rs.next()) {
                userExists = true;
            }
        }
        catch (DAOExceptions e)
        {
            System.out.println(e.getMessage());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }

        }

        return userExists;
    }

    @Override
    public boolean login(int caoNumber, String password)
    {
        boolean successfulLogin = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = this.getConnection();

            String query = "select * from user where cao_number = ? and password = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(caoNumber));
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                successfulLogin = true;
            }
        }
        catch (DAOExceptions e)
        {
            System.out.println(e.getMessage());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }

        }

        return successfulLogin;
    }


}
