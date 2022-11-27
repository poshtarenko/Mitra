package com.mitra.entity;

public interface User {
    String getEmail();

    String getPassword();

    Role getRole();

    Profile getProfile();

    void setEmail(String email);

    void setPassword(String password);

    void setRole(Role role);

    void setProfile(Profile profile);
}
