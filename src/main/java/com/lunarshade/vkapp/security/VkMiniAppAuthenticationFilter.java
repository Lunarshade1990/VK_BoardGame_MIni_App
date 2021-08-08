package com.lunarshade.vkapp.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VkMiniAppAuthenticationFilter extends OncePerRequestFilter {

    private final VkMiniappUserDetailService userDetailService;
    final SignChecker signChecker;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public VkMiniAppAuthenticationFilter(VkMiniappUserDetailService userDetailService,
                                         SignChecker signChecker,
                                         AuthenticationEntryPoint authenticationEntryPoint) {
        this.userDetailService = userDetailService;
        this.signChecker = signChecker;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    private final List<String> excludePatches = new ArrayList<>();
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {


        excludePatches.add("/auth");

        if(excludePatches.stream().anyMatch(p -> antPathMatcher.match(p, request.getServletPath()))) {
            chain.doFilter(request, response);
            return;
        }


        String sign = request.getHeader("authorization");
        if (sign == null || sign.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        try {
            if (signChecker.check(request)) {
                String vkId = request.getHeader("vk_user_id");
                if (vkId == null || vkId.isEmpty()) {
                    throw new AuthenticationCredentialsNotFoundException("Не указан VK ID");
                }
                UserDetails userDetails = userDetailService.loadUserByUsername(vkId);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                chain.doFilter(request, response);
            }else throw new BadCredentialsException("Токен не прошёл валидацию");
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            this.authenticationEntryPoint.commence(request, response, e);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }


}
