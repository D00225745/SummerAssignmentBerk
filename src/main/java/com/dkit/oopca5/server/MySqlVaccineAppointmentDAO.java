package com.dkit.oopca5.server;

import com.dkit.oopca5.client.CAOClient;
import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.IVaccineAppointmentDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class MySqlVaccineAppointmentDAO extends MySqlDAO implements IVaccineAppointmentDAO {
    private static MySqlVaccineCentreDAO vaccineCentreDAO = new MySqlVaccineCentreDAO();

    @Override
    public String displayVaccineAppointmentChoice(String email) throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";
        StringBuffer appointments = new StringBuffer();

        try {
            con = this.getConnection();
            String query = "select * from vaccine_appointment as v inner join vaccine_centre p on v.centre_id = p.centre_id  where v.user_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, (email));

            rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int centerId = rs.getInt("centre_id");
                String location = rs.getString("location");
                String time = rs.getString("appointment_time");

                appointments.append(CAOService.SUCCESSFUL_DISPLAY_APPOINMENT + CAOService.BREAKING_CHARACTER +
                        + centerId+ CAOService.BREAKING_CHARACTER +location + CAOService.BREAKING_CHARACTER+time + "&" );

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            e.getMessage();
            return CAOService.VACCENTRES_EMPTY;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.getMessage();
            return CAOService.VACCENTRES_EMPTY;
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

        return appointments.toString();
    }


    @Override
    public String bookVaccineAppointmentChoice(int userId, int center_id, String datetime) throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";

        try {
            con = this.getConnection();

            String query = "insert into vaccine_appointment(user_id,centre_id ,appointment_time) values(?, ?, ?)";
            ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setInt(2, center_id);
            ps.setString(3, datetime);
            ps.executeUpdate();
        } catch (SQLException se) {
            throw new DAOExceptions(se.getMessage());
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
        result = CAOService.SUCCESSFUL_BOOK_VACCINE + CAOService.BREAKING_CHARACTER + center_id
                + CAOService.BREAKING_CHARACTER + datetime;
        return result;
    }

    @Override
    public String updateVaccineAppointmentChoice(int userId, int center_id, String datetime) throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";




        try {
            con = this.getConnection();

            String query = "update vaccine_appointment set appointment_time = ? where user_id = ?";
            ps = con.prepareStatement(query);

            ps.setString(1, datetime);

            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException se) {
            throw new DAOExceptions(se.getMessage());
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
        result = CAOService.UPDATE_CURRENT_APPOINTMENT_SUCCESS + CAOService.BREAKING_CHARACTER + center_id
                + CAOService.BREAKING_CHARACTER + datetime;
        return result;
    }

}
