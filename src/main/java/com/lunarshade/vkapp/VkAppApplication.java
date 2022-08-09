package com.lunarshade.vkapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class VkAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(VkAppApplication.class, args);
    }

}
