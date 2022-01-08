package com.modeling.demo.configs;

import com.modeling.demo.service.CSRFTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class CSRFHandlerInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CSRFTokenManager csrfTokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!request.getMethod().equalsIgnoreCase("POST") ) {
            // Not a POST - allow the request
            return true;
        } else {
            // This is a POST request - need to check the CSRF token
            String sessionToken = CSRFTokenManager.getTokenForSession(request.getSession());
            String requestToken = CSRFTokenManager.getTokenFromRequest(request);
            if (sessionToken.equals(requestToken)) {
                return true;
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bad or missing CSRF value");
                return false;
            }
        }
    }


}