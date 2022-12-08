package com.mitra.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    public boolean emailIsValid(String email) {
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher emailMatcher = emailPattern.matcher(email);

        return emailMatcher.matches();
    }

    public boolean passwordIsValid(String password) {
        Pattern passwordPattern = Pattern.compile("[A-Za-z0-9._%+-]{6,30}");
        Matcher passwordMatcher = passwordPattern.matcher(password);

        return passwordMatcher.matches();
    }
}
