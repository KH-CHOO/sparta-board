package com.sparta.spartaboard.filters;

import com.sparta.spartaboard.domain.entity.User;
import com.sparta.spartaboard.repository.UserRepository;
import com.sparta.spartaboard.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@RequiredArgsConstructor
@Order(1)
public class AuthFilter implements Filter {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();

        if (StringUtils.hasText(url) &&
                (url.startsWith("/swagger-ui") || url.startsWith("/v3/api-docs") || url.startsWith("/api/user") || url.equals("/api/posts") || (url.startsWith("/api/post") && method.equals("GET"))))
        {
            chain.doFilter(request, response);
        } else {
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequestHeader(httpServletRequest);

            if (tokenValue != null) {
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                    //throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findById(info.getSubject()).orElseThrow(() ->
                        new NullPointerException("Not Found User")
                );

                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Not found token");
//                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }

}

/*
 To return a 401 Unauthorized HTTP status code from the AuthFilter in the given code snippet, you can modify it as follows:

import jakarta.servlet.http.HttpServletResponse;

// ...

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    // ... existing code ...

    if (tokenValue != null) {
        // ... existing code ...

        if (!jwtUtil.validateToken(token)) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        // ... existing code ...

        chain.doFilter(request, response);
    } else {
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token not found");
    }
}

In the updated code, I added checks for an invalid token inside the if (tokenValue != null) block. If the token is not valid, it will send a 401 Unauthorized response with the error message "Invalid token" using the sendError method of the HttpServletResponse.
If no token is found, it will send a 401 Unauthorized response with the error message "Token not found".
By returning the appropriate HTTP status code and error message, the client will receive a proper indication of the unauthorized access, allowing them to handle it accordingly.
 */