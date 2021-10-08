package com.lunarshade.vkapp.config;

import com.lunarshade.vkapp.dao.request.BoardGameFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BoardGameFilter.StringToEnumConverter());
    }
}