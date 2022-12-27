package com.mitra.controller.request_processor.impl;

import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.AuthHelper;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.dto.CredentialsDto;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationProcessor extends AbstractRequestProcessor {

    private final UserService userService;

    public RegistrationProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, AppUrl.REGISTRATION.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = ParameterHelper.getNecessaryParameter(request, "email");
        String password = ParameterHelper.getNecessaryParameter(request, "password");

        try {
            userService.register(new CredentialsDto(email, password));
            AuthHelper.loginAndUpdateSessionAttrs(email, password, userService, request);
            redirect(response, AppUrl.CREATE_PROFILE.getUrl());
        } catch (ValidationException e) {
            request.setAttribute("errors", e.getErrors());
            processGet(request, response);
        }
    }
}
