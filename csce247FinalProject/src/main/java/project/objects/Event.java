package project.objects;

import java.util.LinkedList;

/**
 * Event class describes the information that an event holds
 */

public class Event {

    private String date;
    private String title;
    private Genre genre;
    private String description;
    private boolean explicit;
    private Type type;
    private double price;
    private String seats[][] = new String[10][10];

    /**
     * @param date
     * @param title
     * @param genre
     * @param description
     * @param explicit
     * @param type
     * @param price
     */
    public Event(String date, String title, Genre genre, String description,
        boolean explicit, Type type, double price) {
        this.setDate(date);
        this.setTitle(title);
        this.setGenre(genre);
        this.setDescription(description);
        this.setExplicit(explicit);
        this.setType(type);
        this.setPrice(price);
        this.initRoom();
    }

    /**
     * Initializes the seating chart (Double Matrices)
     */
    public void initRoom() {
        this.seats = new String[10][10];

        for (int i = 0; i < this.seats.length; i++) {
            for (int j = 0; j < this.seats[i].length; j++) {
                this.seats[i][j] = "O";
            }
        }
    }

    /**
     * Getters
     */

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

    public String[][] getSeats() {
        return seats;
    }


    /**
     * Setters
     */

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

    public void setSeats(int x, int y) {
        this.seats[y][x] = "X";
    }

//    /**
//     * Prints out the menu for the display of events
//     */
//    public String toString() {
//        return "Type: \t" + this.type + "\nPrice: \t$" + this.price + "\nDate & Time: \t"
//            + this.date + "\nTitle: \t" + this.title + "\nGenre: \t" + this.genre
//            + "\nDescription: \t" + this.description + "\nVenue: \t"
//            + "\nExplicit: \t" + (this.explicit ? "Yes"
//            : "No")
//            + "\nRatings: \t" + this.getRating() + "\n";
//    }

    public String ticketString() {
        return "*********************************************************************"

            + "\n*********************************************************************";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Event event = (Event) obj;
        return event.getTitle().equalsIgnoreCase(getTitle()) &&
            event.getGenre() == getGenre() && event.getType() == getType() &&
            event.getDate().equalsIgnoreCase(getDate()) && event.getDescription()
            .equalsIgnoreCase(getDescription())
            && event.getPrice() == getPrice();
    }
}