package com.round2.round2.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    /**
     * 인가용 , 나중에 추가
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
////        throw new RuntimeException();
//        response.sendRedirect("/exception/access-denied");
////        response.setStatus(SC_FORBIDDEN);

    }
}

