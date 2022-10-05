package fr.ing.secu.leakybank.pages.transfer;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.ing.secu.leakybank.UserSession;
import fr.ing.secu.leakybank.dao.AccountsDAO;
import fr.ing.secu.leakybank.dao.TransactionsDAO;
import fr.ing.secu.leakybank.pages.BaseController;

@Controller
@RequestMapping("/transfers")
public class TransferController extends BaseController {

	@Autowired
	private UserSession userSession;

	@Autowired
	private AccountsDAO accountsDOA;
	
	@Autowired
	private TransactionsDAO transactionDAO;

	/**
	 * Display the money transfer page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String transferPage(@ModelAttribute("transferForm") TransferForm transferForm, BindingResult result, final ModelMap model) {

		fillModel(model);

		return "transfers";
	};

	/**
	 * Process a money transfer request
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/new")
	public String createTransfer(@ModelAttribute("transferForm") @Valid TransferForm transferForm, BindingResult result, final ModelMap model) {
		fillModel(model);
		
		// Form validation
		// Min transfer amount value
		if (transferForm.getAmount() != null && transferForm.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			result.rejectValue("amount", "amount.negativeValue", "Please enter a positive amount.");
		}
		
		// Checks that debit & credit accounts are not the same
		if (transferForm.getCreditAccount() != 0 & transferForm.getDebitAccount() != 0 && transferForm.getCreditAccount() == transferForm.getDebitAccount()) {
			result.rejectValue("creditAccount", "creditAccount.debitAccount", "Please select distinct debit and credit accounts.");
		}
		
		// Return to the transfer page for validation errors
		if (result.hasErrors()) {
			return "transfers";
		} 
		// Else, persist the money transfer in database
		else {
			transactionDAO.processMoneyTransfer(transferForm.getDebitAccount(), transferForm.getCreditAccount(), transferForm.getAmount(), transferForm.getDescription());
			return "redirect:/accounts?message=Money transfer succeeded.";
		}


	};

	/**
	 * Fill fields
	 * 
	 * @param model
	 */
	private void fillModel(ModelMap model) {
		// Add debit / credit accounts
		model.put("accounts", accountsDOA.findInternalAccountsByUser(userSession.getUser().getLogin()));
	}
}
