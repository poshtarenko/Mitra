package com.mitra.controller.request_processor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RequestProcessor {

    void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
