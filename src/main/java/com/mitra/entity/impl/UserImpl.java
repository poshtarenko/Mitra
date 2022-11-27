package com.mitra.entity.impl;

import com.mitra.entity.Profile;
import com.mitra.entity.Role;
import com.mitra.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserImpl implements User {
    private Integer id;
    private String email;
    private String password;
    private Role role;
    private Profile profile;

    public UserImpl(int id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
