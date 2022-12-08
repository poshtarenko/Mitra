package com.mitra.validator;

public class ValidatorFactory {

    private static final ValidatorFactory INSTANCE = new ValidatorFactory();

    private final UserValidator userValidator;

    private ValidatorFactory() {
        userValidator = new UserValidator();
    }

    public static ValidatorFactory getInstance() {
        return INSTANCE;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }
}
