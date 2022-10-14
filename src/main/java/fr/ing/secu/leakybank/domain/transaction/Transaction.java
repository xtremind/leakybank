package fr.ing.secu.leakybank.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {

    private int id;
    private int accountNumber;
    private BigDecimal amount;
    private String description;
    private Date operationDate;

}
