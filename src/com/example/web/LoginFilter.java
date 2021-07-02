package com.example.web;
import java.io.IOException;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 
@WebFilter("/*")
public class LoginFilter implements Filter {
    private HttpServletRequest httpRequest;
 
    private static final String[] notLoginRequiredURLs = {
            "/index.jsp","/signup.jsp","/signup.html"
    };
 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        httpRequest = (HttpServletRequest) request;
 
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
 
        if (path.startsWith("/Mail/")) {
            chain.doFilter(request, response);
            return;
        }
 
        HttpSession session = httpRequest.getSession(false);
 
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
 
        String loginURI = httpRequest.getContextPath() + "/index.jsp";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("/index.jsp");
 
        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            httpRequest.getRequestDispatcher("/inbox.jsp").forward(request, response);
 
        } else if (!isLoggedIn && isLoginRequired()) {
            String loginPage = "/index.jsp";
            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
            dispatcher.forward(request, response);
        } else {
            // for other requested pages that do not require authentication
            // or the user is already logged in, continue to the destination
            chain.doFilter(request, response);
        }
    }
 
 
    private boolean isLoginRequired() {
        String requestURL = httpRequest.getRequestURL().toString();
 
        for (String notLoginRequiredURL : notLoginRequiredURLs) {
            if (requestURL.contains(notLoginRequiredURL)) {
                return false;
            }
        }
 
        return true;
    }
 
 
    public void destroy() {
    }
 
    public void init(FilterConfig fConfig) throws ServletException {
    }
 
}