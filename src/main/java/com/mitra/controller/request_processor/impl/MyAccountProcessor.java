package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.util.ParameterHelper;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAccountProcessor extends AbstractRequestProcessor {

    private final UserService userService;

    public MyAccountProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());
        request.setAttribute("user", userService.find(myId).get());
        forward(request, response, UrlPath.MY_ACCOUNT.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (ParameterHelper.redirectIfParameterIsEmpty(response, action, UrlPath.MY_ACCOUNT.getUrl()))
            return;

        int myId = (int) request.getSession().getAttribute(SessionAttributes.USER_ID.name());

        if (action.equals("UPD_PASSWORD")) {
            String password = request.getParameter("password");
            if (ParameterHelper.redirectIfParameterIsEmpty(response, password, UrlPath.MY_ACCOUNT.getUrl()))
                return;
            try {
                userService.changePassword(myId, password);
            } catch (ValidationException e) {
                request.setAttribute("passwordErrors", e.getErrors());
            }
        } else if (action.equals("UPD_EMAIL")) {
            String email = request.getParameter("email");
            if (ParameterHelper.redirectIfParameterIsEmpty(response, email, UrlPath.MY_ACCOUNT.getUrl()))
                return;
            try {
                userService.changeEmail(myId, email);
            } catch (ValidationException e) {
                request.setAttribute("emailErrors", e.getErrors());
            }
        }

        processGet(request, response);
    }
}
