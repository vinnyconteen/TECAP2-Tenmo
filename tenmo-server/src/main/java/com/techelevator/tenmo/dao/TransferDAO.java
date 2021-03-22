package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	void createTransfer(long type, long status, long from, long to, double amount);
	
	Transfer getTransferById(long id);
	
	Transfer[] getAll();

	Transfer[] getAll(long id);
	
}
