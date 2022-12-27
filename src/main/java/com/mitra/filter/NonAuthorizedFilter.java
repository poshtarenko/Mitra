package com.mitra.filter;


import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.util.SessionAttrAccessor;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(AppUrl.APP_PATH_PREFIX + "/*")
public class NonAuthorizedFilter implements Filter {

    private static final Set<String> enabledUrlsForNonAuthorized;

    static {
        enabledUrlsForNonAuthorized = new HashSet<>();
        enabledUrlsForNonAuthorized.add(AppUrl.LANDING_PAGE.getUrl());
        enabledUrlsForNonAuthorized.add(AppUrl.AUTHORIZATION.getUrl());
        enabledUrlsForNonAuthorized.add(AppUrl.REGISTRATION.getUrl());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        UserDto user = SessionAttrAccessor.getUser(req);
        ProfileDto profile = SessionAttrAccessor.getProfile(req);

        if (enabledUrlsForNonAuthorized.contains(req.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (user == null) {
                resp.sendRedirect(AppUrl.AUTHORIZATION.getUrl());
            } else if (profile == null && !req.getRequestURI().equals(AppUrl.CREATE_PROFILE.getUrl())) {
                resp.sendRedirect(AppUrl.CREATE_PROFILE.getUrl());
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
