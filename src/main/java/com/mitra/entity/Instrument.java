package com.mitra.entity;

public interface Instrument extends Identifiable<Integer> {
    String getName();

    void setName(String name);
}
