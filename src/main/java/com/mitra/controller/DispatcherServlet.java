package com.mitra.controller;

import com.mitra.controller.request_processor.RequestProcessor;
import com.mitra.controller.request_processor.RequestProcessorFactory;
import com.mitra.exception.PageDontExistException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(AppUrl.APP_PATH_PREFIX + "/*")
@MultipartConfig()
public class DispatcherServlet extends HttpServlet {

    private static final RequestProcessorFactory requestProcessorFactory = new RequestProcessorFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getProcessor(String.valueOf(request.getRequestURI()))
                .processGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getProcessor(String.valueOf(request.getRequestURI()))
                .processPost(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getProcessor(String.valueOf(request.getRequestURI()))
                .processDelete(request, response);
    }

    public RequestProcessor getProcessor(String requestUrl) {
        Optional<AppUrl> urlPath = AppUrl.getByPath(requestUrl);

        if (!urlPath.isPresent())
            throw new PageDontExistException("Path " + requestUrl + " is not represented");

        return requestProcessorFactory.getProcessor(urlPath.get());
    }

}
