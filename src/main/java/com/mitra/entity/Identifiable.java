package com.mitra.entity;

public interface Identifiable<K extends Number> {
    K getId();
    void setId(K id);
}
