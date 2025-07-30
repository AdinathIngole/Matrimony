
//This utility class will handle the conversion from User entity to UserDTO.
package com.Matrimony.DTO;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Matrimony.Entities.User;

public class UserMapper {
	
	public static UserDTO toUserDTO(User user) {
		
		UserDTO dto = new UserDTO();
		
		dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setAge(user.getAge());
        dto.setGender(user.getGender());
        dto.setReligion(user.getReligion());
        dto.setEducation(user.getEducation());
        dto.setSubscriptionStatus(user.getSubscriptionStatus());
        
     // Convert profile photo from byte[] to Base64 string if available
        if(user.getProfilePhoto() != null && user.getProfilePhotoContentType() != null) {
        	String base64ProfilePhoto = Base64.getEncoder().encodeToString(user.getProfilePhoto());
        	dto.setProfilePhoto(base64ProfilePhoto);
        	dto.setProfilePhotoContentType(user.getProfilePhotoContentType());
        }
        
        if(user.getUserAddtionalDetails() != null) {
        	 dto.setHeight(user.getUserAddtionalDetails().getHeight());
             dto.setAnnualIncome(user.getUserAddtionalDetails().getAnnualIncome());
             dto.setOccupation(user.getUserAddtionalDetails().getOccupation());
             dto.setSmallBio(user.getUserAddtionalDetails().getSmallBio());
             dto.setAboutYourself(user.getUserAddtionalDetails().getAboutYourself());
             dto.setHobbies(user.getUserAddtionalDetails().getHobbies());
             dto.setInterests(user.getUserAddtionalDetails().getInterests());
             dto.setLifestyle(user.getUserAddtionalDetails().getLifestyle());
             dto.setOtherHobby(user.getUserAddtionalDetails().getOtherHobby());
             dto.setOtherInterest(user.getUserAddtionalDetails().getOtherInterest());
             dto.setOtherLifestyle(user.getUserAddtionalDetails().getOtherLifestyle());
             dto.setBackground(user.getUserAddtionalDetails().getBackground());
             dto.setState(user.getUserAddtionalDetails().getState());
             dto.setCity(user.getUserAddtionalDetails().getCity());
             dto.setCaste(user.getUserAddtionalDetails().getCaste());
             dto.setMobileNo(user.getUserAddtionalDetails().getMobileNo());
        }
        return dto;
	}

}
