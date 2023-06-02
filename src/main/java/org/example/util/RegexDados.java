package org.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDados {

    public RegexDados() {
    }

    public boolean validateEmail(String email) {
        String emailRegex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(emailRegex);

        if (email == null || email.isEmpty()) {
            return false;
        }

        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isValidPassword(String pass) {// Regex to check valid password.
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-_.[{}]:;',?/*~$^+=<>]).{4,20}$";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (pass == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(pass);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    public boolean isValidCP(String cp) {// Regex to check valid Zip Code.
        String regex = "^[0-9]{4}(?:-[0-9]{3})$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (cp == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(cp);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    public boolean validateJavaDate(String strDate) {
        /* Check if date is 'null' */
        if (strDate.trim().equals("")) {
            return false;
        }
        /* Date is not 'null' */
        else {
            /*
             * Set preferred date format,
             * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.
             */
            SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
            sdfrmt.setLenient(false);
            /*
             * Create Date object
             * parse the string into date
             */
            try {
                Date javaDate = sdfrmt.parse(strDate);
            }
            /* Date format is invalid */ catch (ParseException e) {
                System.out.println(e.getMessage());
                return false;
            }
            /* Return true if date format is valid */
            return true;
        }
    }

    public boolean isValidDateFormat(String input) {
//        String pattern2 = "\\d{2}/\\d{2}/\\d{4}";
        String pattern = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}$";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(input);
        return matcher.matches();
    }

    public boolean isValidDateFormatLocalDate(String input) {
        System.out.println("++++++++++++++++++++++");
        System.out.println("É aqui OH!!! Que se vê a Diferença Oh");
        try {
            LocalDate date = LocalDate.parse(input);
            System.out.println("A data inserida é: " + date);
        } catch (DateTimeException e) {
            System.out.println("Data Inválida");
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("++++++++++++++++++++++");
        return true;
    }
}
