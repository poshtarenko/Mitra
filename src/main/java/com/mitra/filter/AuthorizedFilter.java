package com.mitra.filter;

import com.mitra.controller.SessionAttributes;
import com.mitra.controller.AppUrl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(AppUrl.APP_PATH_PREFIX + "/*")
public class AuthorizedFilter implements Filter {

    private static final Set<String> notEnabledUrlsForAuthorized;

    static {
        notEnabledUrlsForAuthorized = new HashSet<>();
        notEnabledUrlsForAuthorized.add(AppUrl.AUTHORIZATION.getUrl());
        notEnabledUrlsForAuthorized.add(AppUrl.REGISTRATION.getUrl());
    }

    // if user is authorized and tries to move to auth/register page --> redirect him to his profile page
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Integer userId = (Integer) req.getSession().getAttribute(SessionAttributes.USER_ID.name());
        String userName = (String) req.getSession().getAttribute(SessionAttributes.USER_NAME.name());

        if (notEnabledUrlsForAuthorized.contains(req.getRequestURI()) && userId != null) {
            if (userName == null) {
                resp.sendRedirect(AppUrl.CREATE_PROFILE.getUrl());
            } else {
                resp.sendRedirect(AppUrl.MY_PROFILE.getUrl());
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
