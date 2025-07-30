package com.Matrimony.Configuration;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		
		if (jwt != null) {
			System.out.println("JWT Received: " + jwt);  // Log the raw JWT
            jwt = jwt.substring(7);  // Remove "Bearer " prefix
            System.out.println("JWT After Bearer Removal:." +jwt);

			try {
				
				System.out.println("in to try block...line 40");
				
				SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				
				System.out.println("secretKey ..." +secretKey);

				Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
				
				System.out.println("Parsed Claims: " + claims);
				
				System.out.println("in to try block...");
				
				
                // Extract email and authorities
				String email = String.valueOf(claims.get("email"));
				
				String authorities = String.valueOf(claims.get("authorities"));
				
				// Convert authorities to GrantedAuthority List
				List<GrantedAuthority> commaSeparatedStringToAuthorityList = AuthorityUtils
						.commaSeparatedStringToAuthorityList(authorities);
				
				// Set authentication in the security context
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
						commaSeparatedStringToAuthorityList);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}catch (Exception e) {
				 System.out.println("in to 2 nd catch block.....");
				 System.out.println("Invalid token...");
	             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	             response.getWriter().write("Invalid token");
	             //return;
	             throw new BadCredentialsException("Invalid token...from jwt validator");
			}
		}
		filterChain.doFilter(request, response);
	}

}
