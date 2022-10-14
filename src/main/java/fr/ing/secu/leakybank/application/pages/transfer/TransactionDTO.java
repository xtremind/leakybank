package fr.ing.secu.leakybank.application.pages.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class TransactionDTO {

    private int id;
    private int accountNumber;
    private BigDecimal amount;
    private String description;
    private Date operationDate;

}
