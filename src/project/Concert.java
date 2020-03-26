package project;

public class Concert extends Show {
	private static final int POINT = 2; 
	
	public Concert(Venue venue, String date, String title, Genre genre, String description, boolean explicit) {
		super(venue, date, title, genre, description, explicit);
		this.points = POINT;
	}
	
	public String toString() {
		return super.toString() + "\nPoints: \t" + this.points;
	}
}