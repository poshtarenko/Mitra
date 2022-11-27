package com.mitra.entity;

public interface Identifiable<K extends Number> {

    /**
     * This interface marks all Mitra entities
     */

    K getId();

    void setId(K id);
}
