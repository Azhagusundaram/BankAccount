package com.bankaccountmanagement;

public class AccountInfo {
	private long accountNumber;
	private int customerId;
	private float balance;
	public void setAccountNumber(long accountNumber) {
		this.accountNumber=accountNumber;
	}
	public void setCustomerId(int customerId) {
		this.customerId=customerId;
	}
	public void setBalance(float balance) {
		this.balance=balance;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public int getCustomerId() {
		return customerId;
	}
	public float getbalance() {
		return balance;
	}
}
