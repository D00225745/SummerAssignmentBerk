package com.dkit.oopca5.client.test;

/* This class should contain static methods to verify input in the application
 */
//https://www.javacodeexamples.com/java-regular-expression-validate-date-example-regex/1504
public class RegexChecker
{
    public static final String caoNumberRegex = "[\\d]{8}";
    public static final String dobRegex = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])";
    public static final String passwordRegex = "[\\S]{8,30}";


    public static boolean testCaoNumber(int caoNumber)
    {
        boolean result = false;
        if (Integer.toString(caoNumber).matches(caoNumberRegex))
        {
            result = true;
        }

        return result;
    }

    public static boolean testDateOfBirth(String dateOfBirth)
    {
        boolean result = false;
        if (dateOfBirth.matches(dobRegex))
        {
            result = true;
        }

        return result;
    }

    public static boolean testPassword(String password)
    {
        boolean result = false;
        if (password.matches(passwordRegex))
        {
            result = true;
        }

        return result;
    }

    public static boolean testUserDetails(int caoNumber, String dateOfBirth, String password)
    {
        boolean result = false;

        if(testCaoNumber(caoNumber) && testDateOfBirth(dateOfBirth) && testPassword(password))
        {
            result = true;
        }

        return result;
    }

}

