package fr.ing.secu.leakybank.pages.transfer;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransferForm {

	@NotNull(message = "Amount is required.")
	private BigDecimal amount;

	@Min(value=1, message = "Please select a debited account.")
	private int debitAccount;

	@Min(value=1, message = "Please select a credited account.")
	private int creditAccount;

	private String description;

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

	public int getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(int debitAccount) {
		this.debitAccount = debitAccount;
	}

	public int getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(int creditAccount) {
		this.creditAccount = creditAccount;
	}
	
	

}
