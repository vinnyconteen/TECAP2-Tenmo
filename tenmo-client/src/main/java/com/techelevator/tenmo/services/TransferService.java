package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Transfer;

public class TransferService {

	private String BASE_URL;
	private String token;
	private RestTemplate restTemplate = new RestTemplate();

	public TransferService(String url, String token) {
		this.BASE_URL = url;
		this.token = token;
	}

	public Transfer[] getAll(long id) {
		Transfer[] transfer = null;
		try {
			// send request here
			transfer = restTemplate.exchange(BASE_URL + "transfers?user=" + id, HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
		} catch (RestClientResponseException ex) {
			ex.printStackTrace();
		}
		return transfer;
	}

	public Transfer getTransfer(int id) {
		Transfer transfer = null;
		try {
			transfer = restTemplate
					.exchange(BASE_URL + "transfers/{" + id + "}", HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
		} catch (RestClientResponseException ex) {
		}
		return transfer;
	}

	public Transfer createTransfer(long type, long status, long from, long to, double amount) {
		Transfer transfer = new Transfer();
		transfer.setType(type);
		transfer.setStatus(status);
		transfer.setFrom(from);
		transfer.setTo(to);
		transfer.setAmount(amount);

		try {
			Transfer newTransfer = restTemplate.postForObject(BASE_URL + "transfers/create", makeTransferEntity(transfer), Transfer.class);
			ResponseEntity<Boolean> success = restTemplate.exchange(BASE_URL + "accounts", HttpMethod.POST, makeTransferEntity(transfer), boolean.class);
		} catch (RestClientResponseException ex) {
			ex.printStackTrace();
		}
		
		return transfer;
	}

	private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
		return entity;
	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}
