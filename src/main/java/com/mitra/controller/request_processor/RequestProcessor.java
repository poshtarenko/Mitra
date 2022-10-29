package com.mitra.controller.request_processor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface RequestProcessor {

    void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void processPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void processDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
