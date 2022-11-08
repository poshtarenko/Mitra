package com.mitra.controller.request_processor.impl;

import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.CookieNames;
import com.mitra.dto.ProfileDto;
import com.mitra.service.ProfileService;
import com.mitra.service.ServiceFactory;
import com.mitra.util.CookieHelper;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SearchBySwipeProcessor extends AbstractRequestProcessor {

    private static final char delimiter = 'x';

    private final ProfileService profileService = ServiceFactory.getInstance().getProfileService();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie profileIdsCookie = CookieHelper.getCookie(request, CookieNames.PROFILE_IDS.name());
        // update cookie if not exists
        if (profileIdsCookie == null) {
            profileIdsCookie = updateProfileIdsCookie(response, profileIdsToString(profileService.getAllIds()));
        }

        String profileIdsCookieValue = profileIdsCookie.getValue();
        // update cookie if value is incorrect
        if (profileIdsCookieValue == null || profileIdsCookieValue.equals("")){
            profileIdsCookieValue = profileIdsToString(profileService.getAllIds());
            updateProfileIdsCookie(response, profileIdsCookieValue);
        }

        int id = getProfileIdFromString(profileIdsCookieValue);

        Optional<ProfileDto> profileOptional = profileService.getById(id);
        if (!profileOptional.isPresent()){
            redirect(response, UrlPath.SLIDE_SEARCH.get());
        }

        request.setAttribute("profile", profileOptional.get());
        request.setAttribute("nextProfileId", id);

        // update cookie with new value
        profileIdsCookieValue = cutProfileIds(profileIdsCookieValue, id);
        updateProfileIdsCookie(response, profileIdsCookieValue);

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

    private String cutProfileIds(String profileIds, int profileId) {
        String profileIds1 = delimiter + profileIds;
        String substrToCut = delimiter + String.valueOf(profileId) + delimiter;
        return profileIds1.replace(substrToCut, String.valueOf(delimiter)).substring(1);
    }

    private Cookie updateProfileIdsCookie(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie(CookieNames.PROFILE_IDS.name(), value);
        cookie.setMaxAge(8 * 60 * 60); // 8 hours
        response.addCookie(cookie);
        return cookie;
    }
}
