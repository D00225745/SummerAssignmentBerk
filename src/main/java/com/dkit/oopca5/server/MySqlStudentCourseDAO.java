package com.dkit.oopca5.server;

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.IStudentCourseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class MySqlStudentCourseDAO extends MySqlDAO implements IStudentCourseDAO
{
    private static MySqlCourseDAO courseDAO = new MySqlCourseDAO();

    @Override
    public String displayCurrentChoice(int caoNumber) throws DAOExceptions
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";
        StringBuffer currentChoices = new StringBuffer();

        try
        {
            con = this.getConnection();
            String query = "select * from student_course where cao_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(caoNumber));

            ps.executeQuery();

            while(rs.next())
            {
                String courseId = rs.getString("courseId");

                currentChoices.append(courseDAO.displayCourse(courseId));
                currentChoices.append("\n\n");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (NullPointerException e)
        {
            e.getMessage();
            return CAOService.COURSES_EMPTY;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            e.getMessage();
            return CAOService.COURSES_EMPTY;
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

        return currentChoices.toString();
    }

    //Didn't know how to make multiple choices...
    @Override
    public String updateCurrentChoice(String caoNumber, String choice) throws DAOExceptions
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";

        try
        {
            con = this.getConnection();
            String query = "delete * from student_choices where cao_number = ?; insert into student_course values(?, ?)";

            int caoNum = Integer.parseInt(caoNumber);

            ps.setInt(1, caoNum);
            ps.setInt(2, caoNum);
            ps.setString(3, choice);

            ps.executeUpdate();

            if(!displayCurrentChoice(caoNum).equals(CAOService.COURSES_EMPTY))
            {
                result = CAOService.UPDATE_CURRENT_CHOICE_SUCCESS;
            }
            else
            {
                result = CAOService.UPDATE_CURRENT_CHOICE_FAILED;
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
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

        return  result;
    }
}
