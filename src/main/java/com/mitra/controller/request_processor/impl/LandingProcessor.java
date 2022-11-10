package com.mitra.controller.request_processor.impl;

import com.mitra.controller.UrlPath;
import com.mitra.util.GoogleDriveProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class LandingProcessor extends AbstractRequestProcessor {

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            GoogleDriveProvider.saveImgTest(request.getServletContext().getRealPath(""));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        forward(request, response, UrlPath.LANDING_PAGE.getJspFileName());
    }
}
