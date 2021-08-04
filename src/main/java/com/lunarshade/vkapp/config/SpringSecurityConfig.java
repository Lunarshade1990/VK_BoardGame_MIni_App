package com.lunarshade.vkapp.config;

import com.lunarshade.vkapp.security.VkMiniAppAuthenticationEntryPoint;
import com.lunarshade.vkapp.security.VkMiniAppAuthenticationFilter;
import com.lunarshade.vkapp.security.VkMiniappUserDetailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    final VkMiniappUserDetailService userDetailService;
    final VkMiniAppAuthenticationFilter vkMiniAppAuthenticationFilter;
    final VkMiniAppAuthenticationEntryPoint entryPoint;

    public SpringSecurityConfig(VkMiniappUserDetailService userDetailService, VkMiniAppAuthenticationFilter vkMiniAppAuthenticationFilter, VkMiniAppAuthenticationEntryPoint entryPoint) {
        this.userDetailService = userDetailService;
        this.vkMiniAppAuthenticationFilter = vkMiniAppAuthenticationFilter;
        this.entryPoint = entryPoint;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .addFilterBefore(vkMiniAppAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint);
    }
}
