package com.community.api.common.ipauthentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class IpAuthenticationIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        System.out.println(ipAddress+"hihi");
        // 여기에서 데이터베이스에서 IP에 해당하는 사용자 정보를 가져와 인증을 처리
        // 예를 들어, userRepository.findByIpAddress(ipAddress)를 사용하여 사용자를 가져올 수 있음
        // 인증이 성공하면 true를 반환하고, 실패하면 false를 반환

        // 인증 로직을 간단히 하기 위해 임시로 true를 반환하도록 설정
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
