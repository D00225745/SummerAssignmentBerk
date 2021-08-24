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
    public String registerUser(String email, String password) throws DAOExceptions
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";

        try {
            con = this.getConnection();

            String query = "insert into user(email,password) values(?, ?)";
            ps = con.prepareStatement(query);


            ps.setString(1, email);
            ps.setString(2, password);

            if(!checkIfUserIdTaken(email)) {
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
    public boolean checkIfUserIdTaken(String email) {
        boolean userExists = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = this.getConnection();

            String query = "select * from user where email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, (email));

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
    public int login(String email, String password)
    {
        boolean successfulLogin = false;
        int userId = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = this.getConnection();

            String query = "select * from user where email = ? and password = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                userId = rs.getInt(1);
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

        return userId;
    }


}
