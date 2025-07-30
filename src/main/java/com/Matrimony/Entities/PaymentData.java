package com.Matrimony.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PaymentData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String email;
	private String contact;
	private double amount;
	private String rzpSignature;
	private String rzpOrderId;
	private String rzpPaymentId;
	
	public PaymentData() {
		super();
		
	}
	
	public PaymentData(long id, String name, String email, String contact, double amount, String rzpSignature,
			String rzpOrderId, String rzpPaymentId) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.contact = contact;
		this.amount = amount;
		this.rzpSignature = rzpSignature;
		this.rzpOrderId = rzpOrderId;
		this.rzpPaymentId = rzpPaymentId;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getRzpSignature() {
		return rzpSignature;
	}
	public void setRzpSignature(String rzpSignature) {
		this.rzpSignature = rzpSignature;
	}
	public String getRzpOrderId() {
		return rzpOrderId;
	}
	public void setRzpOrderId(String rzpOrderId) {
		this.rzpOrderId = rzpOrderId;
	}
	public String getRzpPaymentId() {
		return rzpPaymentId;
	}
	public void setRzpPaymentId(String rzpPaymentId) {
		this.rzpPaymentId = rzpPaymentId;
	}
	
	
}
