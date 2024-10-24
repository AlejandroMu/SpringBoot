package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecureFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            try {
                
                String auth = request.getHeader("Authorization");
                if (auth != null && !auth.equals("Bearer null")) {
                    
                    String token = auth.substring(7);
                    if(!jwtTokenProvider.isTokenExpired(token)) {
                        UserDetails userDetails = jwtTokenProvider.getUserDetails(token);
                        UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, "token", userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                    
                }
            } catch (Exception e) {
                logger.error(
                    "No se pudo establecer la autenticación del usuario en el contexto de seguridad", e
                    );
                }
            filterChain.doFilter(request, response);

    }
    
}
