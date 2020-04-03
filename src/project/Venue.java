package project;

import java.util.LinkedList;

public class Venue {
	private String location;
	private String name;
	private LinkedList<Theaters> theaters;

	public Venue(String location, String name) {
		this.setLocation(location);
		this.setName(name);
		this.theaters = new LinkedList<Theaters>();
	}

	public void addTheater(Theaters theater) {
		this.theaters.add(theater);
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the theaters
	 */
	public LinkedList<Theaters> getTheaters() {
		return theaters;
	}

	/**
	 * @param theaters the theaters to set
	 */
	public void setTheaters(LinkedList<Theaters> theaters) {
		this.theaters = theaters;
	}
}
