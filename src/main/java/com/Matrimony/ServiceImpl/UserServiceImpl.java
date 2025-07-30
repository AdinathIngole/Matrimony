package com.Matrimony.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Matrimony.DTO.UserDTO;
import com.Matrimony.DTO.UserMapper;
import com.Matrimony.Entities.User;
import com.Matrimony.Entities.UserAddtionalDetails;
import com.Matrimony.Exception.UserException;
import com.Matrimony.Repository.UserAddtionalDetailsRepository;
import com.Matrimony.Repository.UserRepository;
import com.Matrimony.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserAddtionalDetailsRepository userAdditionalDetailsRepo;

	@Override
	public User saveUser(User user) {
		
		return userRepo.save(user);
	}

	@Override
	public User findByEmail(String email) {
		
		return userRepo.findByEmail(email);
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		
		return userRepo.findById(userId).orElseThrow(() -> new UserException("User Not Found"));
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		try {
			List<User> allUsers = userRepo.findAll();
			return allUsers;
		} catch (Exception e) {
			// Log the error
	        e.printStackTrace(); // You can replace this with a proper logging framework
	        throw new RuntimeException("Failed to fetch users");
		}
		
		
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
