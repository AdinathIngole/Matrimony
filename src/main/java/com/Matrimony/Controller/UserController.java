package com.Matrimony.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.Matrimony.Configuration.JwtProvider;
import com.Matrimony.DTO.UserDTO;
import com.Matrimony.DTO.UserMapper;
import com.Matrimony.Entities.PasswordResetToken;
import com.Matrimony.Entities.User;
import com.Matrimony.Entities.UserAddtionalDetails;
import com.Matrimony.Exception.UserException;
import com.Matrimony.Repository.PasswordResetTokenRepository;
import com.Matrimony.Request.LoginRequest;
import com.Matrimony.Response.AuthResponse;
import com.Matrimony.Service.UserAddtionalDetailsService;
import com.Matrimony.Service.UserService;
import com.Matrimony.ServiceImpl.CustomeUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserAddtionalDetailsService userAdditionalDetailsServie;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private CustomeUserServiceImpl customeUserServiceImpl;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordResetTokenRepository passTokenRepo;

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<AuthResponse> registerUserHandler(@RequestPart("profileData") String profileData,
			@RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) throws UserException {

		try {
			User user = objectMapper.readValue(profileData, User.class);

			String encodePass = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodePass);
			if (profilePhoto != null && !profilePhoto.isEmpty()) {
				user.setProfilePhoto(profilePhoto.getBytes());
				user.setProfilePhotoContentType(profilePhoto.getContentType());// Set the content type

			}

			User saveUser = userService.saveUser(user);

			Authentication authentication = new UsernamePasswordAuthenticationToken(saveUser.getEmail(),
					saveUser.getPassword());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			String token = jwtProvider.generateToken(authentication);

			AuthResponse authReponse = new AuthResponse();
			authReponse.setJwt(token);
			authReponse.setMessage("signup Success");

			return new ResponseEntity<AuthResponse>(authReponse, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/user-additional-details")
	public ResponseEntity<?> userAdditionalDetails(@RequestBody UserAddtionalDetails user) {

		try {
			System.out.println("Received data: " + user);
			
			 // Authentication check
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()
                    || "anonymousUser".equals(authentication.getPrincipal())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            String loggedInUserEmail = authentication.getName();
            System.out.println("Logged-in user email: " + loggedInUserEmail);

            User loggedInUser = userService.findByEmail(loggedInUserEmail);
            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
            // Save the additional details
            userAdditionalDetailsServie.saveAdditionalDetails(loggedInUser, user);
            return ResponseEntity.ok("Additional details added successfully");
		} catch (Exception e) {
			  e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving details");
		}
		
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {

		String userName = loginRequest.getEmail();
		String Password = loginRequest.getPassword();

		Authentication authentication = authenticate(userName, Password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		// Retrieve user details based on the email (username)
		User userByEmail = userService.findByEmail(userName);

		if (userByEmail == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		AuthResponse authReponse = new AuthResponse();
		authReponse.setJwt(token);
		authReponse.setMessage("signin Success");
		authReponse.setUserId(userByEmail.getId()); // Set the userId in the response)

		return new ResponseEntity<AuthResponse>(authReponse, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserProfile(@PathVariable("userId") Long userId) {
		try {
			User user = userService.findUserById(userId);
			if (user == null) {
				return ResponseEntity.notFound().build();
			}

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (UserException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDTO>> getAllUserList() {

		try {
			List<User> allUsers = userService.getAllUsers();

			if (allUsers.isEmpty()) {
				return ResponseEntity.noContent().build(); // 204 No Content
			}
			// Convert each User entity to UserDTO
			List<UserDTO> userDTO = allUsers.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());

			return ResponseEntity.ok(userDTO); // 200 OK with the list of users
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
		}
	}

	@PostMapping("/updateSubscription")
	public ResponseEntity<?> updateSubscription(@RequestBody User request) {
		try {
			User userByEmail = userService.findByEmail(request.getEmail());
			if (userByEmail == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
			userByEmail.setSubscriptionStatus(request.getSubscriptionStatus());
			userService.saveUser(userByEmail);

			return ResponseEntity.ok().body("Plan updated successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating plan");
		}

	}

	@PutMapping("/edit-Profile")
	public ResponseEntity<AuthResponse> updateUser(@RequestBody UserDTO userDTO) throws UserException {

		try {
			// Get the logged-in user's email from the authentication token
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String loggedInUserEmail = authentication.getName();

			// Fetch the user based on email
			User user = userService.findByEmail(loggedInUserEmail);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}


			// Update the User entity
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			user.setAge(userDTO.getAge());
			user.setGender(userDTO.getGender());
			user.setEducation(userDTO.getEducation());
			if (userDTO.getProfilePhoto() != null) {
				user.setProfilePhoto(userDTO.getProfilePhoto().getBytes());
				user.setProfilePhotoContentType(userDTO.getProfilePhotoContentType());
			}

			// Save the updated User entity
			userService.saveUser(user);

			UserAddtionalDetails userAddtionalDetails = user.getUserAddtionalDetails();

			if (userAddtionalDetails != null) {

				userAddtionalDetails.setMobileNo(userDTO.getMobileNo());
				userAddtionalDetails.setOccupation(userDTO.getOccupation());
				userAddtionalDetails.setCity(userDTO.getCity());
				userAddtionalDetails.setSmallBio(userDTO.getSmallBio());

			}
			userAdditionalDetailsServie.saveAdditionalDetails(user, userAddtionalDetails);

			AuthResponse authResponse = new AuthResponse();
			authResponse.setMessage("User updated successfully");

			return ResponseEntity.ok(authResponse);
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

		}
	}

	@GetMapping("/{id}/profilePhoto")
	public ResponseEntity<byte[]> downloadProfilePhoto(@PathVariable Long id) throws UserException {
		User user = userService.findUserById(id);
		byte[] profilePhoto = user.getProfilePhoto();
		String contentType = user.getProfilePhotoContentType();
		if (contentType == null || contentType.isEmpty()) {
			throw new IllegalArgumentException("Content type is missing for the Aadhaar file.");
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"profilePhoto." + getExtension(contentType) + "\"")
				.body(profilePhoto);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam ("email") String email){
		
		try {
			customeUserServiceImpl.initiatePasswordReset(email);
			 return ResponseEntity.ok("Password reset email sent successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam ("token") String token, 
            @RequestParam ("password") String password){
		PasswordResetToken passwordResetToken = passTokenRepo.findByToken(token);
		if(passwordResetToken == null || passwordResetToken.getExpiryDate().before(new Date())) {
			 return ResponseEntity.badRequest().body("Invalid or expired token.");
		}
		
		User user = passwordResetToken.getUser();
		user.setPassword(passwordEncoder.encode(password));
		userService.saveUser(user);
		passTokenRepo.delete(passwordResetToken);
		
		 return ResponseEntity.ok("Password reset successful.");
	}
	
	private Authentication authenticate(String userName, String password) {

		UserDetails userByUsername = customeUserServiceImpl.loadUserByUsername(userName);

		if (userByUsername == null) {
			throw new BadCredentialsException("Inavalid User name...");
		}
		if (!passwordEncoder.matches(password, userByUsername.getPassword())) {
			throw new BadCredentialsException("Invalid Password...");
		}

		return new UsernamePasswordAuthenticationToken(userByUsername, null, userByUsername.getAuthorities());
	}

	private String getExtension(String contentType) {
		switch (contentType) {
		case "application/pdf":
			return "pdf";
		case "image/jpeg":
			return "jpg";
		default:
			return "bin";
		}
	}
}
