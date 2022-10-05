package fr.ing.secu.leakybank.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import fr.ing.secu.leakybank.model.AccountType;
import fr.ing.secu.leakybank.model.InternalAccount;

/**
 * DAO that manages internal accounts
 * 
 * @author chouippea
 *
 */
@Repository
public class AccountsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** Maps a result set row to a InternalAccount object */
	private RowMapper<InternalAccount> internalAccountMapper = (rs, rowNum) -> new InternalAccount(rs.getInt("ACCOUNT_NUMBER"), 
			new AccountType(rs.getInt("ACCOUNT_TYPE"), rs.getString("LABEL")), rs.getBigDecimal("AVAILABLE_BALANCE"));

	/**
	 * Return the list of internal accounts owned by the user
	 * 
	 * @param userLogin
	 *            login of the user
	 * @return the list of internal accounts owned by the user
	 */
	public List<InternalAccount> findInternalAccountsByUser(String userLogin) {
		return jdbcTemplate.query("select * from INTERNAL_ACCOUNTS account, INTERNAL_ACCOUNT_TYPES type where account.owner='" + userLogin
				+ "' and account.account_type = type.id", internalAccountMapper);
	}

	/**
	 * Return an internal account from its account number
	 * 
	 * @param accountNumber
	 *            account number of the requested account
	 * @return an internal account from its account number
	 */
	public Optional<InternalAccount> findInternalAccountByAccountNumber(final int accountNumber) {
		try {
		return Optional.of(jdbcTemplate.queryForObject("select * from INTERNAL_ACCOUNTS account, INTERNAL_ACCOUNT_TYPES type where account.account_type=type.id and ACCOUNT_NUMBER=" + accountNumber,
				internalAccountMapper));
		} catch (EmptyResultDataAccessException ex) {
			return Optional.empty();
		}
	}

}
