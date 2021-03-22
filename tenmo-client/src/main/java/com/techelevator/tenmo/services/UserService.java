package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.User;

public class UserService {
	
	private String BASE_URL;
	private String token;
	private RestTemplate restTemplate = new RestTemplate();

	public UserService(String url, String token) {
		this.BASE_URL = url;
		this.token = token;
	}

	public User[] getAll() {
		User[] user = null;
		try {
			// send request here
			user = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class)
					.getBody();
		} catch (RestClientResponseException ex) {

		}
		return user;
	}
	
	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}
