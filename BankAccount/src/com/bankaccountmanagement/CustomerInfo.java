package com.bankaccountmanagement;

public class CustomerInfo {
	private String name,address;
	private long phoneNumber;
	private int customerId;
	public void setName(String name) {
		this.name=name;
	}
	public void setCustomerId(int customerId) {
		this.customerId=customerId;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber=phoneNumber;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public int getCustomerId() {
		return customerId;
	}
}
