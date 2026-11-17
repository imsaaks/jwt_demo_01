package com.iung.dev.common.config;

import com.iung.dev.common.constants.AppConstants;
import com.iung.dev.common.security.entry.AuthEntryPoint;
import com.iung.dev.common.security.handler.AuthDeniedHandler;

import com.iung.dev.common.security.filter.AuthenticationFilter;
import com.iung.dev.common.security.filter.AuthorizationFilter;
import com.iung.dev.common.security.provider.AuthProvider;

import com.iung.dev.common.security.handler.AuthFailureHandler;
import com.iung.dev.common.security.handler.AuthSuccessHandler;
import com.iung.dev.common.security.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.Security;
import java.util.*;


@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String[] THIRD_PARTY_PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/favicon.ico",
            /* Chrome 개발자 도구 */
            "/.well-known/**"
    };

    public static final String[] WHITELIST_PERMIT_URL_ARRAY = {
            /* mvc */
            AppConstants.SIGN_IN_PATH,
            AppConstants.SIGN_UP_PATH,
            AppConstants.SIGN_OUT_PATH,
            AppConstants.CHECK_ID_DUPLICATE_PATH,
            AppConstants.TOKEN_CHECK_PATH
    };


    public static List<String> PUBLIC_PERMIT_URL_ARRAY;
    private final AuthEntryPoint authEntryPoint;
    private final AuthDeniedHandler authDeniedHandler;

    public SecurityConfig(AuthEntryPoint entryPoint, AuthDeniedHandler deniedHandler) {
        this.authEntryPoint = entryPoint;
        this.authDeniedHandler = deniedHandler;
        this.PUBLIC_PERMIT_URL_ARRAY = new ArrayList<>(Arrays.asList(THIRD_PARTY_PERMIT_URL_ARRAY));
        this.PUBLIC_PERMIT_URL_ARRAY.addAll(Arrays.asList(WHITELIST_PERMIT_URL_ARRAY));
        log.info("** PUBLIC_PERMIT_URL_ARRAY: {}", PUBLIC_PERMIT_URL_ARRAY);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthenticationFilter authenticationFilter,
            AuthorizationFilter authorizationFilter) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(request ->
                        request.requestMatchers(THIRD_PARTY_PERMIT_URL_ARRAY).permitAll()
                                .requestMatchers(WHITELIST_PERMIT_URL_ARRAY).permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(authorizationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ae -> ae.authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(authDeniedHandler))
                .build();
    }

    @Bean
    public AuthorizationFilter authorizationFilter(AuthUserService authUserService) {
        return new AuthorizationFilter(authUserService);
    }

    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager,
                                AuthSuccessHandler authSuccessHandler,
                                AuthFailureHandler authFailureHandler
                                ) {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl(AppConstants.SIGN_IN_PATH);
        authenticationFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authFailureHandler);
        authenticationFilter.afterPropertiesSet();
        return authenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthProvider authProvider){
        return new ProviderManager(List.of(authProvider));
    }
    @Bean
    public AuthProvider customAuthenticationProvider(AuthUserService authUserService) {
        return new AuthProvider(
                authUserService
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOriginPatterns(List.of(
//                "http://localhost:*",
//                "http://210.109.14.111:*"
//                )
//        );
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
