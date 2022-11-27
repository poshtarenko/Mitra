package com.mitra.entity;

public interface Location extends Identifiable<Integer> {
    String getCity();

    String getLocalArea();

    String getRegion();

    String getCountry();

    void setCity(String city);

    void setLocalArea(String localArea);

    void setRegion(String region);

    void setCountry(String country);
}
