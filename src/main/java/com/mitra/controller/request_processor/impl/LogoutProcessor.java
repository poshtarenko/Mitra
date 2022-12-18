package com.mitra.controller.request_processor.impl;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.AbstractRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutProcessor extends AbstractRequestProcessor {
    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(SessionAttributes.USER_ID.name(), null);
        redirect(response, AppUrl.AUTHORIZATION.getUrl());
    }
}
