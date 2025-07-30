package com.Matrimony.Configuration;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {

	SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication auth) {
		 
		String jwt=Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+846000000))
				.claim("email", auth.getName())
				.signWith(key).compact();
		
		// Log the generated token
	    System.out.println("Generated JWT: " + jwt);
	    
		return jwt;
		
	}
	
	public String getEmailFromToken(String jwt) {
		jwt=jwt.substring(7);
		
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		
		String email=String.valueOf(claims.get("email"));
		
		return email;
	}
	
	 public Date getExpirationDateFromToken(String jwt) {
		 jwt = jwt.substring(7);
		 
	     Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
	     
	     return claims.getExpiration();

	 }
	 
	// Method to generate a token specifically for password reset purposes
    public String generateTokenForPasswordReset(String email) {
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 3600000)) // 1 hour validity
                .claim("email", email)
                .signWith(key)
                .compact();
        return jwt;
    }
}
