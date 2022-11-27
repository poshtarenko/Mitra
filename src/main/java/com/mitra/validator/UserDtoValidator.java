package com.mitra.validator;

import com.mitra.dto.UserDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDtoValidator implements Validator<UserDto> {

    @Override
    public boolean isValid(UserDto dto) {
        return emailIsValid(dto.getEmail())
                && passwordIsValid(dto.getPassword());
    }

    private boolean emailIsValid(String email) {
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher emailMatcher = emailPattern.matcher(email);

        return emailMatcher.matches();

        // TODO : check if there are the same email in the database
    }

    private boolean passwordIsValid(String password) {
        return password.length() >= 6;
    }
}
