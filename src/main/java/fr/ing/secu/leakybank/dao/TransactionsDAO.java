package fr.ing.secu.leakybank.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.ing.secu.leakybank.model.Transaction;

@Repository
public class TransactionsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Return the list of transactions 
	 * @param accountNumber
	 * @return
	 */
	public List<Transaction> findTransactionsByAccountNumber(int accountNumber) {
		return jdbcTemplate.query("select * from INTERNAL_ACCOUNT_TRANSACTIONS where account_number=" + accountNumber + " order by OPERATION_DATE desc, ID DESC", 
				(rs, rowNum) -> new Transaction(rs.getInt("ID"), rs.getInt("ACCOUNT_NUMBER"), rs.getBigDecimal("AMOUNT"), rs.getString("DESCRIPTION"), rs.getDate("OPERATION_DATE")));
	}
	
	
	/** 
	 * Persists a money transfer between two internal accounts
	 */
	public void processMoneyTransfer(int debitAccountNumber, int creditAccountNumber, BigDecimal amount, String description) {
		// Insert a transaction in the debit account 
		jdbcTemplate.update("INSERT INTO INTERNAL_ACCOUNT_TRANSACTIONS(ACCOUNT_NUMBER, AMOUNT, DESCRIPTION, OPERATION_DATE) values(" + 
		debitAccountNumber + ", " + amount.negate() + ", 'DEBIT - " + description + "', CURDATE())");
		// Update debit account balance
		jdbcTemplate.update("UPDATE INTERNAL_ACCOUNTS set AVAILABLE_BALANCE=AVAILABLE_BALANCE - " + amount + " where ACCOUNT_NUMBER=" + debitAccountNumber);
		
		// Insert a transaction in the credit account
		jdbcTemplate.update("INSERT INTO INTERNAL_ACCOUNT_TRANSACTIONS(ACCOUNT_NUMBER, AMOUNT, DESCRIPTION, OPERATION_DATE) values(" + 
		creditAccountNumber + ", " + amount + ", 'CREDIT - " + description + "', CURDATE())");	
		// Update credit account balance
		jdbcTemplate.update("UPDATE INTERNAL_ACCOUNTS set AVAILABLE_BALANCE=AVAILABLE_BALANCE + " + amount + " where ACCOUNT_NUMBER=" + creditAccountNumber);
	}

}
