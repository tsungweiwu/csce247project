package project.objects;

/**
 * Specific class that controls all administrative settings and still maintains user foundation
 */
public class Admin extends User {

    private String password;

    public Admin(String username, String password) {
        super(username);
        setPassword(password);
    }

    /**
     * Getters
     */
    public String getUsername() {
        return super.getUsername();
    }

    public String getPassword() {
        return this.password;
    }

    /**
     * Setter
     */
    public void setPassword(String password) {
        this.password = password;
    }


}
