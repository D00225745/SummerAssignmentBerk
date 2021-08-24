package com.dkit.oopca5.server;

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.IVaccineCentreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class MySqlVaccineCentreDAO extends MySqlDAO implements IVaccineCentreDAO {

    @Override
    public String displayVaccineCentre(int centerId) throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String courseDetails = "none";

        try {
            con = this.getConnection();
            String query = "select * from vaccine_centre where center_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, centerId + "");

            ps.executeQuery();

            while (rs.next() && rs != null) {
                String location = rs.getString("location");


                courseDetails = ("Center Id: " + centerId + "\nlocation: " + location );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
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

        return courseDetails;
    }

    @Override
    public String displayAllVaccineCenters() throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer vaccinecenters = new StringBuffer();
        String vaccines = "";

        try {
            con = this.getConnection();
            String query = "select * from vaccine_centre";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                int centerId = rs.getInt("centre_id");
                String location = rs.getString("location");

                vaccinecenters.append(centerId + "%%" + location+"&");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (NoSuchElementException e)
        {
            System.out.println(Colours.RED + "Couldn't find any vaccine centers to display." + Colours.RESET);
        }
        catch (NullPointerException e)
        {
            e.getMessage();
            return CAOService.VACCENTRES_EMPTY;
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

        vaccines = vaccinecenters.toString();
        vaccines = (vaccines.substring(0, vaccines.length() - 2));
        return vaccines;
    }

    @Override
    public boolean doesVaccineCenterExist(int centerId) throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            con = this.getConnection();
            String query = "select * from vaccine_centre where center_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, centerId);

            ps.executeQuery();

            if (rs != null) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e)
        {
            System.out.println(e.getMessage());
            result = false;
            return result;
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

            return result;
        }


    }
}
