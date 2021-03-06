package project.databases;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import project.objects.Event;
import project.objects.Genre;
import project.objects.Status;
import project.objects.Theater;
import project.objects.Type;
import project.objects.Venue;

/**
 * Holds all functionality to read and write Users to JSON files. Also manages the data within the
 * program
 */
public class VenueDatabase extends DataManager implements Database<Venue> {

    private ArrayList<Venue> venues = new ArrayList<>();
    private ReviewDatabase reviewDatabase;

    public VenueDatabase(ReviewDatabase reviewDatabase) {
        this.reviewDatabase = reviewDatabase;
        loadVenues();
    }

    /**
     * Getter
     */
    public ArrayList<Venue> getVenues() {
        return venues;
    }

    /**
     * Setter
     */
    public void setVenues(ArrayList<Venue> venues) {
        this.venues = venues;
    }

    /**
     * adds the theater into the venue and checks to see if it is the same venue to avoid
     * duplicating venues everytime we add a theater
     *
     * @param object
     */
    @Override
    public void add(Venue object) {
        if (!containsVenue(object)) {
            getVenues().add(object);
        } else {
            Venue existingVenue = findVenue(object);
            for (Theater theater : object.getTheaters()) {
                if (!existingVenue.containsTheater(theater)) {
                    existingVenue.addTheater(theater);
                } else {
                    Theater existingTheater = existingVenue.findTheater(theater);
                    for (Event event : theater.getEvents()) {
                        if (!existingTheater.containsEvent(event)) {
                            existingTheater.addEvent(event);
                        }
                    }
                    existingVenue.addTheater(existingTheater);
                }
            }
            getVenues().add(existingVenue);
        }
        saveVenues();
    }

    /**
     * removes the venue from the list and from the JSON
     *
     * @param object
     * @return
     */
    @Override
    public boolean remove(Venue object) {
        boolean check = getVenues().remove(object);
        if (check) {
            saveVenues();
        }
        return check;
    }

    /**
     * finds the venue to see if it exists and returns it
     *
     * @param venue
     * @return
     */
    public Venue findVenue(Venue venue) {
        for (Venue v : getVenues()) {
            if (v.equals(venue)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Checks to see if the venue already exists
     *
     * @param venue
     * @return boolean
     */
    public boolean containsVenue(Venue venue) {
        for (Venue v : getVenues()) {
            if (v.equals(venue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for the event info by the title and prints it all out
     *
     * @param title
     */
    public void search(String title) {
        for (Venue venue : getVenues()) {
            for (Theater theater : venue.getTheaters()) {
                for (Event event : theater.getEvents()) {
                    if (event.getTitle().equals(title)) {
                        printFormat(venue, theater, event);
                    }
                }
            }
        }
    }

    /**
     * filters the events by the genre
     *
     * @param genre
     */
    public void filterByGenre(Genre genre) {
        for (Venue venue : getVenues()) {
            for (Theater theater : venue.getTheaters()) {
                for (Event event : theater.getEvents()) {
                    if (event.getGenre() == genre) {
                        printFormat(venue, theater, event);
                    }
                }
            }
        }
    }

    /**
     * filters the events by the venue
     *
     * @param venueName
     */
    public void filterByVenue(String venueName) {
        for (Venue venue : getVenues()) {
            for (Theater theater : venue.getTheaters()) {
                for (Event event : theater.getEvents()) {
                    if (venue.getName().equalsIgnoreCase(venueName)) {
                        printFormat(venue, theater, event);
                    }
                }
            }
        }
    }

    /**
     * filters the events by the ratings
     *
     * @param rating
     */
    public void filterByRating(double rating) {
        for (Venue venue : getVenues()) {
            for (Theater theater : venue.getTheaters()) {
                for (Event event : theater.getEvents()) {
                    if (reviewDatabase.findEventReview(event).getRating() < getCeil(rating)
                        && reviewDatabase.findEventReview(event).getRating() >= Math
                        .floor(rating)) {
                        printFormat(venue, theater, event);
                    }
                }
            }
        }
    }

    /**
     * Filters the events by the type
     *
     * @param type
     */
    public void filterByEventType(Type type) {
        for (Venue venue : getVenues()) {
            for (Theater theater : venue.getTheaters()) {
                for (Event event : theater.getEvents()) {
                    if (event.getType() == type) {
                        printFormat(venue, theater, event);
                    }
                }
            }
        }
    }

    /**
     * Finds the ceiling of a number
     *
     * @param num
     * @return
     */
    public double getCeil(double num) {
        if (num == Math.ceil(num)) {
            return num + 1;
        }
        return Math.ceil(num);
    }

    /**
     * Searches for the event by title and retrieves all the related data and returns the event
     *
     * @param title
     * @return event
     */
    public Event getEvent(String title) {
        for (Venue venue : getVenues()) {
            for (Theater theater : venue.getTheaters()) {
                for (Event event : theater.getEvents()) {
                    if (event.getTitle().equalsIgnoreCase(title)) {
                        return event;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Formats the data in a neat form
     *
     * @param venue
     * @param theater
     * @param event
     */
    public void printFormat(Venue venue, Theater theater, Event event) {
        StringBuilder builder = new StringBuilder();
        builder.append("Venue Name: ").append(venue.getName()).append("\n");
        builder.append("Venue Location: ").append(venue.getLocation()).append("\n");
        builder.append("Theater Room: ").append(theater.getRoom()).append("\n");
        builder.append("Theater Handicap: ").append(theater.isHandicap()).append("\n");
        builder.append("Event Title: ").append(event.getTitle()).append("\n");
        builder.append("Event Date: ").append(event.getDate()).append("\n");
        builder.append("Event Description: ").append(event.getDescription()).append("\n");
        builder.append("Event Genre: ").append(event.getGenre().toString()).append("\n");
        builder.append("Event Price: $").append(event.getPrice()).append("\n");
        builder.append("Event Type: ").append(event.getType().toString()).append("\n");
        builder.append("Event Rating: ")
            .append(String.format("%.2f", reviewDatabase.findEventReview(event).getRating()))
            .append("\n");
        builder.append("Seats Left: ").append(event.getAvailable())
            .append("\n");
        System.out.println(builder.toString());
    }

    /**
     * Prints the Ticket
     *
     * @param venue
     * @param theater
     * @param event
     */
    public void printTicket(Venue venue, Theater theater, Event event, int quantity) {
        StringBuilder builder = new StringBuilder();
        builder.append("Type:\t").append(event.getType().toString()).append("\n");
        builder.append("Theater Room:\t").append(theater.getRoom()).append("\n");
        builder.append("Price:\t$").append(event.getPrice() * quantity).append("\n");
        builder.append("Date & Time:\t").append(event.getDate()).append("\n");
        builder.append("Title:\t").append(event.getTitle()).append("\n");
        builder.append("Genre:\t").append(event.getGenre().toString()).append("\n");
        builder.append("Venue:\t").append(venue.getName()).append("\t").append(venue.getLocation())
            .append("\n");
        builder.append("Event Rating: ")
            .append(String.format("%.2f", reviewDatabase.findEventReview(event).getRating()))
            .append("\n");
        System.out.println(builder.toString());
    }

    /**
     * Prints the Ticket with discounted price of users with status
     *
     * @param venue
     * @param theater
     * @param event
     */
    public void printTicket(Venue venue, Theater theater, Event event, Status status,
        int quantity) {
        double discount = discountPercent(status);
        StringBuilder builder = new StringBuilder();

        builder.append("Type:\t").append(event.getType().toString()).append("\n");
        builder.append("Theater Room:\t").append(theater.getRoom()).append("\n");
        builder.append("Original Price:\t$").append(event.getPrice() * quantity).append("\n");
        builder.append("Price with ").append(status).append(" discount:\t$")
            .append(String.format("%.2f", event.getPrice() * quantity * discount)).append("\n");
        builder.append("Date & Time:\t").append(event.getDate()).append("\n");
        builder.append("Title:\t").append(event.getTitle()).append("\n");
        builder.append("Genre:\t").append(event.getGenre().toString()).append("\n");
        builder.append("Venue:\t").append(venue.getName()).append("\t").append(venue.getLocation())
            .append("\n");
        builder.append("Event Rating: ")
            .append(String.format("%.2f", reviewDatabase.findEventReview(event).getRating()))
            .append("\n");
        System.out.println(builder.toString());

    }

    /**
     * Prints the receipt of the ticket
     *
     * @param venue
     * @param theater
     * @param event
     * @param line
     * @param col
     * @return
     */
    public String printReceipt(Venue venue, Theater theater, Event event, char[] line,
        LinkedList<Integer> col) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < line.length; i++) {
            builder.append("\n*************************************************\n");
            builder.append("Venue Name: ").append(venue.getName()).append("\n");
            builder.append("Location: ").append(venue.getLocation()).append("\n");
            builder.append("Room: ").append(theater.getRoom()).append(" - Seat: ").append(line[i])
                .append(col.get(i)).append("\n");
            builder.append("Title: ").append(event.getTitle()).append("\n");
            builder.append("Date: ").append(event.getDate()).append("\n");
            builder.append("Price: $").append(event.getPrice()).append("\n");
            builder.append("*************************************************\n");
        }

        return builder.toString();
    }

    /**
     * Prints receipt with the discounted price for users with status
     *
     * @param venue
     * @param theater
     * @param event
     * @param line
     * @param col
     * @param status
     * @return
     */
    public String printReceipt(Venue venue, Theater theater, Event event, char[] line,
        LinkedList<Integer> col,
        Status status) {
        double discount = discountPercent(status);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < line.length; i++) {
            builder.append("\n*************************************************\n");
            builder.append("Venue Name: ").append(venue.getName()).append("\n");
            builder.append("Location: ").append(venue.getLocation()).append("\n");
            builder.append("Room: ").append(theater.getRoom()).append(" - Seat: ").append(line[i])
                .append(col.get(i)).append("\n");
            builder.append("Title: ").append(event.getTitle()).append("\n");
            builder.append("Date: ").append(event.getDate()).append("\n");
            builder.append("Price: $").append(String.format("%.2f", event.getPrice() * discount))
                .append("\n");
            builder.append("*************************************************\n");
        }

        return builder.toString();
    }

    public double discountPercent(Status status) {
        if (status == Status.MILITARY || status == Status.EMPLOYEE) {
            return 0.85;
        } else if (status == Status.TEACHER) {
            return 0.90;
        } else if (status == Status.STUDENT || status == Status.SENIOR) {
            return 0.92;
        }
        return 1;
    }

    /**
     * Prints out all the events
     */
    public void printAll() {
        for (Venue venue : getVenues()) {
            for (Theater theater : venue.getTheaters()) {
                for (Event event : theater.getEvents()) {
                    printFormat(venue, theater, event);
                }
            }
        }
    }

    /**
     * Loads all the data from the JSON file
     */
    public void loadVenues() {
        if (!new File(VENUE_FILE_NAME).exists()) {
            try {
                Files.createDirectories(Paths.get(VENUE_FILE_NAME).getParent());
                Files.createFile(Paths.get(VENUE_FILE_NAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveVenues();
        }
        setVenues(read(VENUE_FILE_NAME, new TypeToken<ArrayList<Venue>>() {
        }.getType()));
    }

    /**
     * Saves all the data into the JSON file
     */
    public void saveVenues() {
        write(getVenues(), VENUE_FILE_NAME);
    }


}
