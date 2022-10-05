package fr.ing.secu.leakybank.pages.login;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {
	
	@NotEmpty(message="Login is required.")
	private String login;
	
	@NotEmpty(message="Password is required.")
	private String password;
	
	/**
	 * Optional, target url after login success
	 */
	private String targetUrl;
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}


}
