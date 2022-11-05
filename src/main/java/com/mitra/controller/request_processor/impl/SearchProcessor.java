package com.mitra.controller.request_processor.impl;

import com.mitra.controller.UrlPath;
import com.mitra.service.ProfileService;
import com.mitra.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchProcessor extends AbstractRequestProcessor {

    private static final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static final ProfileService profileService = serviceFactory.getProfileService();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("profiles", profileService.findAll());
        forward(request, response, UrlPath.SEARCH.getJspFileName());
    }
}
