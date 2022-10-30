package com.mitra.controller;

import com.mitra.controller.request_processor.RequestProcessor;
import com.mitra.controller.request_processor.RequestProcessorFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

// All requests on default servlets marked with prefix "/app"
// Another servlets (jsp, image processors) do not have any prefix
@WebServlet(UrlPath.SERVLET_CONST + "/*")
public class DispatcherServlet extends HttpPatchServlet {

    private static final RequestProcessorFactory requestProcessorFactory = new RequestProcessorFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getProcessor(String.valueOf(request.getRequestURI()))
                .processGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getProcessor(String.valueOf(request.getRequestURL()))
                .processPost(request, response);
    }

    @Override
    public void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getProcessor(String.valueOf(request.getRequestURL()))
                .processPatch(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getProcessor(String.valueOf(request.getRequestURL()))
                .processDelete(request, response);
    }

    public RequestProcessor getProcessor(String requestUrl) {
        UrlPath urlPath = UrlPath.getByPath(requestUrl);
        return requestProcessorFactory.getProcessor(urlPath);
    }

}
