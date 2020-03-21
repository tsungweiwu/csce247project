package project;

public class Movie extends Show {
	private int points;
	
	public Movie(Venue venue, String date, String title, Genre genre, String description, boolean explicit) {
		super(venue, date, title, genre, description, explicit);
		this.points = 1;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public String toString() {
		return super.toString() + "\nPoints: \t" + this.points;
	}
}