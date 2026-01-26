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

            // Existing GOOGLE user: Login directly
            String token = jwtUtil.generateToken(email, "ROLE_USER", existingUser.getUserId());
            response.sendRedirect("http://localhost:5173/?token=" + token);
        } else {
            // New Google User: Create entry (now includes default profile/address) and
            // login directly
            com.example.model.Customer newUser = oauthUserService.saveCustomer(user);
            String token = jwtUtil.generateToken(email, "ROLE_USER", newUser.getUserId());
            response.sendRedirect("http://localhost:5173/?token=" + token);
        }
    }

}
