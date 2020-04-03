import java.util.LinkedList;

/**
 * Event class
 */

public class Event {
	private Theaters theater;
	private String date;
	private String title;
	private Genre genre;
	private String description;
	private boolean explicit;
	private Type type;
	private double price;
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
	public Event(Theaters theater, String date, String title, Genre genre, String description, boolean explicit, Type type, double price) {
		this.setTheaters(theater);
		this.setDate(date);
		this.setTitle(title);
		this.setGenre(genre);
		this.setDescription(description);
		this.setExplicit(explicit);
		this.setType(type);
		this.setPrice(price);
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

		while (reviews.peek() != null) {
			sum += reviews.pop().getRating();
		}

		return sum / total;

	}

	/**
	 * @return the venue
	 */
	public Theaters getTheater() {
		return theater;
	}

	/**
	 * @param venue the venue to set
	 */
	public void setTheaters(Theaters theater) {
		this.theater = theater;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the genre
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the explicit
	 */
	public boolean isExplicit() {
		return explicit;
	}

	/**
	 * @param explicit the explicit to set
	 */
	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}
	
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * @return the type
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param type the type to set
	 */
	public void setPrice(double price) {
		if(price >= 0) {
			this.price = price;
		} else {
			price = 0.00;
		}
	}

	/**
	 * @return the reviews
	 */
	public LinkedList<Review> getReviews() {
		return reviews;
	}

	/**
	 * @param review the reviews to set
	 */
	public void addReview(Review review) {
		reviews.add(review);
	}
	public Venue getVenue() {
		return theater.getVenue();
	}
	/**
	 * 
	 */
	public String toString() {
		return "Type: \t" + this.type + "\nPrice: \t$" + this.price + "\nDate & Time: /t" + this.date + "\nVenue: /t" + this.theater.getVenue() + "\nTitle: \t" + this.title + "\nGenre: \t"
				+ this.genre + "\nDescription: \t" + this.description + "Explicit: \t" + (this.explicit ? "Yes" : "No")
				+ "\nRatings: \t" + this.getRating();
	}
	
	public String ticketString() {
		return "*********************************************************************"
				
				+ "\n*********************************************************************";
	}
}