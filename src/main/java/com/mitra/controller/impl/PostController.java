package com.mitra.controller.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PostController {

    void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
