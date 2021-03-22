package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;

@Component
public class TransferSqlDAO implements TransferDAO{

	private JdbcTemplate jdbcTemplate;
	
	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void createTransfer(long type, long status, long from, long to, double amount) {
		Transfer[] allTransfers = getAll();
		
		long latestId = 0;
		
		for (Transfer transfer : allTransfers) {
			if (transfer.getId() > latestId) {
				latestId = transfer.getId();
			}
		}
		
		String sql = "insert into transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
					 "values (?, ?, ?, ?, ?, ?);";
		jdbcTemplate.update(sql, latestId + 1, type, status, from, to, amount);
		
	}

	@Override
	public Transfer getTransferById(long id) {
		for (Transfer transfer : this.getAll()) {
			if (transfer.getId() == id) {
				return transfer;
			}
		}
		
		return null;
	}
	
	@Override
	public Transfer[] getAll() {
		List<Transfer> transfers = new ArrayList<>();
		String sqlGetAll = "select * from transfers;";
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlGetAll);
		while(rowSet.next()) {
			Transfer transfer = mapRowToTransfer(rowSet);
			transfers.add(transfer);
		}
		
		return transfers.toArray(new Transfer[transfers.size()]);
	}

	@Override
	public Transfer[] getAll(long id) {
		List<Transfer> transfers = new ArrayList<>();
		String sqlGetAll = "select * from transfers where account_from = ?;";
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlGetAll, id);
		while(rowSet.next()) {
			Transfer transfer = mapRowToTransfer(rowSet);
			transfers.add(transfer);
		}
		
		return transfers.toArray(new Transfer[transfers.size()]);
	}
	
	private Transfer mapRowToTransfer(SqlRowSet rowSet) {
		Transfer transfer = new Transfer();
		transfer.setId(rowSet.getLong("transfer_id"));
		transfer.setType(rowSet.getLong("transfer_type_id"));
		transfer.setStatus(rowSet.getLong("transfer_status_id"));
		transfer.setFrom(rowSet.getLong("account_from"));
		transfer.setTo(rowSet.getLong("account_to"));
		transfer.setAmount(rowSet.getDouble("amount"));
		
		return transfer;
	}

}
