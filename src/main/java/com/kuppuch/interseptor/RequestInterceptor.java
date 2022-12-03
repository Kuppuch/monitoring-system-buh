package com.kuppuch.interseptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    public static Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Objects.equals(request.getRequestURI(), "/login"))
            return true;

        boolean flag = true;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            flag = false;
        } else {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "auth")) {
                    String token = cookie.getValue();
                    DecodedJWT decodedJWT;
                    try {
                        Algorithm algorithm = Algorithm.HMAC256("123");
                        decodedJWT = JWT.require(algorithm)
                                .build().verify(token);

                        String subject = decodedJWT.getSubject();

                        log.info(subject);


                        Date exp = decodedJWT.getExpiresAt();
                        if (!exp.after(new Date())) {
                            flag = false;
                        }
                    } catch (JWTVerificationException exception) {
                        log.info(exception.toString());
                    }
                }
            }
        }

        if (!flag) {
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", "login");
            response.sendRedirect("login");
            return true;
        }
        return flag;
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
