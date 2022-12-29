package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.AuthHelper;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthorizeUserController implements PostController {

    private final UserService userService;

    public AuthorizeUserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = ParameterHelper.getNecessaryParameter(request, "email");
        String password = ParameterHelper.getNecessaryParameter(request, "password");

        try {
            AuthHelper.loginAndUpdateSessionAttrs(email, password, userService, request);
            response.sendRedirect(GetUrl.MY_PROFILE.getUrl());
        } catch (ValidationException e) {
            log.info("Trying to login with wrong credentials {} : {}. {}", email, password, e.getErrors());
            throw e;
        }
    }
}
