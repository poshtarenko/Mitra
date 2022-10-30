package com.mitra.controller.request_processor.impl;

import com.mitra.controller.UrlPath;
import com.mitra.dto.UserDto;
import com.mitra.entity.Role;
import com.mitra.exception.ValidationException;
import com.mitra.service.UserService;
import com.mitra.service.impl.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class AuthorizationProcessor extends AbstractRequestProcessor {

    private static final UserService userService = UserServiceImpl.getInstance();

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
            if (!user.isPresent()){
                throw new ValidationException("Credentials are invalid");
            }
            request.getSession().setAttribute("USER", user.get());
            redirect(response, "SOME SUCCESS PAGE"); // TODO : change redirect page when it will be realized
        } catch (ValidationException e) {
            redirect(response, UrlPath.AUTHORIZATION.get());
        }
    }
}
