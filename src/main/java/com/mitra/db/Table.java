package com.mitra.db;

public enum Table {

    USER,
    ROLE;

    @Override
    public String toString() {
        return "\"" + super.toString().toLowerCase() + "\"";
    }
}
