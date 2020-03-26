package project;

import java.util.LinkedList;

public abstract class Show extends Event{
	private String title;
	private Genre genre;
	private String description;
	private boolean explicit;
	protected int points;
	private LinkedList<Review> reviews;
	
	/**
	 * 
	 * @param venue
	 * @param date
	 * @param title
	 * @param genre
	 * @param description
	 * @param explicit
	 */
	public Show(Venue venue, String date, String title, Genre genre, String description, boolean explicit) {
		super(venue, date);
		this.title = title;
		this.genre = genre;
		this.description = description;
		this.explicit = explicit;
		this.reviews = new LinkedList<Review>();
	}
	
	/**
	 * 
	 */
	public void viewReviews() {
		System.out.println(reviews.toString());
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRating() {
		int total = reviews.size();
		int sum = 0;
		
		while(reviews.peek() != null) {
			sum += reviews.pop().getRating();
		}
		
		return sum/total;
		
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public String toString() {
		return super.toString() + "\nTitle: \t" + this.title + "\nGenre: \t" + this.genre + "\nDescription: \t" + this.description + "Explicit: \t" + (this.explicit ? "Yes" : "No") + "\nRatings: \t" + this.getRating();
	}
}
