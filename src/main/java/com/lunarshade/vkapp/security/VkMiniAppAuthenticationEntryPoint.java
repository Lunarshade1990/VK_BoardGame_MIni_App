package com.lunarshade.vkapp.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class VkMiniAppAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        if (authException instanceof AuthenticationCredentialsNotFoundException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, authException.getMessage());
        } else if (authException instanceof BadCredentialsException) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, authException.getMessage());
        } else if (authException instanceof UsernameNotFoundException) {
            response.sendError(HttpServletResponse.SC_ACCEPTED, authException.getMessage());
        }
    }
}
