package project;

import java.util.LinkedList;
import java.util.ArrayList;

public class UserDatabase implements Database<User> {
	private LinkedList<User> userList;
	private static UserDatabase userDatabase = null;
	private ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();
	private ArrayList<Admin> admin = new ArrayList<Admin>();

	public UserDatabase() {
		userList = new LinkedList<User>();
		users = DataLoader.loadUsers();
		admin = DataLoader.loadAdmin();
	}

	public static UserDatabase getInstance() {
		if (userDatabase == null) {
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

	public void add(User user) {
		userList.add(user);
	}

	public boolean remove(User user) {
		return userList.remove(user);
	}

	public User get(String username) {
		User user = null;
		for (User u : userList) {
			if (user.getUsername().equals(username))
				user = u;
		}
		return user;
	}

	public boolean hasUser(String username) {
		for (User user : userList) {
			if (user.getUsername().equals(username))
				return true;
		}
		return false;
	}
}
