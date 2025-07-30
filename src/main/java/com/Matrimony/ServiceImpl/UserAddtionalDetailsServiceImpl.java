package com.Matrimony.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Matrimony.Entities.User;
import com.Matrimony.Entities.UserAddtionalDetails;
import com.Matrimony.Repository.UserAddtionalDetailsRepository;
import com.Matrimony.Repository.UserRepository;
import com.Matrimony.Service.UserAddtionalDetailsService;

@Service
public class UserAddtionalDetailsServiceImpl implements UserAddtionalDetailsService  {

	@Autowired
	private UserAddtionalDetailsRepository userAddtionalDetailsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void saveAdditionalDetails(User user, UserAddtionalDetails userDetails) {
		// Set the User reference in UserAdditionalDetails
		userDetails.setUser(user);
		 // Set the User's additional details
		user.setUserAddtionalDetails(userDetails);
		// Save the user entity (this will also save UserAddtionalDetails due to cascading)
        
		userRepository.save(user);
		
	}



}
