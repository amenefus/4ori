package com.weatherapp.tmw_home_test_ori_blanka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WeatherServiceConfig {
    @Value("${weather.data.base-url}")
    private String baseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(baseUrl)
                .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(10 * 1024 * 1024))
                .build();

    }
}
