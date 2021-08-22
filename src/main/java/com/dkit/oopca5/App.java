package com.dkit.oopca5;

import com.dkit.oopca5.client.RegexChecker;
import com.dkit.oopca5.core.User;

import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {


        //testRegex();

    }

    private static void testRegex()
    {
        User userOne = new User(12345678, "2000-12-11", "123456789");
        User userTwo = new User(123456789, "2000-12-11", "123456789");
        User userThree = new User(12345678, "2000-13-11", "123456789");
        User userFour = new User(12345678, "2000-13-11", "1234567");

        ArrayList<User> users = new ArrayList();

        System.out.println(RegexChecker.testCaoNumber(userOne.getCaoNumber()));
        System.out.println(RegexChecker.testDateOfBirth(userOne.getDateOfBirth()));
        System.out.println(RegexChecker.testPassword(userOne.getPassword()));


        System.out.println(RegexChecker.testCaoNumber(userTwo.getCaoNumber()));
        System.out.println(RegexChecker.testDateOfBirth(userThree.getDateOfBirth()));
        System.out.println(RegexChecker.testPassword(userFour.getPassword()));
    }
}
