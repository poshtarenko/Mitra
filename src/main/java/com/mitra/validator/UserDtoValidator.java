package com.mitra.validator;

import com.mitra.dto.UserDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDtoValidator implements Validator<UserDto> {

    private static final UserDtoValidator INSTANCE = new UserDtoValidator();

    private UserDtoValidator() {

    }

    public static UserDtoValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValid(UserDto dto) {
        String email = dto.getEmail();
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher emailMatcher = emailPattern.matcher(email);
        if (!emailMatcher.matches()) {
            return false;
        }

        String password = dto.getPassword();
        if (password.length() < 6)
            return false;

        return true;
    }
}
