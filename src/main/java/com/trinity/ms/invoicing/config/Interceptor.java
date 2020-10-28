package com.trinity.ms.invoicing.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Interceptor {

    @Value("${security.authorization.key}")
    private String authorization;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            if(template.path().contains("/oauth")){
                template.header("Authorization", authorization);
            }
        };
    }
}
