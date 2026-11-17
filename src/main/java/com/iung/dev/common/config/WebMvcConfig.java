package com.iung.dev.common.config;


import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.interceptor.TokenInterceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;
    private final String[] PUBLIC_URL;
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/static/",
            "classpath:/",
            "classpath:/resources/"
    };

    public WebMvcConfig(TokenInterceptor interceptor) {
        this.tokenInterceptor = interceptor;
        PUBLIC_URL =
                Stream.concat(
                        Arrays.stream(SecurityConfig.THIRD_PARTY_PERMIT_URL_ARRAY),
                        Arrays.stream((SecurityConfig.WHITELIST_PERMIT_URL_ARRAY))).toArray(String[]::new);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //
        registry.addInterceptor(this.tokenInterceptor)
                .excludePathPatterns(PUBLIC_URL)
                .addPathPatterns("/**"); // 제외 경로 이외의 모든 URL에 대해 JWT 토큰 검사를 적용
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico");
        registry.addResourceHandler("/**").addResourceLocations(String.join(",", CLASSPATH_RESOURCE_LOCATIONS));
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // org.springframework.boot.restclient.RestTemplateBuilder
        return builder.connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(10)).build();
    }
}
