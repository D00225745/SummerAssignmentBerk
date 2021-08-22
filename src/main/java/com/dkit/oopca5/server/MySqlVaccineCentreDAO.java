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
    public String displayCentre(String centreId) throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String centreDetails = "none";

        try {
            con = this.getConnection();
            String query = "select * from centre where course_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, centreId);

            ps.executeQuery();

            while (rs.next() && rs != null) {
                String level = rs.getString("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");

                centreDetails = ("VaccineCentre Id: " + centreId + "\nLevel: " + level + "\nTitle: " + title + "\nInstitution" + institution);
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

        return centreDetails;
    }

    @Override
    public String displayAllCentres() throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer centres = new StringBuffer();
        String centreDetails = "0";

        try {
            con = this.getConnection();
            String query = "select * from centre";
            ps = con.prepareStatement(query);

            ps.executeQuery();

            while (rs.next() && rs != null) {
                String centreId = rs.getString("centre_id");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");

                centres.append("\nVaccineCentre Id: " + centreId + "\tLevel: " + level + "\tTitle: " + title + "\tInstitution: " + institution);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (NoSuchElementException e)
        {
            System.out.println(Colours.RED + "Could not find any vaccine centres to display." + Colours.RESET);
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

        centreDetails = centres.toString();
        return centreDetails;
    }

    @Override
    public boolean doesCentreExist(String centreId) throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            con = this.getConnection();
            String query = "select * from vaccine centre where centre_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, centreId);

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
