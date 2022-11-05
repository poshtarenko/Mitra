package com.mitra.filter;


import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.UserDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(UrlPath.SERVLET_CONST + "/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> enabledUrlsForNonAuthorized;

    static {
        enabledUrlsForNonAuthorized = new HashSet<>();
        enabledUrlsForNonAuthorized.add(UrlPath.LANDING_PAGE.get());
        enabledUrlsForNonAuthorized.add(UrlPath.AUTHORIZATION.get());
        enabledUrlsForNonAuthorized.add(UrlPath.REGISTRATION.get());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        UserDto user = (UserDto) req.getSession().getAttribute(SessionAttributes.USER.name());

        if (user == null && !enabledUrlsForNonAuthorized.contains(req.getRequestURI())) {
            resp.sendRedirect(UrlPath.AUTHORIZATION.get());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
