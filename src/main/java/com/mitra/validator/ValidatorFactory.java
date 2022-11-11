package com.mitra.validator;

public class ValidatorFactory {

    private static final ValidatorFactory INSTANCE = new ValidatorFactory();

    private UserDtoValidator userDtoValidator;

    private ValidatorFactory() {
        userDtoValidator = new UserDtoValidator();
    }

    public static ValidatorFactory getInstance() {
        return INSTANCE;
    }

    public UserDtoValidator getUserDtoValidator() {
        return userDtoValidator;
    }
}
