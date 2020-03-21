package project;

/**
 * Event class
 */

public class Event {
	private Venue venue;
	private String date;
	
	
	/**
	 * 
	 * @param show
	 * @param venue
	 * @param date
	 */
	public Event(Venue venue, String date) {
		this.venue = venue;
		this.date = date;
	}
	
	/**
	 * 
	 */
	public String toString() {
		return "Date & Time: /t" + this.date + "\nVenue: /t" + this.venue;
	}
}