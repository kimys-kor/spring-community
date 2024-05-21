package com.community.api.common.ipauthentication;

import com.community.api.common.jwt.JwtTokenProvider;
import com.community.api.common.properties.JwtProperties;
import com.community.api.model.User;
import com.community.api.model.base.UserRole;
import com.community.api.service.IpService;
import com.community.api.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class GuestPathIntercepter implements HandlerInterceptor {

    @Resource
    private IpService ipService;
    @Resource
    private UserService userService;
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //FIXME 로그인이 하나로 통합되었기 때문에 role에 따라서 blocked, approved 수정해주어야함
        String Authorization = request.getHeader(jwtProperties.headerString());
        String token = Authorization.replace("Bearer ", "");
        String username = jwtTokenProvider.resolveToken(token);
        User user = userService.findByUsername(username);

        if (user.getRole().equals(UserRole.ROLE_USER)) {
            String ipAddress = request.getRemoteAddr();
            return ipService.findIp("blocked", ipAddress);
        } else {
            String ipAddress = request.getRemoteAddr();
            return ipService.findIp("approved", ipAddress);
        }


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
