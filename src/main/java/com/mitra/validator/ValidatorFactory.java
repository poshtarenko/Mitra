package com.mitra.validator;

public class ValidatorFactory {

    private static final ValidatorFactory INSTANCE = new ValidatorFactory();

    private final CredentialsValidator credentialsValidator;
    private final ProfileValidator profileValidator;
    private final MessageValidator messageValidator;

    private ValidatorFactory() {
        credentialsValidator = new CredentialsValidator();
        profileValidator = new ProfileValidator();
        messageValidator = new MessageValidator();
    }

    public static ValidatorFactory getInstance() {
        return INSTANCE;
    }

    public CredentialsValidator getCredentialsValidator() {
        return credentialsValidator;
    }

    public ProfileValidator getProfileValidator() {
        return profileValidator;
    }

    public MessageValidator getMessageValidator() {
        return messageValidator;
    }
}
