package project;

import java.util.LinkedList;

/**
 * Event class describes the information that an event holds
 */

public class Event {

    private Theater theater;
    private String date;
    private String title;
    private Genre genre;
    private String description;
    private boolean explicit;
    private Type type;
    private double price;
    private LinkedList<Review> reviews;

    /**
     * @param theater
     * @param date
     * @param title
     * @param genre
     * @param description
     * @param explicit
     * @param type
     * @param price
     */
    public Event(Theater theater, String date, String title, Genre genre, String description,
        boolean explicit, Type type, double price) {
        this.setTheater(theater);
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
     * @return
     */
    public double getRating() {
        int total = reviews.size();
        int sum = 0;

        while (reviews.peek() != null) {
            sum += reviews.pop().getRating();
        }

        return ((double) sum / (double) total);

    }

    /**
     * Getters
     */
    public Theater getTheater() {
        return theater;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public Type getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public LinkedList<Review> getReviews() {
        return reviews;
    }

    public Venue getVenue() {
        return theater.getVenue();
    }

    /**
     * Setters
     */
    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            this.price = 10.00;
        }
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    /**
     * Prints out the menu for the display of events
     */
    public String toString() {
        return "Type: \t" + this.type + "\nPrice: \t$" + this.price + "\nDate & Time: \t"
            + this.date + "\nTitle: \t" + this.title + "\nGenre: \t" + this.genre
            + "\nDescription: \t" + this.description + "\nVenue: \t"
            + this.theater.getVenue().getName() + ", " + this.theater.getVenue().getLocation()
            + "\nExplicit: \t" + (this.explicit ? "Yes"
            : "No")
            + "\nRatings: \t" + this.getRating() + "\n";
    }

    /**
     * Prints out the ticket to be exported into a text file
     */
    public String ticketString() {
        return "*********************************************************************"

            + "\n*********************************************************************";
    }
}