package com.cursos.servicio_cursos.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cursos.servicio_cursos.entities.UserEntity;
import com.cursos.servicio_cursos.repositories.UserRepository;
import com.cursos.servicio_cursos.services.JwtService;
import com.cursos.servicio_cursos.web.ValidateTokenPort;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final ValidateTokenPort validateTokenPort;
  private final UserDetailsService userDetailsService;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getMethod().equals("OPTIONS")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Berarer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String token = authHeader.substring(7);
    final String userEmail = jwtService.getEmail(token);
    if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
      filterChain.doFilter(request, response);
      return;
    }

    boolean isTokenValid = validateTokenPort.validateToken(token);
    if (!isTokenValid) {
      filterChain.doFilter(request, response);
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
    final Optional<UserEntity> user = userRepository.findByEmail(userDetails.getUsername());
    if (user.isEmpty()) {
      filterChain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(auth);
    filterChain.doFilter(request, response);

  }

}
