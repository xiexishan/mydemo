package com.example.mytttptestpro.common;

import com.example.mytttptestpro.entity.JwtProperties;
import com.example.mytttptestpro.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 拦截器  校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NotNull Object handler) {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        //从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenPrefix());
        //校验令牌
        try {
            log.info("jwt校验:{}", token);
            Jws<Claims> claimsJws = JwtUtil.parseJWT(token, jwtProperties.getTokenPrefix());

            String account = claimsJws.getPayload().get("userAccount").toString();
            log.info("当前用户id:{}", account);
            BaseContext.setCurrentUser(account);
            //放行
            return true;
        } catch (Exception e) {
            //不通过401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

}