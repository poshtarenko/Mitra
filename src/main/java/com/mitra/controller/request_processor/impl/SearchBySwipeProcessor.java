package com.mitra.controller.request_processor.impl;

import com.mitra.controller.Cookies;
import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.dto.ProfileDto;
import com.mitra.service.ProfileLikeService;
import com.mitra.service.ProfileService;
import com.mitra.util.CookieHelper;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SearchBySwipeProcessor extends AbstractRequestProcessor {

    private static final char DELIMITER = 'x';

    private final ProfileService profileService;
    private final ProfileLikeService profileLikeService;

    public SearchBySwipeProcessor(ProfileService profileService, ProfileLikeService profileLikeService) {
        this.profileService = profileService;
        this.profileLikeService = profileLikeService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());

        Cookie profileIdsCookie = CookieHelper.getCookie(request, Cookies.PROFILE_IDS.name());
        // update cookie if not exists
        if (profileIdsCookie == null) {
            List<Integer> allIds = profileService.getAllIDs();
            allIds.remove(Integer.valueOf(myId));
            profileIdsCookie = updateProfileIdsCookie(response, profileIdsToString(allIds));
        }

        String profileIdsCookieValue = profileIdsCookie.getValue();
        // update cookie if value is incorrect
        if (profileIdsCookieValue == null || profileIdsCookieValue.equals("")) {
            List<Integer> allIds = profileService.getAllIDs();
            allIds.remove(Integer.valueOf(myId));
            profileIdsCookieValue = profileIdsToString(allIds);
            updateProfileIdsCookie(response, profileIdsCookieValue);
        }

        int id = getProfileIdFromString(profileIdsCookieValue);

        Optional<ProfileDto> profileOptional = profileService.find(id);
        if (!profileOptional.isPresent()) {
            redirect(response, AppUrl.SWIPE_SEARCH.getUrl());
            return;
        }

        request.setAttribute("profile", profileOptional.get());
        request.setAttribute("nextProfileId", id);

        // update cookie with new value
        profileIdsCookieValue = cutProfileIds(profileIdsCookieValue, id);
        updateProfileIdsCookie(response, profileIdsCookieValue);

        // skip profile if we already liked it or this user liked us
        if (profileLikeService.getLike(myId, id).isPresent()
                || profileLikeService.getLike(id, myId).isPresent()) {
            redirect(response, AppUrl.SWIPE_SEARCH.getUrl());
            return;
        }

        forward(request, response, AppUrl.SWIPE_SEARCH.getJspFileName());
    }

    public String profileIdsToString(List<Integer> profileIds) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer profileId : profileIds) {
            stringBuilder.append(profileId).append(DELIMITER);
        }
        return stringBuilder.toString();
    }

    public int getProfileIdFromString(String profileIds) {
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < profileIds.length(); i++) {
            char sym = profileIds.charAt(i);

            if (sym != DELIMITER)
                number.append(sym);
            else
                break;
        }

        return Integer.parseInt(number.toString());
    }

    public String cutProfileIds(String profileIds, int profileId) {
        String profileIds1 = DELIMITER + profileIds;
        String substrToCut = DELIMITER + String.valueOf(profileId) + DELIMITER;
        return profileIds1.replace(substrToCut, String.valueOf(DELIMITER)).substring(1);
    }

    public Cookie updateProfileIdsCookie(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie(Cookies.PROFILE_IDS.name(), value);
        cookie.setMaxAge(8 * 60 * 60); // 8 hours
        response.addCookie(cookie);
        return cookie;
    }
}
