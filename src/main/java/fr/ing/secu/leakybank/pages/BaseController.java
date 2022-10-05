package fr.ing.secu.leakybank.pages;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Base class of all the Spring MVC controllers
 * 
 * @author chouippea
 *
 */
public abstract class BaseController {
	
	
	/**
	 * Just there to bypass Thymeleaf XSS protection mechanism
	 * @param message
	 * @return
	 */
	@ModelAttribute("message")
	public String message(HttpServletRequest request) {
		return request.getParameter("message");
	}

}
