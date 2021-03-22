package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountsDAO {
	
	Account getAccountById(int accountId);
	
	Account getAccountByUserId(int userId);
	
	List<Account> getAll();

	void setAccountBalance(int accountId, double amount);

}
	
//	int accountId();
//	
//	int userId();
//	
//	double balance();




