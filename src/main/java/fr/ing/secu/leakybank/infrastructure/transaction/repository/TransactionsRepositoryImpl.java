package fr.ing.secu.leakybank.infrastructure.transaction.repository;

import fr.ing.secu.leakybank.domain.transaction.Transaction;
import fr.ing.secu.leakybank.domain.transaction.Transfer;
import fr.ing.secu.leakybank.domain.transaction.imports.TransactionsRepository;
import fr.ing.secu.leakybank.infrastructure.transaction.mapper.TransactionMapper;
import fr.ing.secu.leakybank.infrastructure.transaction.repository.db.TransactionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionsRepositoryImpl implements TransactionsRepository {

    @Autowired
    TransactionsDAO transactionsDAO;


    @Override
    public void transfer(Transfer transfer) {
        transactionsDAO.processMoneyTransfer(transfer.getDebitAccount(), transfer.getCreditAccount(), transfer.getAmount(), transfer.getDescription());
    }

    @Override
    public List<Transaction> findTransactionsByAccountNumber(int accountNumber) {
        return transactionsDAO.findTransactionsByAccountNumber(accountNumber).stream().map(TransactionMapper.INSTANCE::toDomain).collect(Collectors.toList());
    }
}
