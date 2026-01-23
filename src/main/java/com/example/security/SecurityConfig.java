package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

        @Autowired
        private OAuthSuccessHandler oauthSuccessHandler;

        @Autowired
        private JwtAuthFilter jwtAuthFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .requestMatchers(
                                                                "/api/users/register",
                                                                "/api/users/login",
                                                                "/api/users/google-login",
                                                                "/api/users/complete-registration/**",
                                                                "/oauth2/**",
                                                                "/login/**")
                                                .permitAll()
                                                // ✅ Admin APIs
                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                                                // ✅ Customer APIs
                                                .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form.disable())
                                .httpBasic(basic -> basic.disable())
                                .exceptionHandling(exception -> exception
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        if (request.getRequestURI().startsWith("/api/")) {
                                                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                                                                "Unauthorized");
                                                        } else {
                                                                response.sendRedirect("/oauth2/authorization/google");
                                                        }
                                                }))
                                .oauth2Login(oauth2 -> oauth2
                                                .successHandler(oauthSuccessHandler))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:5173"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control", "Accept"));
                
                configuration.setExposedHeaders(List.of("Authorization")); // ✅ important

                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
