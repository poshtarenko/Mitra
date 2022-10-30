package com.mitra.controller.request_processor.impl;

import com.mitra.controller.HttpMethod;
import com.mitra.controller.request_processor.RequestProcessor;
import com.mitra.util.PropertiesUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

abstract class AbstractRequestProcessor implements RequestProcessor {

    public static final String JSP_PATH = PropertiesUtil.get("jsp.path");

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        methodNotAllowed(request, response, HttpMethod.GET);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        methodNotAllowed(request, response, HttpMethod.POST);
    }

    @Override
    public void processPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        methodNotAllowed(request, response, HttpMethod.PATCH);
    }

    @Override
    public void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        methodNotAllowed(request, response, HttpMethod.DELETE);
    }

    private void methodNotAllowed(HttpServletRequest request, HttpServletResponse response, HttpMethod method) throws IOException {
        String msg = "Method" + method.name().toUpperCase() + "is not supported";
        response.sendError(400, msg);
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        String completedPath = JSP_PATH + path + ".jsp";
        request.getRequestDispatcher(completedPath)
                .forward(request, response);
    }

    protected void redirect(HttpServletResponse response, String path) throws IOException {
        String completedPath = JSP_PATH + path + ".jsp";
        response.sendRedirect(completedPath);
    }

}
