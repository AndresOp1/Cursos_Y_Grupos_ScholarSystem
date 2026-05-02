package com.cursos.servicio_cursos.services.impl;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cursos.servicio_cursos.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Override
  public String getEmail(String tokenHash) {
    Claims claims = Jwts.parser()
        .verifyWith(getSingKey())
        .build()
        .parseSignedClaims(tokenHash)
        .getPayload();
    return claims.getSubject();
  }

  private SecretKey getSingKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
