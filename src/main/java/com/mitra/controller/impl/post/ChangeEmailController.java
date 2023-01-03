package com.mitra.controller.impl.post;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.PostController;
import com.mitra.controller.impl.util.ParameterHelper;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ChangeEmailController implements PostController {

    private final UserService userService;

    public ChangeEmailController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int myId = SessionAttrAccessor.getProfileId(request);

        String email = ParameterHelper.getNecessaryParameter(request, "email");
        try {
            userService.changeEmail(myId, email);
        } catch (ValidationException e) {
            log.info("Trying to change email on invalid one - {}. {}", email, e.getErrors());
            throw e;
        }

        response.sendRedirect(GetUrl.MY_ACCOUNT.getUrl());
    }
}
