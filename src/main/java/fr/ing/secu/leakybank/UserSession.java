package fr.ing.secu.leakybank;

import fr.ing.secu.leakybank.model.User;

public class UserSession {
	
	private boolean isAuthenticated = false;
	
	private User user;

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
