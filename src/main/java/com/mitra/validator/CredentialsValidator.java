package com.mitra.validator;

import com.mitra.dto.CredentialsDto;
import com.mitra.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialsValidator {

    public void checkCredentials(CredentialsDto credentials) throws ValidationException {
        List<Error> errors = new ArrayList<>();
        checkEmail(credentials.getEmail()).ifPresent(errors::add);
        checkPassword(credentials.getPassword()).ifPresent(errors::add);

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }

    public Optional<Error> checkEmail(String email) {
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher emailMatcher = emailPattern.matcher(email);

        if (emailMatcher.matches())
            return Optional.empty();
        else
            return Optional.of(Error.of("WRONG EMAIL", "Введіть правильний e-mail"));
    }

    public Optional<Error> checkPassword(String password) {
        Pattern passwordPattern = Pattern.compile("[A-Za-z0-9_%]{6,30}");
        Matcher passwordMatcher = passwordPattern.matcher(password);

        if (passwordMatcher.matches())
            return Optional.empty();
        else
            return Optional.of(Error.of("WRONG PASSWORD", "Пароль має мати більше 6 символів, та " +
                    "складатися з латинських букв, цифр або символів '_' та '%'"));
    }
}
