package com.example.gdsc_2nd_w1.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String LOGIN_SESSION_KEY = "yr_approved";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(LOGIN_SESSION_KEY) == null) {
            response.sendRedirect("/login-form");   // 세션 없거나 사용자의 정보가 없으면 로그인 페이지로 보냄
            return false;
        }
        return true;
    }
}
