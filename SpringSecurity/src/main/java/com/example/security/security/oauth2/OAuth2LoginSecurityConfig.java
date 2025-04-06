package com.example.security.security.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration for OAuth2 login.
 * This configuration is only active when the "oauth2" profile is active.
 */
@Configuration
@EnableWebSecurity
@Profile("oauth2")
public class OAuth2LoginSecurityConfig {

    /**
     * Configure security filter chain for OAuth2 login.
     *
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .antMatchers("/", "/login", "/oauth2/**").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login ->
                oauth2Login
                    .loginPage("/login")
                    .defaultSuccessUrl("/loginSuccess")
                    .failureUrl("/loginFailure")
            );
        
        return http.build();
    }
}
