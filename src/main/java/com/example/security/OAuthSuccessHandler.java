package com.example.security;

import java.io.IOException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.model.Customer;
import com.example.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public OAuthSuccessHandler(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        try {
            Customer customer = userService.processGoogleLogin(email, name);

            if (customer.isProfileCompleted()) {
                // Login directly
                String token = jwtUtil.generateToken(email, "ROLE_USER", customer.getUserId());
                response.sendRedirect("http://localhost:5173/login?token=" + token);
            } else {
                // Redirect to Register to complete profile
                // Pass isOAuth=true so frontend knows to enable "Complete Profile" mode
                String targetUrl = "http://localhost:5173/register?email=" +
                        URLEncoder.encode(email, StandardCharsets.UTF_8) +
                        "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) +
                        "&isOAuth=true";
                response.sendRedirect(targetUrl);
            }

        } catch (RuntimeException e) {
            // Error (e.g. Registered as Local User)
            response.sendRedirect("http://localhost:5173/login?error=" +
                    URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }

}
