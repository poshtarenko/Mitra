package com.mitra.entity;

public interface Track {
    String getName();

    String getAuthor();

    String getFilePath();

    Profile getOwner();

    void setName(String name);

    void setAuthor(String author);

    void setFilePath(String filePath);

    void setOwner(Profile owner);
}
