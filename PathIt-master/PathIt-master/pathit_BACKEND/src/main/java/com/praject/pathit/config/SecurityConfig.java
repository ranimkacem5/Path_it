package com.praject.pathit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable()) // Désactiver CSRF pour le développement
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/pathit/register", "/error").permitAll() // Routes publiques
                .anyRequest().authenticated() // Toutes les autres routes nécessitent une authentification
            )
            .formLogin(form -> form.disable()) // Désactiver les formulaires de connexion
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
