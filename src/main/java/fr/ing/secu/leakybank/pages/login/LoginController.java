package fr.ing.secu.leakybank.pages.login;

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
import fr.ing.secu.leakybank.dao.UsersDAO;
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
	private UsersDAO loginDao;

	@RequestMapping(method = RequestMethod.GET)
	public String showLogin(@ModelAttribute("loginForm") LoginForm loginForm) {
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitLogin(@ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result, HttpSession session) {

		// Return to login page for validation errors
		if (result.hasErrors()) {
			return "login";
		} else {
			
			// Try to authenticate user
			return loginDao.login(loginForm.getLogin(), loginForm.getPassword()).map(user -> {
				userSession.setAuthenticated(true);
				userSession.setUser(user);
				session.setAttribute("user", user);
				if (Strings.isNullOrEmpty(loginForm.getTargetUrl())) {
					return user.isAdmin() ? "redirect:admin/users" : "redirect:accounts";
				} else {
					return "redirect:" + loginForm.getTargetUrl();
				}
			}).orElseGet(() -> {
				result.reject("badCredentials", "Invalid credentials, please retry.");
				return "login";
			});


		}

	}

}
