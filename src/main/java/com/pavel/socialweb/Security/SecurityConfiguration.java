package com.pavel.socialweb.Security;

import com.pavel.socialweb.Filter.JwtFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@SecurityScheme(
        name = "BearerJWT",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Bearer token",
        in = SecuritySchemeIn.HEADER
)
public class SecurityConfiguration {
    private final UserSecurity userSecurity;

    private final JwtFilter jwtFilter;

    public SecurityConfiguration(UserSecurity userSecurity, JwtFilter jwtFilter) {
        this.userSecurity = userSecurity;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> {
                    csrf.disable();
                })
                .userDetailsService(userSecurity)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(HttpMethod.POST, "/token").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/captcha").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/user").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                            .requestMatchers("/error").permitAll()
                            .requestMatchers("/token/**").permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
