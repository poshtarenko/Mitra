package com.mitra.controller;

import java.util.Arrays;
import java.util.Optional;

public enum PostUrl {

    AUTH("/auth"),
    REGISTER("/register"),
    LOGOUT("/logout"),
    CHANGE_EMAIL("/upd_email"),
    CHANGE_PASSWORD("/upd_password"),
    OPEN_CHAT("/open_chat"),
    PUT_LIKE("/put_like"),
    RESPONSE_ON_LIKE("/send_response"),
    CREATE_PROFILE("/create_profile"),
    UPDATE_PROFILE("/upd_profile"),
    UPDATE_PROFILE_PHOTO("/upd_photo"),
    ADD_TRACK("/add_track"),
    DELETE_TRACK("/delete_track"),
    SEND_MESSAGE("/send_message");

    private final String url;

    public String getUrl() {
        return getPathWithServletPrefix();
    }

    private String getPathWithServletPrefix() {
        return APP_PATH_PREFIX + url;
    }

    PostUrl(String url) {
        this.url = url;
    }

    // All requests on default servlets are marked with prefix "/app"
    // Another requests don't have prefixes, they use directories names
    // ("resources" for css/js/images, "WEB-INF for jsp, etc)
    public static final String APP_PATH_PREFIX = "/app";
}
