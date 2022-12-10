package com.mitra.validator;

import java.util.Optional;

public class ProfileValidator {
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
