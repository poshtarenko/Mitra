package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.AuthHelper;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.dto.CredentialsDto;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RegisterNewUserController implements PostController {

    private final UserService userService;

    public RegisterNewUserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = ParameterHelper.getNecessaryParameter(request, "email");
        String password = ParameterHelper.getNecessaryParameter(request, "password");

        try {
            userService.register(new CredentialsDto(email, password));
            AuthHelper.loginAndUpdateSessionAttrs(email, password, userService, request);
            response.sendRedirect(GetUrl.CREATE_PROFILE.getUrl());
        } catch (ValidationException e) {
            log.info("Trying to register with invalid credentials {} : {}. {}", email, password, e.getErrors());
            throw e;
        }
    }
}
