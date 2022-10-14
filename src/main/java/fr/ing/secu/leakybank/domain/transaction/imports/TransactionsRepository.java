package fr.ing.secu.leakybank.domain.transaction.imports;

import fr.ing.secu.leakybank.domain.transaction.Transaction;
import fr.ing.secu.leakybank.domain.transaction.Transfer;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

import java.util.List;

@DDD.Repository
public interface TransactionsRepository {
    void transfer(Transfer transfer);
    List<Transaction> findTransactionsByAccountNumber(int accountNumber);

}
