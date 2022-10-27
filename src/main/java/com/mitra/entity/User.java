package com.mitra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Identifiable<Integer> {
    private Integer id;
    private String email;
    private String password;
    private Role role;
}
