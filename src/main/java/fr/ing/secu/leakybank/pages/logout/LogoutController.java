package fr.ing.secu.leakybank.pages.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.ing.secu.leakybank.pages.BaseController;

/**
 * Logout page 
 * @author chouippea
 *
 */
@Controller
@RequestMapping("/logout")
public class LogoutController extends BaseController {


	/**
	 * Invalidate the current session and redirect to the login page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		
		return "redirect:login?message=Logout succeeded.";
	}
	
}
