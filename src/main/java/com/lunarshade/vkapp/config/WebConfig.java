package com.lunarshade.vkapp.config;

import com.lunarshade.vkapp.dto.request.BoardGameFilter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BoardGameFilter.StringToEnumConverter());
    }
}
