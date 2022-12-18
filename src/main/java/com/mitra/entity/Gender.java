package com.mitra.entity;

import java.util.Arrays;

public enum Gender {
    MALE(1),
    FEMALE(2);

    private final int id;

    Gender(int id) {
        this.id = id;
    }

    public static Gender getById(int id) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wrong gender id"));
    }

    public int getId() {
        return id;
    }
}
