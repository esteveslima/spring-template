package com.template.spring.demo.external.infrastructure.configuration.auth;

import com.template.spring.demo.external.infrastructure.filters.auth.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class AuthConfig {

    @Autowired AuthFilter authFilter;
    @Value("${ALLOWED_DOMAINS}") private String allowedDomainsString;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // TODO: not differentiating authentication and authorization, everything resolves to the same exception
        // Set the auth filter
        http.addFilterBefore(this.authFilter, UsernamePasswordAuthenticationFilter.class);

        // Basic configurations
        http.cors().configurationSource(this.corsConfigurationSource());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Disable some native features
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable();

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        List<String> allowedDomains = Arrays.stream(allowedDomainsString.split(",")).toList();
        List<String> allowedMethods = List.of("*");
        List<String> allowedHeaders = List.of("*");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedDomains);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}