package com.mitra.controller;

import java.util.Arrays;
import java.util.Optional;

public enum UrlPath {

    LANDING_PAGE("", "landing"),
    AUTHORIZATION("/auth", "authorization"),
    REGISTRATION("/register", "registration"),
    LOGOUT("/logout", ""),
    CREATE_PROFILE("/create_profile", "create_profile"),
    PROFILE("/profile", "profile"),
    MY_PROFILE("/me", "my_profile"),
    UPDATE_PROFILE("/upd_profile", "update_profile"),
    SEARCH("/search", "search_filter"),
    MUSIC("/music", "my_music"),
    SWIPE_SEARCH("/go", "search_swipe"),
    LIKES("/likes", "likes_page"),
    CHAT("/chat", "chat_page"),
    CHATS("/chats", "chats_page"),
    MY_ACCOUNT("/account", "my_account"),
    IMAGES("/images", ""),
    AUDIO("/audio", "");

    private final String urlPath;
    private final String jspFileName;

    public String getUrl() {
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
                .filter(urlPath -> urlPath.getPathWithServletPrefix().contains(path))
                .findFirst();
    }

    // All requests on default servlets are marked with prefix "/app"
    // Another requests don't have prefixes, they use directories names
    // ("resources" for css/js/images, "WEB-INF for jsp, etc)
    public static final String SERVLET_CONST = "/app";
}
