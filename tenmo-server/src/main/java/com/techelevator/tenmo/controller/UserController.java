package com.techelevator.tenmo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;

@RestController
@RequestMapping("users")
public class UserController {
	
	private final TokenProvider tokenProvider;
    private UserDAO userDao;
    
    public UserController(TokenProvider tokenProvider, UserDAO userDao) {
        this.tokenProvider = tokenProvider;
        this.userDao = userDao;
    }
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public User[] findAll() {
		User[] users = new User[userDao.findAll().size()];
		
		for (int i = 0; i < users.length; i++) {
			users[i] = userDao.findAll().get(i);
		}
		
		return users;
	}

}
