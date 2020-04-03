/**
 * 
 */
package project;

import java.util.ArrayList;
/**
 *
 */
public class UserDatabase {
	private static UserDatabase userDatabase = null;
	private static ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();
	private static ArrayList<Admin> admin = new ArrayList<Admin>();
	
	private UserDatabase() {
		users = DataLoader.loadUsers();
		admin = DataLoader.loadAdmin();
	}
	
	public static UserDatabase getInstance() {
		if(userDatabase == null) {
			userDatabase = new UserDatabase();
		}
		return userDatabase;
	}
	
	public ArrayList<RegisteredUser> getUsers() {
		return users;
	}
	
	public ArrayList<Admin> getAdmin() {
		return admin;
	}
	
	public void addUser(String username, String password, Status status) {
		users.add(new RegisteredUser(username, password, status));
		DataWriter.saveUsers();
	}
	
	public void addAdmin(String username, String password) {
		admin.add(new Admin(username, password));
		DataWriter.saveAdmin();
	}
}