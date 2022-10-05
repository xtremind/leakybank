package fr.ing.secu.leakybank.model;

public class User {
	
	private String login;
	
	private String firstName;
	
	private String lastName;
	
	private boolean isAdmin;
	
	public User(String login, String firstName, String lastName, boolean isAdmin) {
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setAdmin(isAdmin);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
