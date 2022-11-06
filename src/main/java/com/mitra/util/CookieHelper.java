package com.mitra.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class CookieHelper {

    private CookieHelper() {
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }

}
