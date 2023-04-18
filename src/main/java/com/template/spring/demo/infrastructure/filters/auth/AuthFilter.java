package com.template.spring.demo.infrastructure.filters.auth;

import com.template.spring.demo.application.exceptions.auth.UnauthorizedException;
import com.template.spring.demo.application.exceptions.token.InvalidTokenException;
import com.template.spring.demo.application.interfaces.ports.token.TokenGateway;
import com.template.spring.demo.application.interfaces.ports.token.TokenPayloadDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component // Follows the Spring security filters order(?)
public class AuthFilter extends OncePerRequestFilter {

    @Autowired private TokenGateway tokenGateway;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Validate token and get its payload
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final boolean hasAuthorizationHeader = authorizationHeader != null;
        if(!hasAuthorizationHeader){
            filterChain.doFilter(request, response);
            return;
        }

        final String headerPrefix = "Bearer ";
        final boolean isValidAuthorizationPrefix = authorizationHeader.startsWith(headerPrefix);
        if(!isValidAuthorizationPrefix){
            filterChain.doFilter(request, response);
            return;
        }

        TokenPayloadDTO decodedTokenPayloadDTO;
        try{
            final String jwtToken = authorizationHeader.split(" ")[1].trim();
            decodedTokenPayloadDTO = this.tokenGateway.decodeToken(jwtToken);
        } catch(InvalidTokenException exception) {
            filterChain.doFilter(request, response);
            return;
        }

        // Set user for spring security context and authentication method
        String principal = decodedTokenPayloadDTO.username;
        String credentials = null;
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(decodedTokenPayloadDTO.role.name()));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                credentials,
                authorities
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

}
