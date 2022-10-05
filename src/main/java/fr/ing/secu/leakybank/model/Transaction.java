package fr.ing.secu.leakybank.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
	
	private int id;
	
	private int accountNumber;
	
	private BigDecimal amount;
	
	private String description;
	
	private Date operationDate;
	

	public Transaction(int id, int accountNumber, BigDecimal amount, String description, Date operationDate) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.description = description;
		this.operationDate = operationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	
	

}
