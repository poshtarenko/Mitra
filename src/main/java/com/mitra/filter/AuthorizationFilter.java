package com.mitra.filter;


import com.mitra.controller.SessionAttributes;
import com.mitra.controller.UrlPath;
import com.mitra.dto.UserDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(UrlPath.SERVLET_CONST + "/*")
public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        UserDto user = (UserDto) req.getSession().getAttribute(SessionAttributes.USER.name());
        if (user == null
                && !String.valueOf(req.getRequestURI()).equals(UrlPath.AUTHORIZATION.get())
                && !String.valueOf(req.getRequestURI()).equals(UrlPath.REGISTRATION.get())){
            resp.sendRedirect(UrlPath.AUTHORIZATION.get());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    // Overriding with empty method body of init() and destroy()
    // methods, because Tomcat can't start up without this :(
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void destroy() {
//    }
}
