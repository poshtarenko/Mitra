package com.mitra.controller;

import com.mitra.exception.PageDontExistException;

import java.util.Arrays;
import java.util.Optional;

public enum UrlPath {

    AUTHORIZATION("/auth"),
    REGISTRATION("/register");

    private final String urlPath;

    public String get() {
        return "/app" + urlPath;
    }

    UrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public static UrlPath getByPath(String path) {
        Optional<UrlPath> result = Arrays.stream(UrlPath.values())
                .filter(urlPath -> urlPath.get().equals(path))
                .findFirst();

        if (result.isPresent())
            return result.get();
        else throw new PageDontExistException("Path " + path + " is not represented");
    }
}
