package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.util.LoginHelper;
import com.mitra.dto.UserDto;
import com.mitra.entity.Role;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class RegistrationProcessor extends AbstractRequestProcessor {

    private final UserService userService;

    public RegistrationProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, UrlPath.REGISTRATION.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(SessionAttributes.USER_ID.name()) != null) {
            redirect(response, UrlPath.MY_PROFILE.getUrl());
            return;
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");


        try {
            userService.register(email, password);

            LoginHelper.login(email, password, userService, request);

            redirect(response, UrlPath.CREATE_PROFILE.getUrl());
        } catch (ValidationException e) {
            redirect(response, UrlPath.REGISTRATION.getUrl());
        }
    }
}
