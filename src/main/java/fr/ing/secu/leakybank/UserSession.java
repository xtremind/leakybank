package fr.ing.secu.leakybank;

import fr.ing.secu.leakybank.application.pages.login.UserDTO;

public class UserSession {
	
	private boolean isAuthenticated = false;
	
	private UserDTO user;

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
