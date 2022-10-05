package fr.ing.secu.leakybank.model;

import java.math.BigDecimal;

public class InternalAccount {

	private int accountNumber;

	private AccountType accountType;

	private BigDecimal availableBalance;

	public InternalAccount(int accountNumber, AccountType type, BigDecimal availableBalance) {
		this.accountNumber = accountNumber;
		this.setAccountType(type);
		this.setAvailableBalance(availableBalance);
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

}
