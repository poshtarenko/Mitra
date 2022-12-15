package com.mitra.controller.request_processor.impl;

import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.AbstractRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LandingProcessor extends AbstractRequestProcessor {

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, UrlPath.LANDING_PAGE.getJspFileName());
    }
}
