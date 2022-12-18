package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.LoginHelper;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationProcessor extends AbstractRequestProcessor {

    private final UserService userService;

    public AuthorizationProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, AppUrl.AUTHORIZATION.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = ParameterHelper.getNecessaryParameter(request, "email");
        String password = ParameterHelper.getNecessaryParameter(request, "password");

        try {
            LoginHelper.loginAndUpdateSessionAttrs(email, password, userService, request);
            redirect(response, AppUrl.MY_PROFILE.getUrl());
        } catch (ValidationException e) {
            request.setAttribute("errors", e.getErrors());
            processGet(request, response);
        }
    }
}
