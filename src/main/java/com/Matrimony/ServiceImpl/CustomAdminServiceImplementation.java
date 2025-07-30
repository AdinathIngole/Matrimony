package com.Matrimony.ServiceImpl;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAdminServiceImplementation implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null) {
            throw new UsernameNotFoundException("Admin not found with username: " + username);
        }
		
		List<SimpleGrantedAuthority> singletonList = Collections.singletonList(new SimpleGrantedAuthority(username));
		return new org.springframework.security.core.userdetails.User(username, null, singletonList);
	}

}
