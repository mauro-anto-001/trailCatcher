package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
    throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res); //filter the request and response
            return;
        }
    String token = header.substring(7);
    try {
        Jws<Claims> jws = JwtUtil.validate(token);
        String subject = jws.getBody().getSubject(); //set email as subject

        var auth = new UsernamePasswordAuthenticationToken(
                subject, // subject = email
                null,
                List.of(() -> "ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(auth);
    } catch(Exception e) {
        SecurityContextHolder.clearContext();
    }
    chain.doFilter(req, res);
    }

}
