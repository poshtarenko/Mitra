package com.mitra.filter;


import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.util.SessionAttrAccessor;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(GetUrl.APP_PATH_PREFIX + "/*")
public class NonAuthorizedFilter implements Filter {

    private static final Set<String> enabledUrlsForNonAuthorized;

    static {
        enabledUrlsForNonAuthorized = new HashSet<>();
        enabledUrlsForNonAuthorized.add(GetUrl.LANDING_PAGE.getUrl());
        enabledUrlsForNonAuthorized.add(GetUrl.AUTHORIZATION.getUrl());
        enabledUrlsForNonAuthorized.add(GetUrl.REGISTRATION.getUrl());
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
                resp.sendRedirect(GetUrl.AUTHORIZATION.getUrl());
            } else if (profile == null && !req.getRequestURI().equals(GetUrl.CREATE_PROFILE.getUrl())) {
                resp.sendRedirect(GetUrl.CREATE_PROFILE.getUrl());
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
