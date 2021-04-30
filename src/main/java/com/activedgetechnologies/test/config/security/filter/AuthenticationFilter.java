package com.activedgetechnologies.test.config.security.filter;


import com.activedgetechnologies.test.config.security.JwtTokenHandler;
import com.activedgetechnologies.test.config.security.UserAuthentication;
import com.activedgetechnologies.test.model.user.UserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {


    private final JwtTokenHandler tokenHandler;

    private final String allowedOrigins;
    private final String allowedMethods;
    private final String allowedHeaders;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AuthenticationFilter(
            JwtTokenHandler tokenHandler, String allowedOrigins,
            String allowedMethods, String allowedHeaders) {

        this.tokenHandler = tokenHandler;
        this.allowedOrigins = allowedOrigins;
        this.allowedMethods = allowedMethods;
        this.allowedHeaders = allowedHeaders;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Authentication authentication = this.getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String method = request.getMethod();
        if (StringUtils.hasText(allowedOrigins)) {
            response.setHeader("Access-Control-Allow-Origin", allowedOrigins);
        }

        if (StringUtils.hasText(allowedMethods)) {
            response.setHeader("Access-Control-Allow-Methods", allowedMethods
            );
        }

        response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader(
                "Access-Control-Allow-Headers",
                allowedHeaders);
        if ("OPTIONS".equals(method)) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }


    }

    private Authentication getAuthentication(HttpServletRequest request) {
        Authentication auth = null;
        String accessToken = tokenHandler.resolveToken(request);
        try {
            if (accessToken != null && !accessToken.isEmpty() && tokenHandler.validateToken(accessToken)) {

                UserResult userResult = tokenHandler.getAuthentication(accessToken);
                if (userResult != null) {
                    auth = new UserAuthentication(userResult);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage(), e);
            auth = null;
        }

        return auth;
    }
}
