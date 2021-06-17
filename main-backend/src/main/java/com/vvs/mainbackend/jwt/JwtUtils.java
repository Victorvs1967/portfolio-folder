package com.vvs.mainbackend.jwt;

import java.util.Date;

import com.vvs.mainbackend.service.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;

@Log
@Component
public class JwtUtils {
  
  @Value("${jwt.secretString}")
  private String jwtSecret;

  @Value("${jwt.expirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
    
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
      .setSubject(userPrincipal.getUsername())
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(SignatureAlgorithm.HS512, jwtSecret)
      .compact();      
  }

  public boolean validateJwtToken(String jwt) {
    try {
      Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(jwt);
      return true;
    } catch (MalformedJwtException e) {
      log.warning(e.getMessage());
    } catch (IllegalArgumentException e) {
      log.warning(e.getMessage());
    }
    return false;
  }

  public String getUserNameFromJwtToken(String jwt) {
    return Jwts.parser()
      .setSigningKey(jwtSecret)
      .parseClaimsJws(jwt)
      .getBody()
      .getSubject();
  }
}
