package com.mitra.controller.impl.get;

import com.mitra.controller.Cookies;
import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.util.ControllerUtils;
import com.mitra.controller.impl.util.CookieHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.ProfileDto;
import com.mitra.service.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FastSearchController implements GetController {

    private static final char COOKIE_DELIMITER = 'x';

    private final ProfileService profileService;

    public FastSearchController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void processGet(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = CookieHelper.getCookie(request, Cookies.PROFILE_IDS.name());
        String cookieValue = null;

        if (cookie != null) {
            cookieValue = cookie.getValue();
        }

        if (cookieValue == null || cookieValue.equals("")) {
            int myId = SessionAttrAccessor.getProfileId(request);
            List<Integer> allIds = profileService.getIdsForSwipeSearch(myId);

            if (allIds.isEmpty()) {
                ControllerUtils.forward(request, response, GetUrl.FAST_SEARCH.getJspFileName());
            }

            cookieValue = profileIdsToString(allIds);
            CookieHelper.updateCookie(response, Cookies.PROFILE_IDS.name(), cookieValue);
        }

        int id = extractFirstNumberFromString(cookieValue);
        Optional<ProfileDto> profileOptional = profileService.find(id);

        // update cookie with new value
        cookieValue = cutProfileIds(cookieValue, id);
        CookieHelper.updateCookie(response, Cookies.PROFILE_IDS.name(), cookieValue);

        if (!profileOptional.isPresent()) {
            response.sendRedirect(GetUrl.FAST_SEARCH.getUrl());
            return;
        }

        request.setAttribute("profile", profileOptional.get());
        request.setAttribute("nextProfileId", id);

        ControllerUtils.forward(request, response, GetUrl.FAST_SEARCH.getJspFileName());
    }

    public String profileIdsToString(List<Integer> profileIds) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer profileId : profileIds) {
            stringBuilder.append(profileId).append(COOKIE_DELIMITER);
        }
        return stringBuilder.toString();
    }

    public int extractFirstNumberFromString(String profileIds) {
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < profileIds.length(); i++) {
            char sym = profileIds.charAt(i);

            if (sym != COOKIE_DELIMITER)
                number.append(sym);
            else
                break;
        }

        return Integer.parseInt(number.toString());
    }

    public String cutProfileIds(String profileIds, int profileId) {
        String profileIdsTemp = COOKIE_DELIMITER + profileIds;
        String substrToCut = COOKIE_DELIMITER + String.valueOf(profileId) + COOKIE_DELIMITER;
        return profileIdsTemp.replace(substrToCut, String.valueOf(COOKIE_DELIMITER)).substring(1);
    }
}
