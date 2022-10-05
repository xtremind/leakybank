package fr.ing.secu.leakybank.pages.accounts;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.ing.secu.leakybank.UserSession;
import fr.ing.secu.leakybank.dao.AccountsDAO;
import fr.ing.secu.leakybank.dao.TransactionsDAO;
import fr.ing.secu.leakybank.model.InternalAccount;
import fr.ing.secu.leakybank.pages.BaseController;

/**
 * Controller for accounts pages : List of accounts, Detail of an account
 * 
 * @author chouippea
 *
 */
@Controller
@RequestMapping("/accounts")
public class AccountsController extends BaseController {

	@Autowired
	private UserSession userSession;

	@Autowired
	private AccountsDAO accountsDao;

	@Autowired
	private TransactionsDAO transactionsDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView accountList() {

		ModelAndView mView = new ModelAndView("accounts");

		// Add user detail to view
		mView.addObject("user", userSession.getUser());

		// Get list of accounts
		List<InternalAccount> accounts = accountsDao.findInternalAccountsByUser(userSession.getUser().getLogin());
		mView.addObject("internalAccounts", accounts);

		// Compute total available balance
		BigDecimal totalAvailableBalance = accounts.stream().map(InternalAccount::getAvailableBalance).reduce((acc, value) -> acc.add(value))
				.orElse(BigDecimal.ZERO);
		mView.addObject("totalAvailableBalance", totalAvailableBalance);

		return mView;
	}

	@RequestMapping(value = "/{accountNumber}", method = RequestMethod.GET)
	public ModelAndView accountDetail(@PathVariable int accountNumber) {
			
		return accountsDao.findInternalAccountByAccountNumber(accountNumber).map(account ->
			new ModelAndView("accountDetail")
				.addObject("account", account)
				.addObject("user", userSession.getUser())
				.addObject("transactions", transactionsDao.findTransactionsByAccountNumber(accountNumber))
			).orElse(new ModelAndView("redirect:/accounts"));
		
	}

}
