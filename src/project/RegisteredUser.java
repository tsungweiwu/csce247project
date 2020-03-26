package project;
/*
 * @author Juan Mariscal
 * Purpose: decorates User class
 */

import java.util.LinkedList;

public class RegisteredUser extends User{
	private Status status;
	private String password;
	private int freeTickets;
	private Reward reward;
	private LinkedList<String> tickets;
	
	public RegisteredUser(String username, String password, Status status) {
		super(username);
		this.setPassword(password);
		this.setStatus(status);
		reward = new Reward(this);
	}
	
	public void rentTheater() {
		//TODO ask how to implement this
	}
	
	public void updateRewardSystem() {
		//TODO update based on show points
	}
	
	public void addTicket(String ticket) {
		tickets.add(ticket);
	}
	
	private void setPassword(String password) {
		this.password = password;
	}
	
	private void setStatus(Status status) {
		this.status = status;
	}
	public Status getStatus() {
		return this.status;
	}
	
	
	
	
	
	
}
