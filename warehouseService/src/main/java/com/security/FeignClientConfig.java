package com.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Value("${internal.secret}")
    private String internalSecret;

    @Bean
    public RequestInterceptor internalAccessHeaderInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.header("X-Internal-Access", internalSecret);
            }
        };
    }
}
