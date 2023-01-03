package com.mitra.validator;

import com.mitra.dto.ProfileDto;
import com.mitra.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileValidator {

    public void checkProfile(ProfileDto profile) throws ValidationException {
        List<Error> errors = new ArrayList<>();
        checkName(profile.getName()).ifPresent(errors::add);
        checkAge(profile.getAge()).ifPresent(errors::add);
        checkText(profile.getText()).ifPresent(errors::add);

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }

    public Optional<Error> checkName(String name) {
        if (name.length() > 3 && name.length() < 21)
            return Optional.empty();
        else
            return Optional.of(Error.of("WRONG PROFILE NAME",
                    "Ім'я повинно містити від 4 до 21 символів"));
    }

    public Optional<Error> checkAge(int age) {
        if (age > 5 && age < 100)
            return Optional.empty();
        else
            return Optional.of(Error.of("WRONG PROFILE AGE",
                    "Вік може бути у межах від 5 до 100"));
    }

    public Optional<Error> checkText(String text) {
        if (text.length() < 500)
            return Optional.empty();
        else
            return Optional.of(Error.of("WRONG PROFILE TEXT",
                    "Текст анкети може містити максимум 500 символів"));
    }
}
