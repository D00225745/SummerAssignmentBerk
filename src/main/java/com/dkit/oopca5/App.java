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
        User userOne = new User(12345678, "useremail@gmail.com", "123456789");
        User userTwo = new User(123456789, "useremail@gmail.com", "123456789");
        User userThree = new User(12345678, "useremail@hotmail.com", "123456789");
        User userFour = new User(12345678, "useremail@hotmail.com", "1234567");

        ArrayList<User> users = new ArrayList();

        System.out.println(RegexChecker.testUserId(userOne.getUserId()));
        System.out.println(RegexChecker.testEmail(userOne.getEmail()));
        System.out.println(RegexChecker.testPassword(userOne.getPassword()));


        System.out.println(RegexChecker.testUserId(userTwo.getUserId()));
        System.out.println(RegexChecker.testEmail(userThree.getEmail()));
        System.out.println(RegexChecker.testPassword(userFour.getPassword()));
    }
}
