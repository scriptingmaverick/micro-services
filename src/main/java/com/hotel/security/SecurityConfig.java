package com.hotel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final JWTFilter jwtFilter;

  public SecurityConfig(JWTFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.requestMatchers(
                            "/api/users/**",
                            "/api/hotel/**",
                            "/api/search/**"
                    ).permitAll()
                    .anyRequest().authenticated())
            .addFilterBefore(jwtFilter, SecurityContextHolderFilter.class);

    return http.build();
  }
}
