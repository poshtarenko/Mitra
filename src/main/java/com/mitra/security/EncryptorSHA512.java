package com.mitra.security;

import com.mitra.util.PropertiesUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptorSHA512 implements PasswordEncryptor{

    private static final EncryptorSHA512 INSTANCE = new EncryptorSHA512();

    public static EncryptorSHA512 getInstance() {
        return INSTANCE;
    }

    private EncryptorSHA512() {
    }

    private static final String salt = PropertiesUtil.get("security.salt");

    @Override
    public String encrypt(String password) {
        return encrypt(password, salt);
    }

    public String encrypt(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return generatedPassword;
    }
}
