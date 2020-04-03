import java.util.LinkedList;

public class UserDatabase implements Database <User> {
	private LinkedList<User> userList;
	
	public UserDatabase() {
		userList = new LinkedList<User>();
	}
	
	public void add(User user) {
		userList.add(user);
	}

	public boolean remove(User user) {
		return userList.remove(user);
	}
	
	public User get(String username) {
		User user = null;
		for(User u: userList) {
			if(user.getUsername().equals(username))
				user = u;
		}
		return user;
	}
	
	public boolean hasUser(String username) {
		for(User user: userList) {
			if(user.getUsername().equals(username))
				return true;
		}
		return false;
	}
}
