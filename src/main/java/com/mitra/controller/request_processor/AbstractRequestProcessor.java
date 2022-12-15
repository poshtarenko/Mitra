package com.mitra.controller.request_processor;

import com.mitra.controller.request_processor.RequestProcessor;
import com.mitra.util.PropertiesUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractRequestProcessor implements RequestProcessor {

    public static final String JSP_PATH = PropertiesUtil.get("jsp.path");

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        methodNotAllowed(response, "GET");
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        methodNotAllowed(response, "POST");
    }

    @Override
    public void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        methodNotAllowed(response, "DELETE");
    }

    private void methodNotAllowed(HttpServletResponse response, String method) throws IOException {
        String msg = "Method" + method + "is not supported";
        response.sendError(400, msg);
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response, String jspPath) throws ServletException, IOException {
        String completedPath = JSP_PATH + jspPath + ".jsp";
        request.getRequestDispatcher(completedPath)
                .forward(request, response);
    }

    protected void redirect(HttpServletResponse response, String path) throws IOException {
        response.sendRedirect(path);
    }

}
