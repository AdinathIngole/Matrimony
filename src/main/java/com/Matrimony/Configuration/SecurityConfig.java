package com.Matrimony.Configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.Matrimony.ServiceImpl.CustomAdminServiceImplementation;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {
	
	/*
	 * @Bean public UserDetailsService userDetailsService() { return new
	 * CustomeUserServiceImpl(); // Custom service to load User by username }
	 */
	 @Bean
	 public UserDetailsService adminDetailsService() {
	        return new CustomAdminServiceImplementation();  // Custom service to load admin by username
	    }
	 @Bean
	 public AuthenticationProvider adminAuthenticationProvider() {
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(adminDetailsService());
	        provider.setPasswordEncoder(passwordEncoder());
	        return provider;
	    }
	 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeHttpRequests(Authorize -> Authorize
				.requestMatchers(
						"/api/user/**",
						"/api/contact/**",
						"/api/payments/**")
				.permitAll()
				.requestMatchers("/api/admins/**").hasRole("ADMIN")
				.anyRequest()
				.authenticated())
		.authenticationProvider(adminAuthenticationProvider())
		.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
		.csrf().disable()
		.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				
				corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3001"));
				corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
				corsConfiguration.setAllowCredentials(true);
				corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
				corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
				corsConfiguration.setMaxAge(3600L);
				return corsConfiguration;
			}
		})
		.and().httpBasic().and().formLogin();
		
		return http.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
