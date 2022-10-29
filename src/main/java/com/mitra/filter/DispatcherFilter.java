package com.mitra.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public class DispatcherFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith("/WEB-INF/") || path.startsWith("/resources/"))
            chain.doFilter(request, response);
        else
            request.getRequestDispatcher("/app" + path)
                    .forward(request, response);
    }
}
