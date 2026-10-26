package com.uniti.cil.itta.inventory.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                    @Value("${inventory.security.enforce-roles:false}") boolean enforce) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/actuator/health/**", "/actuator/info", "/v3/api-docs/**",
                            "/swagger-ui/**", "/swagger-ui.html").permitAll();
                    if (enforce) {
                        auth.requestMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("SCOPE_inventory.read", "SCOPE_inventory.write");
                        auth.requestMatchers("/api/**").hasAuthority("SCOPE_inventory.write");
                    }
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
