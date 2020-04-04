package project;

import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Holds all functionality to read and write Users to JSON files. Also manages the data within the
 * program
 */
public class UserDatabase implements Database<User> {

    private LinkedList<User> userList;
    private static UserDatabase userDatabase = null;
    private ArrayList<RegisteredUser> users;
    private ArrayList<Admin> admin;

    public UserDatabase() {
        userList = new LinkedList<User>();
        users = DataLoader.loadUsers();
        admin = DataLoader.loadAdmin();
    }

    /**
     * Creates a new instance of UserDatabase in case it is null
     *
     * @return userDatabase
     */
    public static UserDatabase getInstance() {
        if (userDatabase == null) {
            userDatabase = new UserDatabase();
        }
        return userDatabase;
    }

    /**
     * Retrieves the users for the loader
     *
     * @return users
     */
    public ArrayList<RegisteredUser> getUsers() {
        return users;
    }

    /**
     * Retrieves the admin for the loader
     *
     * @return admin
     */
    public ArrayList<Admin> getAdmin() {
        return admin;
    }

    /**
     * Adds user into JSON file using DataWriter class
     *
     * @param username
     * @param password
     * @param status
     */
    public void addUser(String username, String password, Status status) {
        users.add(new RegisteredUser(username, password, status));
        DataWriter.saveUsers();
    }

    /**
     * Adds admin into JSON file using DataWriter class
     *
     * @param username
     * @param password
     */
    public void addAdmin(String username, String password) {
        admin.add(new Admin(username, password));
        DataWriter.saveAdmin();
    }

    /**
     * Adds a user into list within the program
     *
     * @param user
     */
    public void add(User user) {
        userList.add(user);
    }

    /**
     * Removes a user from list within the program
     *
     * @param user
     */
    public boolean remove(User user) {
        return userList.remove(user);
    }

    /**
     * Gets all the users that you are looking for
     *
     * @param username
     * @return user
     */
    public User get(String username) {
        User user = null;
        for (User u : userList) {
            if (user.getUsername().equals(username)) {
                user = u;
            }
        }
        return user;
    }

    /**
     * Checks to see if list is empty
     *
     * @param username
     * @return boolean
     */
    public boolean hasUser(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
