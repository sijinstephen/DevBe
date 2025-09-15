// src/main/java/.../SecurityConfig.java
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // if you use cookies/session, keep CSRF and configure tokens; for APIs it’s common to disable
      .csrf(csrf -> csrf.disable())
      // IMPORTANT: wire CORS into the Security chain
      .cors(Customizer.withDefaults())
      // Allow preflight; tighten other matchers as needed
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .anyRequest().permitAll()
      );
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();

    // DEV: explicitly list your frontend(s)
    // Use setAllowedOriginPatterns if you need wildcards, e.g. "http://localhost:*"
    cfg.setAllowedOrigins(List.of("http://localhost:5173"));

    // Methods you support
    cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));

    // Headers the browser may send (include your custom ones)
    cfg.setAllowedHeaders(List.of(
      "Content-Type","Authorization","X-Requested-With","X-Request-ID"
    ));

    // Headers the browser may READ from responses (custom correlation id, etc.)
    cfg.setExposedHeaders(List.of("X-Request-ID"));

    // If you plan to use cookies: set true here AND use a specific origin (not "*")
    cfg.setAllowCredentials(false);

    // Cache the preflight for 1 hour
    cfg.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }
}
