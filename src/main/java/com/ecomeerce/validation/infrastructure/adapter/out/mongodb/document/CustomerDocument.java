package com.ecomeerce.validation.infrastructure.adapter.out.mongodb.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "customers")
public class CustomerDocument {
	
	@Id
	private String userId;
	
	private String firstName;
	private String paternalLastName;
	private String maternalLastName;
	private String email;
	private String shippingAddress;
	private List<String> orders;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPaternalLastName() {
		return paternalLastName;
	}
	public void setPaternalLastName(String paternalLastName) {
		this.paternalLastName = paternalLastName;
	}
	public String getMaternalLastName() {
		return maternalLastName;
	}
	public void setMaternalLastName(String maternalLastName) {
		this.maternalLastName = maternalLastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public List<String> getOrders() {
		return orders;
	}
	public void setOrders(List<String> orders) {
		this.orders = orders;
	}
	
}
