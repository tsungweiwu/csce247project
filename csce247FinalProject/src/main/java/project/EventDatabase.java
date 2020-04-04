package project;

import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Holds all functionality to read and write Users to JSON files. Also manages the data within the
 * program
 */
public class EventDatabase implements Database<Event> {

    private LinkedList<Event> eventList;
    private static EventDatabase eventDatabase = null;
    private ArrayList<Event> events;

    public EventDatabase() {
        eventList = new LinkedList<Event>();
        events = DataLoader.loadEvent();
    }

    /**
     * Creates a new instance of EventDatabase in case it is null
     *
     * @return eventDatabase
     */
    public static EventDatabase getInstance() {
        if (eventDatabase == null) {
            eventDatabase = new EventDatabase();
            eventDatabase.setEvents(DataLoader.loadEvent());
        }
        return eventDatabase;
    }

    /**
     * Retrieves the events for the loader
     *
     * @return events
     */
    public ArrayList<Event> getEvent() {
        return events;
    }

    /**
     * Retrieves the events from loader and assigns it to events
     *
     * @param events
     */
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    /**
     * Adds an event to be put into the JSON file. Both adds events, difference is the parameters
     * and how the data is passed in from the driver
     *
     * @param theater
     * @param date
     * @param title
     * @param genre
     * @param description
     * @param explicit
     * @param type
     * @param price
     */
    public void addEvent(Theater theater, String date, String title, Genre genre,
        String description, boolean explicit, Type type, double price) {
        events.add(new Event(theater, date, title, genre, description, explicit, type, price));
        DataWriter.saveEvent();
    }

    /**
     * Adds an event to be put into the JSON file
     *
     * @param event
     */
    public void addEvent(Event event) {
        events.add(event);
        DataWriter.saveEvent();
    }

    /**
     * Adds an event into list within the program
     *
     * @param event
     */
    public void add(Event event) {
        eventList.add(event);
    }

    /**
     * Adds an event into list within the program
     *
     * @param event
     */
    public boolean remove(Event event) {
        return eventList.remove(event);
    }

    /**
     * Searches an event by its title
     *
     * @param title
     */
    public void search(String title) {
        for (Event e : eventList) {
            if (e.getTitle().contains(title)) {
                System.out.println(e);
            }
        }
    }

    /**
     * filters all the events by genre
     *
     * @param genre
     */
    public void filterByGenre(Genre genre) {
        for (Event e : eventList) {
            if (e.getGenre().equals(genre)) {
                System.out.println(e);
            }
        }
    }

    /**
     * filters all the events by the venue
     *
     * @param venueName
     */
    public void filterByVenue(String venueName) {
        for (Event e : eventList) {
            if (e.getVenue().getName().equals(venueName)) {
                System.out.println(e);
            }
        }
    }

    /**
     * filters all the events by the ratings
     *
     * @param rating
     */
    public void filterByRating(int rating) {
        for (Event e : eventList) {
            if (e.getRating() == rating) {
                System.out.println(e);
            }
        }
    }

    /**
     * filters all the events by the event type
     *
     * @param type
     */
    public void filterByEventType(Type type) {
        for (Event e : eventList) {
            if (e.getType().equals(type)) {
                System.out.println(e);
            }
        }
    }

    /**
     * returns all the events by the title
     *
     * @param title
     * @return event
     */
    public Event get(String title) {
        Event event = null;
        for (Event e : eventList) {
            if (e.getTitle().equals(title)) {
                event = e;
            }
        }
        return event;
    }


    /**
     * Prints out all the events
     */
    public void printAll() {
        for (Event e : events) {
            System.out.println(e);
        }
    }
}
