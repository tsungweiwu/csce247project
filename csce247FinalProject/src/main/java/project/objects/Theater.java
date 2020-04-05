package project.objects;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Defines the place the event is holding place in
 */
public class Theater {

    private int room;
    private boolean handicap;
    private ArrayList<Event> events;

    public Theater(int room, boolean handicap) {
        this.setRoom(room);
        this.setHandicap(handicap);
        this.setEvents(new ArrayList<>());
    }


    /**
     * Getters
     */
    public int getRoom() {
        return room;
    }

    public boolean isHandicap() {
        return handicap;
    }


    public ArrayList<Event> getEvents() {
        return events;
    }


    /**
     * Setters
     */
    public void setRoom(int room) {
        if (room >= 0) {
            this.room = room;
        }
    }

    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }


    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    /**
     * Checks to see if it contains certain event
     *
     * @param event
     * @return
     */
    public boolean containsEvent(Event event) {
        for (Event e : getEvents()) {
            if (e.equals(event)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param event
     */
    public void addEvent(Event event) {
        getEvents().add(event);
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Theater theater = (Theater) obj;
        return room == theater.room && handicap == theater.handicap;
    }

    /**
     * @param event
     * @return
     */
    public Event findEvent(Event event) {
        for (Event event1 : getEvents()) {
            if (event.equals(event1)) {
                return event;
            }
        }
        return null;
    }

}
