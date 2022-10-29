package com.mitra.controller;

import com.mitra.exception.PageDontExistException;

import java.util.Arrays;
import java.util.Optional;

public enum UrlPath {

    AUTHORIZATION("/auth", "authorization"),
    REGISTRATION("/register", "registration");

    private final String urlPath;
    private final String jspFileName;

    public static final String SERVLET_CONST = "/app";

    public String get() {
        return SERVLET_CONST + urlPath;
    }

    public String getJspFileName() {
        return jspFileName;
    }

    UrlPath(String urlPath, String jspFileName) {
        this.jspFileName = jspFileName;
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
