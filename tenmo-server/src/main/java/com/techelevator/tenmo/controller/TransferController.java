package com.techelevator.tenmo.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.security.jwt.TokenProvider;

@RestController
@RequestMapping("transfers")
public class TransferController {
	
	private final TokenProvider tokenProvider;
    private TransferDAO transferDao;
    private AccountController accountController;
    
    public TransferController(TokenProvider tokenProvider, TransferDAO transferDao) {
        this.tokenProvider = tokenProvider;
        this.transferDao = transferDao;
    }
    
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void createTransfer(@Valid @RequestBody Transfer transfer) {
    	transferDao.createTransfer(transfer.getType(), transfer.getStatus(), transfer.getFrom(), transfer.getTo(), transfer.getAmount());
    	
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@Valid @PathVariable long id) {
    	return transferDao.getTransferById(id);
    }
    
    @RequestMapping(path = "", method = RequestMethod.GET)
    public Transfer[] getAllTransfers(@Valid @RequestParam(defaultValue = "-1") long user) {
    	if (user != -1) {
    		return transferDao.getAll(user);
    	}
    	return null;
    }

}
