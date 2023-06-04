package org.example.util;

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
}
