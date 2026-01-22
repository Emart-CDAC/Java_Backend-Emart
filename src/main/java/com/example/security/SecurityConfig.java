package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// 🔓 LOGIN ENDPOINTS
						.requestMatchers("/api/admin/login", "/api/users/login", "/api/users/register").permitAll()

						// 🔐 ROLE-BASED ACCESS
						.requestMatchers("/api/admin/**").hasRole("ADMIN").requestMatchers("/api/users/**")
						.hasRole("USER")

						// anything else
						.anyRequest().authenticated())
				// 🔑 JWT FILTER
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager();
	}
}
