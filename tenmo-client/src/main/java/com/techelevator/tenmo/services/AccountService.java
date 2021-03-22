package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;

public class AccountService {
	
	private String BASE_URL;
	private String token;
	private RestTemplate restTemplate = new RestTemplate();

	public AccountService(String url, String token) {
		this.BASE_URL = url;
		this.token = token;
	}
	
	public Account getAccount(int id) {
		Account account = null;
		try {
			account = restTemplate
					.exchange(BASE_URL + "accounts/" + id, HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
		} catch (RestClientResponseException ex) {
		}
		return account;
	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}
