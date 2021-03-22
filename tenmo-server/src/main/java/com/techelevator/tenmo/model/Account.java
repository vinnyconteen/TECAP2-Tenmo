package com.techelevator.tenmo.model;

//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Set;

public class Account {
	
	private int accountId;
	private int userId;
	private double balance;
	
	public int getAccountId() { 
		return accountId;
	}
	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public double getBalance() {
		return balance;
	}	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	

	// take a list and throw to a string
	@Override
	public String toString() {
		return "User{" + "account id=" + accountId + ", user id='" + userId + '\'' + ", balance=" + balance + '}';
	}

}
