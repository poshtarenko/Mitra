package com.mitra.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class DispatcherFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith("/WEB-INF/") || path.startsWith("/resources/"))
            filterChain.doFilter(servletRequest, servletResponse);
        else
            servletRequest.getRequestDispatcher("/app" + path)
                    .forward(servletRequest, servletResponse);
    }

    // Overriding with empty method body of init() and destroy()
    // methods, because Tomcat can't start up without it
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
