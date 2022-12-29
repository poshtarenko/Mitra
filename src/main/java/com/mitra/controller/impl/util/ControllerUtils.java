package com.mitra.controller.impl.util;

import com.mitra.util.PropertiesUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ControllerUtils {

    public static final String JSP_PATH = PropertiesUtil.get("jsp.path");

    public static void forward(HttpServletRequest request, HttpServletResponse response, String jspPath) throws ServletException, IOException {
        String completedPath = JSP_PATH + jspPath + ".jsp";
        request.getRequestDispatcher(completedPath)
                .forward(request, response);
    }

    public static void redirectOnReferer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String referer = req.getHeader("referer");
        int index = referer.indexOf("/app");
        resp.sendRedirect(referer.substring(index));
    }

}
