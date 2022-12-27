package com.mitra.controller.request_processor.impl;

import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.controller.request_processor.util.SessionAttrAccessor;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountProcessor extends AbstractRequestProcessor {

    private final UserService userService;

    public AccountProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);
        request.setAttribute("user", userService.find(myId).get());
        forward(request, response, AppUrl.MY_ACCOUNT.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        String action = ParameterHelper.getNecessaryParameter(request, "action");

        if (action.equals("UPD_PASSWORD")) {
            String password = ParameterHelper.getNecessaryParameter(request, "password");
            try {
                userService.changePassword(myId, password);
            } catch (ValidationException e) {
                request.setAttribute("passwordErrors", e.getErrors());
            }
        } else if (action.equals("UPD_EMAIL")) {
            String email = ParameterHelper.getNecessaryParameter(request, "email");
            try {
                userService.changeEmail(myId, email);
            } catch (ValidationException e) {
                request.setAttribute("emailErrors", e.getErrors());
            }
        }

        processGet(request, response);
    }
}
