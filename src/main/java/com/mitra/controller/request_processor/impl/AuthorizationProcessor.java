package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.UserDto;
import com.mitra.entity.Role;
import com.mitra.exception.ValidationException;
import com.mitra.service.ServiceFactory;
import com.mitra.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthorizationProcessor extends AbstractRequestProcessor {

    private static final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static final UserService userService = serviceFactory.getUserService();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, UrlPath.AUTHORIZATION.getJspFileName());
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDto userDto = UserDto.builder()
                .email(email)
                .password(password)
                .role(Role.USER)
                .build();

        try {
            Optional<UserDto> user = userService.tryLogin(userDto);
            if (!user.isPresent()) {
                throw new ValidationException("Credentials are invalid");
            }
            request.getSession().setAttribute(SessionAttributes.USER.name(), user.get());
            redirect(response, UrlPath.MY_PROFILE.get());
        } catch (ValidationException e) {
            redirect(response, UrlPath.AUTHORIZATION.get());
        }
    }
}
