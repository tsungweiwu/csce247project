package project;

public class Admin extends User {
	private String password;

	public Admin(String username, String password) {
		super(username);
		setPassword(password);
	}
	
	public String getUsername() {
		return super.getUsername();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
}
