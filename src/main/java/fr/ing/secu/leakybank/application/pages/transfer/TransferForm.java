package fr.ing.secu.leakybank.application.pages.transfer;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferForm {

    @NotNull(message = "Amount is required.")
    private BigDecimal amount;

    @Min(value = 1, message = "Please select a debited account.")
    private int debitAccount;

    @Min(value = 1, message = "Please select a credited account.")
    private int creditAccount;

    private String description;

}
