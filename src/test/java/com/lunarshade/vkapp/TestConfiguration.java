package com.lunarshade.vkapp;

import org.anystub.http.StubHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class TestConfiguration {

    @Bean
    public RestTemplateBuilder builder() {

        RestTemplateCustomizer restTemplateCustomizer = restTemplate -> {
            HttpClient real = HttpClientBuilder.create().build();
            StubHttpClient stubHttpClient = new StubHttpClient(real);
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(stubHttpClient);
            restTemplate.setRequestFactory(requestFactory);
        };

        return new RestTemplateBuilder(restTemplateCustomizer);
    }
}
