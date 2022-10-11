package fr.ing.secu.leakybank.dao;

import fr.ing.secu.leakybank.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class TransactionsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Return the list of transactions
     *
     * @param accountNumber
     * @return
     */
    public List<Transaction> findTransactionsByAccountNumber(int accountNumber) {
        final String sqlQuery = "select * from INTERNAL_ACCOUNT_TRANSACTIONS where account_number = ? order by OPERATION_DATE desc, ID DESC";
        return jdbcTemplate.query(sqlQuery, preparedStatement -> preparedStatement.setInt(1, accountNumber), (rs, rowNum) -> new Transaction(rs.getInt("ID"), rs.getInt("ACCOUNT_NUMBER"), rs.getBigDecimal("AMOUNT"), rs.getString("DESCRIPTION"), rs.getDate("OPERATION_DATE")));
    }


    /**
     * Persists a money transfer between two internal accounts
     */
    public void processMoneyTransfer(int debitAccountNumber, int creditAccountNumber, BigDecimal amount, String description) {
        // Insert a transaction in the debit account
        final String insertTransactionDebitAccSQL = "INSERT INTO INTERNAL_ACCOUNT_TRANSACTIONS(ACCOUNT_NUMBER, AMOUNT, DESCRIPTION, OPERATION_DATE) values(?,?,?, CURDATE())";
        jdbcTemplate.update(insertTransactionDebitAccSQL, preparedStatement -> {
            preparedStatement.setInt(1, debitAccountNumber);
            preparedStatement.setBigDecimal(2, amount.negate());
            preparedStatement.setString(3, "DEBIT - " + description);
        });
        // Update debit account balance
        final String updateDebitAccSQL = "UPDATE INTERNAL_ACCOUNTS set AVAILABLE_BALANCE=AVAILABLE_BALANCE - ? where ACCOUNT_NUMBER= ?";
        jdbcTemplate.update(updateDebitAccSQL, preparedStatement -> {
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setInt(2, debitAccountNumber);
        });

        // Insert a transaction in the credit account
        final String insertTransactionCreditAccSQL = "INSERT INTO INTERNAL_ACCOUNT_TRANSACTIONS(ACCOUNT_NUMBER, AMOUNT, DESCRIPTION, OPERATION_DATE) values(?,?,?, CURDATE())";
        jdbcTemplate.update(insertTransactionCreditAccSQL, preparedStatement -> {
            preparedStatement.setInt(1, creditAccountNumber);
            preparedStatement.setBigDecimal(2, amount);
            preparedStatement.setString(3, "CREDIT - " + description);
        });
        // Update credit account balance
        final String updateCreditAccSQL = "UPDATE INTERNAL_ACCOUNTS set AVAILABLE_BALANCE=AVAILABLE_BALANCE + ? where ACCOUNT_NUMBER= ?";
        jdbcTemplate.update(updateCreditAccSQL, preparedStatement -> {
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setInt(2, creditAccountNumber);
        });
    }

}
