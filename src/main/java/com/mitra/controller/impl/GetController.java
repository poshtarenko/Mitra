package com.mitra.controller.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface GetController {

    void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
