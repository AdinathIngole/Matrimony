package com.Matrimony.Response;

public class AuthResponse {

	private String jwt;
	private String message;
	private Long userId;
	
	public AuthResponse() {
		super();
		
	}
	public AuthResponse(String jwt, String message,Long userId) {
		super();
		this.jwt = jwt;
		this.message = message;
		this.userId = userId;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
