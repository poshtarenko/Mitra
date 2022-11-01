package com.mitra.controller;

import java.util.Arrays;
import java.util.Optional;

public enum UrlPath {

    LANDING_PAGE("", "landing"),
    AUTHORIZATION("/auth", "authorization"),
    REGISTRATION("/register", "registration");

    private final String urlPath;
    private final String jspFileName;

    public String get() {
        return getPathWithServletPrefix();
    }

    private String getPathWithServletPrefix() {
        return SERVLET_CONST + urlPath;
    }

    public String getJspFileName() {
        return jspFileName;
    }

    UrlPath(String urlPath, String jspFileName) {
        this.jspFileName = jspFileName;
        this.urlPath = urlPath;
    }

    public static Optional<UrlPath> getByPath(String path) {
        return Arrays.stream(UrlPath.values())
                .filter(urlPath -> urlPath.getPathWithServletPrefix().equals(path))
                .findFirst();
    }

    // All requests on default servlets are marked with prefix "/app"
    // Another requests don't have prefixes, they use directories names
    // ("resources" for css/js/images, "WEB-INF for jsp, etc)
    public static final String SERVLET_CONST = "/app";
}
