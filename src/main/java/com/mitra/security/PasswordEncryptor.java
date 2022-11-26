package com.mitra.security;

public interface PasswordEncryptor {

    /**
     * Encrypt password
     *
     * @param password password
     * @return encrypted password
     */
    String encrypt(String password);

}
