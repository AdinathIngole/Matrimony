package com.Matrimony.Service;

import java.util.List;

import com.Matrimony.Entities.User;
import com.Matrimony.Exception.UserException;

public interface UserService {

	public User saveUser(User user);
	
	public User findByEmail(String email);
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User getUserById(Long id);
	
	
	
	public List<User> getAllUsers();
}
