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
public class AuthorizedFilter implements Filter {

    private static final Set<String> notEnabledUrlsForAuthorized;

    static {
        notEnabledUrlsForAuthorized = new HashSet<>();
        notEnabledUrlsForAuthorized.add(GetUrl.AUTHORIZATION.getUrl());
        notEnabledUrlsForAuthorized.add(GetUrl.REGISTRATION.getUrl());
    }

    // if user is authorized and tries to move to auth/register page --> redirect him to his profile page
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        UserDto user = SessionAttrAccessor.getUser(req);
        ProfileDto profile = SessionAttrAccessor.getProfile(req);

        if (notEnabledUrlsForAuthorized.contains(req.getRequestURI()) && user != null) {
            if (profile == null) {
                resp.sendRedirect(GetUrl.CREATE_PROFILE.getUrl());
            } else {
                resp.sendRedirect(GetUrl.MY_PROFILE.getUrl());
            }
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
