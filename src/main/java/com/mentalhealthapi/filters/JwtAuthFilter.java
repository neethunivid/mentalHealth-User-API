package com.mentalhealthapi.filters;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.mentalhealthapi.configuration.UserInfoUserDetailsService;
import com.mentalhealthapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(token);
            } catch (ExpiredJwtException e) {
                // Handle expired token exception here
            	 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            	    response.setContentType("application/json");
            	    response.getWriter().write("{\"code\": 401, \"error\": \"JWT token has expired or UNAUTHORIZED\"}");
            	    return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            try {
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    throw new ExpiredJwtException(null, null, "JWT token has expired");
                }
            } catch (ExpiredJwtException e) {
                // Handle expired token exception here
            	 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         	    response.setContentType("application/json");
         	    response.getWriter().write("{\"code\": 401, \"error\": \"JWT token has expired or UNAUTHORIZED\"}");
         	    return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
