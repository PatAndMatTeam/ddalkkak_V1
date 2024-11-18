//package com.ddalkkak.splitting.user.service;
//
//import com.ddalkkak.splitting.common.exception.ErrorCode;
//import com.ddalkkak.splitting.user.exception.JwtErrorCode;
//import com.ddalkkak.splitting.user.exception.JwtException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//public class JwtExceptionFilter implements  AuthenticationEntryPoint {
//
//    private static void setErrorResponse(HttpServletResponse response)  {
//        JwtErrorCode errorCode = JwtErrorCode.ILLEGAL_ACCESS_TOKEN;
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(errorCode.getStatus().value());
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map map = new HashMap();
//        map.put("message", errorCode.getMessage());
//        map.put("code", errorCode.getCode());
//
//        try {
//            String responseBody = objectMapper.writeValueAsString(map);
//            response.getWriter().write(responseBody);
//            response.getWriter().flush(); // 버퍼를 클라이언트에 즉시 전달
//        } catch (Exception e) {
//            throw new RuntimeException("JSON 처리 중 오류 발생", e);
//        }
//    }
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        Object exception = request.getAttribute("exception");
//        setErrorResponse(response);
//    }
//}
