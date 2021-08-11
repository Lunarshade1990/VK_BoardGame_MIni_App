package com.lunarshade.vkapp.config;

import com.lunarshade.vkapp.security.VkMiniAppAuthenticationEntryPoint;
import com.lunarshade.vkapp.security.VkMiniAppAuthenticationFilter;
import com.lunarshade.vkapp.security.VkMiniappUserDetailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;


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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

        @Override
    protected void configure(HttpSecurity http) throws Exception {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);


        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(vkMiniAppAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CorsFilter(source), VkMiniAppAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and().sessionManagement().disable();

    }
}
