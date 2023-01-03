package com.mitra.controller;

import com.mitra.controller.impl.ControllerFactory;
import com.mitra.controller.impl.GetController;
import com.mitra.controller.impl.PostController;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(GetUrl.APP_PATH_PREFIX + "/*")
@MultipartConfig()
public class DispatcherServlet extends HttpServlet {

    private static final ControllerFactory controllerFactory = new ControllerFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = String.valueOf(request.getRequestURI());
        GetController controller = controllerFactory.findGetController(url);
        if (controller != null) {
            controller.processGet(request, response);
        } else {
            super.doGet(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = String.valueOf(request.getRequestURI());
        PostController controller = controllerFactory.findPostController(url);
        if (controller != null) {
            controller.processPost(request, response);
        } else {
            super.doPost(request, response);
        }
    }
}
