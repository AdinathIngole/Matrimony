package com.Matrimony.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int age;
	private String gender;
	private String religion;
	private String education;
	private String subscriptionStatus = "none";
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] profilePhoto;
	
	private String profilePhotoContentType;
	
	@OneToOne(mappedBy = "user" , cascade = CascadeType.ALL , fetch = FetchType.EAGER ,orphanRemoval = true)
	@JsonManagedReference
	private UserAddtionalDetails userAddtionalDetails;
	
	public User() {
		super();
		
	}
	
	//para constructor
	public User(long id, String firstName, String lastName, String email, String password, int age, String gender,
			String religion,String education, byte[] profilePhoto , UserAddtionalDetails userAddtionalDetails) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.religion = religion;
		this.education = education;
		this.profilePhoto = profilePhoto;
		this.userAddtionalDetails = userAddtionalDetails;
	}

	//getter Setter
		public long getId() {
			return id;
		}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}

	
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public byte[] getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	
	public String getProfilePhotoContentType() {
		return profilePhotoContentType;
	}

	public void setProfilePhotoContentType(String profilePhotoContentType) {
		this.profilePhotoContentType = profilePhotoContentType;
	}

	public UserAddtionalDetails getUserAddtionalDetails() {
		return userAddtionalDetails;
	}

	public void setUserAddtionalDetails(UserAddtionalDetails userAddtionalDetails) {
		this.userAddtionalDetails = userAddtionalDetails;
	}

	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}
	
	
	
}
