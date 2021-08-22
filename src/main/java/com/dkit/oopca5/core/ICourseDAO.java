package com.dkit.oopca5.core;

import com.dkit.oopca5.server.DAOExceptions;

public interface ICourseDAO
{
    String displayCourse(String courseID) throws DAOExceptions;
    String displayAllCourses() throws DAOExceptions;
    boolean doesCourseExist(String courseId) throws DAOExceptions;

}
