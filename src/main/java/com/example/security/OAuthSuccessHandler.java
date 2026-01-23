package com.example.security;

import java.io.IOException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.model.Customer;
import com.example.services.OAuthUserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final OAuthUserService oauthUserService;
    private final JwtUtil jwtUtil;

    public OAuthSuccessHandler(OAuthUserService oauthUserService, JwtUtil jwtUtil) {
        this.oauthUserService = oauthUserService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        Customer existingUser = oauthUserService.findByEmail(email);

        if (existingUser != null) {
            if (existingUser.getAuthProvider() == com.example.model.AuthProvider.LOCAL) {
                // Provider mismatch: Email exists as LOCAL
                response.sendRedirect("http://localhost:5173/login?error=" +
                        URLEncoder.encode("This email is registered with a password. Please login normally.",
                                StandardCharsets.UTF_8));
                return;
            }

            if (!existingUser.isProfileCompleted()) {
                // Return to registration to complete profile
                response.sendRedirect("http://localhost:5173/register" +
                        "?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) +
                        "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) +
                        "&userId=" + existingUser.getUserId());
                return;
            }

            // Existing GOOGLE user with complete profile
            String token = jwtUtil.generateToken(email, "ROLE_USER");
            response.sendRedirect("http://localhost:5173/?token=" + token);
        } else {
            // New User: Create basic entry and redirect to complete registration
            Customer newUser = oauthUserService.saveCustomer(user);
            response.sendRedirect(
                    "http://localhost:5173/register" +
                            "?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) +
                            "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8) +
                            "&userId=" + newUser.getUserId());

        }
    }

}
