package com.Matrimony.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class UserAddtionalDetails {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
	
	 private String height;
	 private String annualIncome;
	 private String occupation;
	 private String smallBio;
	 private String aboutYourself;
	 private String hobbies; // Comma-separated values
	 private String interests; // Comma-separated values
	 private String lifestyle; // Comma-separated values
	 private String otherHobby;
	 private String otherInterest;
	 private String otherLifestyle;
	 private String background;
	 private String state;
	 private String city;
	 private String caste;
	 private String mobileNo;
	 
	 @OneToOne
	 @JoinColumn(name = "user_id")
	 @JsonBackReference
	 private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSmallBio() {
		return smallBio;
	}

	public void setSmallBio(String smallBio) {
		this.smallBio = smallBio;
	}

	public String getAboutYourself() {
		return aboutYourself;
	}

	public void setAboutYourself(String aboutYourself) {
		this.aboutYourself = aboutYourself;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getLifestyle() {
		return lifestyle;
	}

	public void setLifestyle(String lifestyle) {
		this.lifestyle = lifestyle;
	}

	public String getOtherHobby() {
		return otherHobby;
	}

	public void setOtherHobby(String otherHobby) {
		this.otherHobby = otherHobby;
	}

	public String getOtherInterest() {
		return otherInterest;
	}

	public void setOtherInterest(String otherInterest) {
		this.otherInterest = otherInterest;
	}

	public String getOtherLifestyle() {
		return otherLifestyle;
	}

	public void setOtherLifestyle(String otherLifestyle) {
		this.otherLifestyle = otherLifestyle;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	 
	 
}
