package com.mitra.controller.request_processor.impl;

import com.mitra.service.UserService;
import com.mitra.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthorizationProcessor extends AbstractRequestProcessor {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, "auth");
    }
}
