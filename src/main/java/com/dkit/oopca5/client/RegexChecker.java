package com.dkit.oopca5.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* This class should contain static methods to verify input in the application
 */
//https://www.javacodeexamples.com/java-regular-expression-validate-date-example-regex/1504
public class RegexChecker
{
    public static final String caoNumberRegex = "[\\d]{8}";
    public static final String dobRegex = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])";
    public static final String passwordRegex = "[\\S]{8,30}";
    public static final String emailregex = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static boolean testEmail(String email)
    {
        Pattern pattern = Pattern.compile(emailregex);

        Matcher matcher = pattern.matcher(email);
        return  matcher.matches();
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

    public static boolean testUserDetails(String email, String dateOfBirth, String password)
    {
        boolean result = false;

        if(testEmail(email) && testDateOfBirth(dateOfBirth) && testPassword(password))
        {
            result = true;
        }

        return result;
    }

}
