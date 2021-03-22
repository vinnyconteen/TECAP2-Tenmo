package com.techelevator.tenmo.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.security.jwt.TokenProvider;

@RestController
@RequestMapping("accounts")
public class AccountController {
	
	//add authorization
	
	//private final TokenProvider tokenProvider;
    private AccountsDAO accountDao;
    
    public AccountController(TokenProvider tokenProvider, AccountsDAO accountDao) {
        //this.tokenProvider = tokenProvider;
        this.accountDao = accountDao;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@Valid @PathVariable int id)  {
		
		return accountDao.getAccountByUserId(id);
	}
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean accountBalanceUpdate(@Valid @RequestBody Transfer transfer) {
    	Account sender = accountDao.getAccountByUserId((int) transfer.getFrom());
    	Account reciever = accountDao.getAccountByUserId((int) transfer.getTo());
    	
    	//request body
    	
    	if (transfer.getAmount() <= sender.getBalance()) {
    		sender.setBalance(sender.getBalance() - transfer.getAmount());
    		reciever.setBalance(reciever.getBalance() + transfer.getAmount());
    		
    		System.out.println(sender.getBalance());
    		
    		accountDao.setAccountBalance(sender.getAccountId(), sender.getBalance());
    		accountDao.setAccountBalance(reciever.getAccountId(), reciever.getBalance());
    		return true;
    	}
    	return false;
    }
}
