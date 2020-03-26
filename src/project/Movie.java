package project;

public class Movie extends Show {
	private static final int POINT = 1; 
	
	public Movie(Venue venue, String date, String title, Genre genre, String description, boolean explicit) {
		super(venue, date, title, genre, description, explicit);
		this.points = POINT;
	}
	
	public String toString() {
		return super.toString() + "\nPoints: \t" + this.points;
	}
}