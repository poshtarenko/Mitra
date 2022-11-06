package com.mitra.controller.request_processor.impl;

import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.CookieNames;
import com.mitra.service.ProfileService;
import com.mitra.service.ServiceFactory;
import com.mitra.util.CookieHelper;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SearchBySlideProcessor extends AbstractRequestProcessor {

    private static final char delimiter = 'x';

    private final ProfileService profileService = ServiceFactory.getInstance().getProfileService();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie profileIdsCookie = CookieHelper.getCookie(request, CookieNames.PROFILE_IDS.name());
        String profileIdsCookieValue;
        if (profileIdsCookie == null
                || profileIdsCookie.getValue() == null
                || profileIdsCookie.getValue().equals("")) {
            profileIdsCookieValue = profileIdsToString(profileService.getAllIds());
            response.addCookie(createProfileIdsCookie(profileIdsCookieValue));
        } else {
            profileIdsCookieValue = profileIdsCookie.getValue();
        }

        String profileIdParam = request.getParameter("i");
        int profileId;
        if (profileIdParam == null || profileIdParam.equals(""))
            profileId = getProfileIdFromString(profileIdsCookieValue);
        else
            profileId = Integer.parseInt(profileIdParam);

        request.setAttribute("nextProfileId", getProfileIdFromString(profileIdsCookieValue));
        request.setAttribute("profile", profileService.getByUserId(profileId).get());

        profileIdsCookieValue = getCutProfileIdsCookie(profileIdsCookieValue);
        response.addCookie(createProfileIdsCookie(profileIdsCookieValue));

        forward(request, response, UrlPath.SLIDE_SEARCH.getJspFileName());
    }

    private String profileIdsToString(List<Integer> profileIds) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer profileId : profileIds) {
            stringBuilder.append(profileId).append(delimiter);
        }
        return stringBuilder.toString();
    }

    private int getProfileIdFromString(String profileIds) {
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < profileIds.length(); i++) {
            char sym = profileIds.charAt(i);

            if (sym != delimiter)
                number.append(sym);
            else
                break;
        }

        return Integer.parseInt(number.toString());
    }

    private String getCutProfileIdsCookie(String profileIds) {
        StringBuilder cutProfileIds = new StringBuilder(profileIds);
        int l = cutProfileIds.length();
        for (int i = 0; i < l; i++) {
            char sym = cutProfileIds.charAt(0);

            cutProfileIds.deleteCharAt(0);
            if (sym == delimiter)
                break;
        }

        return cutProfileIds.toString();
    }

    private Cookie createProfileIdsCookie(String value) {
        Cookie cookie = new Cookie(CookieNames.PROFILE_IDS.name(), value);
        cookie.setMaxAge(8 * 60 * 60); // 8 hours
        return cookie;
    }
}
