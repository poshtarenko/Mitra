package com.mitra.controller;

public enum GetUrl {

    LANDING_PAGE("", "landing"),
    AUTHORIZATION("/auth", "authorization"),
    REGISTRATION("/register", "registration"),
    LOGOUT("/logout", ""),
    CREATE_PROFILE("/create_profile", "create_profile"),
    PROFILE("/profile", "profile"),
    MY_PROFILE("/me", "my_profile"),
    UPDATE_PROFILE("/upd_profile", "update_profile"),
    SEARCH("/search", "search"),
    MUSIC("/music", "music"),
    FAST_SEARCH("/go", "go"),
    LIKES("/likes", "likes"),
    CHAT("/chat", "chat"),
    CHATS("/chats", "chats"),
    MY_ACCOUNT("/account", "account"),
    IMAGES("/images", ""),
    AUDIO("/audio", "");

    private final String url;
    private final String jspFileName;

    public String getUrl() {
        return getPathWithServletPrefix();
    }

    private String getPathWithServletPrefix() {
        return APP_PATH_PREFIX + url;
    }

    public String getJspFileName() {
        return jspFileName;
    }

    GetUrl(String url, String jspFileName) {
        this.jspFileName = jspFileName;
        this.url = url;
    }

    // All requests on default servlets are marked with prefix "/app"
    // Another requests don't have prefixes, they use directories names
    // ("resources" for css/js/images, "WEB-INF for jsp, etc)
    public static final String APP_PATH_PREFIX = "/app";
}
