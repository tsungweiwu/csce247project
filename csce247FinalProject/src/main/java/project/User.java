package project;

/**
 * Default User class
 */
public class User {

    protected String username;

    public User(String username) {
        this.setUsername(username);
    }

    /**
     * Getter
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
