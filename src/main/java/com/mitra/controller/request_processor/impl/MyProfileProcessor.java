package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyProfileProcessor extends AbstractRequestProcessor {
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProfileDto profile = ((UserDto) request.getSession().getAttribute(SessionAttributes.USER.name())).getProfile();
        request.setAttribute("profile", profile);
        forward(request, response, UrlPath.MY_PROFILE.getJspFileName());
    }
}
