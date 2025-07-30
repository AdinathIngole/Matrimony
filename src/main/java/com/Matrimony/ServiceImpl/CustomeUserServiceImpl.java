package com.Matrimony.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Matrimony.Configuration.JwtProvider;
import com.Matrimony.Entities.PasswordResetToken;
import com.Matrimony.Entities.User;
import com.Matrimony.Exception.UserException;
import com.Matrimony.Repository.PasswordResetTokenRepository;
import com.Matrimony.Repository.UserRepository;
import com.Matrimony.Service.EmailService;

@Service
public class CustomeUserServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private PasswordResetTokenRepository passTokenRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("User not found With Email..." +username);
		}
		
		 List<GrantedAuthority> authorities = new ArrayList<>();
		 
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}
	
	public void initiatePasswordReset(String email) throws UserException{
		
		User user = userRepository.findByEmail(email);
		
		if (user == null) {
		 	System.out.println("No User found with this email address.");
	        throw new UserException("No User found with this email address.");
	        
	    }
		
		String tokenForPasswordReset = jwtProvider.generateTokenForPasswordReset(user.getEmail());
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		
		passwordResetToken.setToken(tokenForPasswordReset);
		passwordResetToken.setUser(user);
		passwordResetToken.setExpiryDate(new Date(new Date().getTime() + 3600000));
		
		passTokenRepository.save(passwordResetToken);
		
		String resetLink = "http://localhost:3000/reset-password?token=" + tokenForPasswordReset;
		emailService.sendEmail(user.getEmail(), "Password Reset Request", "To reset your password, click the link below:\n" + resetLink);
	}

}
