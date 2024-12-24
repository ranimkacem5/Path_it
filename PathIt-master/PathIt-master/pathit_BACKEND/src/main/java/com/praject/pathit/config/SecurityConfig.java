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
            .requestMatchers("/api/pathit/register", "/error", "/api/admin/formation/add", "/api/admin/formation/update/{id}").permitAll() // Toutes les routes publiques
            .anyRequest().permitAll() // Permettre l'accès à toutes les autres routes sans authentification
        )
        .formLogin(form -> form.disable()) // Désactiver les formulaires de connexion
        .build();
}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
