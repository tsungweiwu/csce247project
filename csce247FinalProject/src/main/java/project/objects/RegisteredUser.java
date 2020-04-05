package project.objects;

import java.util.LinkedList;

/**
 * Specific class that controls all administrative settings and still maintains user foundation
 */
public class RegisteredUser extends User {

    private Status status;
    private String password;
    private LinkedList<String> tickets;

    public RegisteredUser(String username, String password, Status status) {
        super(username);
        this.setStatus(status);
        this.setPassword(password);
    }

    /**
     * Adds the ticket in order to be printed out into a text document
     *
     * @param ticket
     */
    public void addTicket(String ticket) {
        tickets.add(ticket);
    }

    /**
     * Getters
     */
    public String getUsername() {
        return super.getUsername();
    }

    public Status getStatus() {
        return this.status;
    }

    public String getPassword() {
        return this.password;
    }

    /**
     * Setters
     */
    private void setStatus(Status status) {
        this.status = status;
    }

    private void setPassword(String password) {
        this.password = password;
    }


}
