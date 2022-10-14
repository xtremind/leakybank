package fr.ing.secu.leakybank.domain;

import fr.ing.secu.leakybank.domain.transaction.Transaction;
import fr.ing.secu.leakybank.domain.transaction.Transfer;
import fr.ing.secu.leakybank.domain.transaction.imports.TransactionsRepository;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DDD.Service
public class TransactionsService {

    @Autowired
    TransactionsRepository transactionsRepository;

    public void transfer(Transfer transfer) {
        transactionsRepository.transfer(transfer);
    }

    public List<Transaction> findTransactionsByAccountNumber(int accountNumber) {
        return transactionsRepository.findTransactionsByAccountNumber(accountNumber);
    }
}
