package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AccountsSqlDAO implements AccountsDAO {

	private JdbcTemplate jdbcTemplate;

	public AccountsSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Account> getAll() {
		List<Account> accounts = new ArrayList<>();
		String sqlGetAll = "Select * from accounts;";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAll);
		while (results.next()) {
			Account account = mapRowToAccounts(results);
			accounts.add(account);
		}
		return accounts;
	}

	@Override
	public Account getAccountById(int accountId) {
		for (Account accountIdentity : this.getAll()) {
			if (accountIdentity.getAccountId() == accountId) {
				return accountIdentity;
			}
		}
		return null;
	}

	@Override
	public Account getAccountByUserId(int userId) {
		Account account = new Account();
		String sqlGetAll = "Select * from accounts where user_id = ?;";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAll, userId);
		while (results.next()) {
			account = mapRowToAccounts(results);
		}

		return account;
	}
	
	@Override
	public void setAccountBalance(int accountId, double amount) {
		String sqlSetBalance = "update accounts set balance = ? where account_id = ?;";
		
		jdbcTemplate.update(sqlSetBalance, amount, accountId); 
	}

	private Account mapRowToAccounts(SqlRowSet srs) {
		Account accountMap = new Account();
		accountMap.setAccountId(srs.getInt("account_id"));
		accountMap.setUserId(srs.getInt("user_id"));
		accountMap.setBalance(srs.getDouble("balance"));
		return accountMap;
	}

}
