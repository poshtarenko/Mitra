package com.mitra.filter;


import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(UrlPath.APP_PATH_PREFIX + "/*")
public class NonAuthorizedFilter implements Filter {

    private static final Set<String> enabledUrlsForNonAuthorized;

    static {
        enabledUrlsForNonAuthorized = new HashSet<>();
        enabledUrlsForNonAuthorized.add(UrlPath.LANDING_PAGE.getUrl());
        enabledUrlsForNonAuthorized.add(UrlPath.AUTHORIZATION.getUrl());
        enabledUrlsForNonAuthorized.add(UrlPath.REGISTRATION.getUrl());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Integer userId = (Integer) req.getSession().getAttribute(SessionAttributes.USER_ID.name());
        String userName = (String) req.getSession().getAttribute(SessionAttributes.USER_NAME.name());

        if (enabledUrlsForNonAuthorized.contains(req.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (userId == null) {
                resp.sendRedirect(UrlPath.AUTHORIZATION.getUrl());
            }
            else if (userName == null && !req.getRequestURI().equals(UrlPath.CREATE_PROFILE.getUrl())) {
                resp.sendRedirect(UrlPath.CREATE_PROFILE.getUrl());
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
