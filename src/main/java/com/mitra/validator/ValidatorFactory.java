package com.mitra.validator;

public class ValidatorFactory {

    private static final ValidatorFactory INSTANCE = new ValidatorFactory();

    private final UserValidator userValidator;
    private final ProfileValidator profileValidator;

    private ValidatorFactory() {
        userValidator = new UserValidator();
        profileValidator = new ProfileValidator();
    }

    public static ValidatorFactory getInstance() {
        return INSTANCE;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }

    public ProfileValidator getProfileValidator() {
        return profileValidator;
    }
}
