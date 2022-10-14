package fr.ing.secu.leakybank.domain.transaction;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transfer {

    @NonNull
    private BigDecimal amount;
    @NonNull
    private int debitAccount;
    @NonNull
    private int creditAccount;
    private String description;

}
