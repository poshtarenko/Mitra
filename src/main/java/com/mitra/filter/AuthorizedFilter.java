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
public class AuthorizedFilter implements Filter {

    private static final Set<String> notEnabledUrlsForAuthorized;

    static {
        notEnabledUrlsForAuthorized = new HashSet<>();
        notEnabledUrlsForAuthorized.add(UrlPath.AUTHORIZATION.get());
        notEnabledUrlsForAuthorized.add(UrlPath.REGISTRATION.get());
    }

    // if user is authorized and tries to move to auth/register page --> redirect him to his profile page
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        UserDto user = (UserDto) req.getSession().getAttribute(SessionAttributes.USER.name());

        if (user != null && notEnabledUrlsForAuthorized.contains(req.getRequestURI())) {
            resp.sendRedirect(UrlPath.MY_PROFILE.get());
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
