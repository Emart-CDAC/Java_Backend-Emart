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

import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

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

                                                // ✅ Allow preflight
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                                // ✅ Public APIs
                                                .requestMatchers(
                                                                "/api/users/register",
                                                                "/api/users/login",
                                                                "/api/users/google-login",
                                                                "/api/users/complete-registration/**",
                                                                "/api/admin/login",
                                                                "/oauth2/**",
                                                                "/login/**")
                                                .permitAll()

                                                // ✅ Admin APIs
                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                                                // ✅ Customer APIs
                                                .requestMatchers("/api/customer/**").hasRole("USER")

                                                // ✅ Everything else secured
                                                .anyRequest().authenticated())
                                .formLogin(form -> form.disable())
                                .httpBasic(basic -> basic.disable())
                                .exceptionHandling(exception -> exception
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        String uri = request.getRequestURI();
                                                        String accept = request.getHeader("Accept");

                                                        if (uri.startsWith("/api/")
                                                                        || (accept != null && accept.contains(
                                                                                        "application/json"))) {
                                                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                                                                "Unauthorized");
                                                        } else {
                                                                response.sendRedirect("/oauth2/authorization/google");
                                                        }
                                                }))
                                .oauth2Login(oauth2 -> oauth2.successHandler(oauthSuccessHandler))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:5173"));
                configuration.setAllowedMethods(
                                Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(
                                Arrays.asList("Authorization", "Content-Type", "Cache-Control", "Accept"));
                configuration.setExposedHeaders(List.of("Authorization"));
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
