package com.mitra.filter;

import com.mitra.controller.UrlPath;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/*")
public class WrongUrlFilter implements Filter {

    private static final Set<String> enabledUrlStarts;

    static {
        enabledUrlStarts = new HashSet<>();
        enabledUrlStarts.add("/WEB-INF");
        enabledUrlStarts.add("/resources");
        enabledUrlStarts.add(UrlPath.SERVLET_CONST);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        for (String enabledUrlStart : enabledUrlStarts) {
            if (requestURI.startsWith(enabledUrlStart)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        ((HttpServletResponse) servletResponse).sendRedirect(UrlPath.LANDING_PAGE.getUrl());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
