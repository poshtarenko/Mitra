package com.mitra.db;

public enum Table {

    USER,
    ROLE,
    PROFILE,
    GENDER,
    MUSIC,
    INSTRUMENT,
    PROFILE_INSTRUMENT,
    SPECIALITY,
    PROFILE_SPECIALITY,
    CITY,
    LOCAL_AREA,
    REGION,
    COUNTRY;

    @Override
    public String toString() {
        return "\"" + super.toString().toLowerCase() + "\"";
    }
}
