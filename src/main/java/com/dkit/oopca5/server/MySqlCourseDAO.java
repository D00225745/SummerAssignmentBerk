package com.dkit.oopca5.server;

import com.dkit.oopca5.core.CAOService;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.ICourseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class MySqlCourseDAO extends MySqlDAO implements ICourseDAO {

    @Override
    public String displayCourse(String courseId) throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String courseDetails = "none";

        try {
            con = this.getConnection();
            String query = "select * from course where course_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, courseId);

            ps.executeQuery();

            while (rs.next() && rs != null) {
                String level = rs.getString("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");

                courseDetails = ("Course Id: " + courseId + "\nLevel: " + level + "\nTitle: " + title + "\nInstitution" + institution);
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
    public String displayAllCourses() throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer courses = new StringBuffer();
        String courseDetails = "0";

        try {
            con = this.getConnection();
            String query = "select * from course";
            ps = con.prepareStatement(query);

            ps.executeQuery();

            while (rs.next() && rs != null) {
                String courseId = rs.getString("course_id");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");

                courses.append("\nCourse Id: " + courseId + "\tLevel: " + level + "\tTitle: " + title + "\tInstitution: " + institution);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (NoSuchElementException e)
        {
            System.out.println(Colours.RED + "Couldn't find any courses to display." + Colours.RESET);
        }
        catch (NullPointerException e)
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

        courseDetails = courses.toString();
        return courseDetails;
    }

    @Override
    public boolean doesCourseExist(String courseId) throws DAOExceptions {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            con = this.getConnection();
            String query = "select * from course where course_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, courseId);

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
