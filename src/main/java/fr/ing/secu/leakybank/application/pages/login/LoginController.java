package fr.ing.secu.leakybank.application.pages.login;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Strings;

import fr.ing.secu.leakybank.UserSession;
import fr.ing.secu.leakybank.application.pages.login.mapper.UserMapper;
import fr.ing.secu.leakybank.domain.UserService;
import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.pages.BaseController;

/**
 * Login page ( = welcome page of the application)
 * 
 * @author chouippea
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	private UserSession userSession;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(@ModelAttribute("loginForm") LoginForm loginForm) {
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitLogin(@ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result,
			HttpSession session) {

		// Return to login page for validation errors
		if (result.hasErrors()) {
			return "login";
		} else {
			return userService.authenticate(UserMapper.INSTANCE.toDomain(loginForm))
					.map(manageLoggedCustomer(loginForm.getTargetUrl(), session))
					.orElseGet(manageUnloggedUser(result));
		}

	}

	private Function<Customer, String> manageLoggedCustomer(String targetUrl, HttpSession session){
		return customer -> {
			UserDTO user = UserMapper.INSTANCE.toDTO(customer);
			userSession.setAuthenticated(true);
			userSession.setUser(user);
			session.setAttribute("user", user);
			if (Strings.isNullOrEmpty(targetUrl)) {
				return customer.isAdmin() ? "redirect:admin/users" : "redirect:accounts";
			} else {
				return "redirect:" + targetUrl;
			}
		};
	}


	private Supplier<? extends String> manageUnloggedUser(BindingResult result) {
		return () -> {
			result.reject("badCredentials", "Invalid credentials, please retry.");
			return "login";
		};
	}
}
