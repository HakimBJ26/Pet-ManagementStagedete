package com.example.PetgoraBackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
   private OurUserDetailsService ourUserDetailsService;


    public JWTAuthFilter(JWTUtils jwtUtils, OurUserDetailsService ourUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.ourUserDetailsService = ourUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    String jwt = cookie.getValue();
                    String username = jwtUtils.extractUsername(jwt);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = ourUserDetailsService.loadUserByUsername(username);
                        if (jwtUtils.isTokenValid(jwt, userDetails)) {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }


//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        try {
//            String accessToken = getJwtFromRequest(request, "accessToken");
//            if (accessToken != null && jwtUtils.isTokenValid(accessToken)) {
//                String username = jwtUtils.extractUsername(accessToken);
//                UserDetails userDetails = ourUserDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else if (accessToken != null && !jwtUtils.isTokenValid(accessToken)) {
//                // Access token is expired or invalid
//                String refreshToken = getJwtFromRequest(request, "refreshToken");
//                if (refreshToken != null && jwtUtils.isTokenValid(refreshToken)) {
//                    // Refresh the access token
//                    String username = jwtUtils.extractUsername(refreshToken);
//                    UserDetails userDetails = ourUserDetailsService.loadUserByUsername(username);
//                    String newAccessToken = jwtUtils.generateToken((UserDetails) new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
//                    // Set new access token in HttpOnly cookie
//                    jwtUtils.setHttpOnlyCookie(response, "accessToken", newAccessToken, 60 * 15); // 15 minutes expiry
//                    // Continue with the new access token
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Cannot set user authentication: " + e);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//
//
//
//
//    private String getJwtFromRequest(HttpServletRequest request, String tokenName) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(tokenName)) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }


}
