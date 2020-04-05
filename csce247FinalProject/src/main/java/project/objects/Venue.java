package project.objects;

/**
 * Defines the address of the event
 */

import java.util.ArrayList;

public class Venue {

    private String location;
    private String name;
    private ArrayList<Theater> theaters;

    public Venue(String location, String name) {
        this.setLocation(location);
        this.setName(name);
        this.theaters = new ArrayList<>();
    }

    /**
     * Getter
     */
    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Theater> getTheaters() {
        return theaters;
    }

    /**
     * Setters
     */
    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTheaters(ArrayList<Theater> theaters) {
        this.theaters = theaters;
    }

    public void addTheater(Theater theater) {
        getTheaters().add(theater);
    }

    /**
     * searches for the theater
     *
     * @param theater
     * @return boolean
     */
    public boolean containsTheater(Theater theater) {
        for (Theater t : theaters) {
            if (t.equals(theater)) {
                return true;
            }
        }
        return false;
    }

    /**
     * searches for the specific theater to return
     *
     * @param theater
     * @return theater
     */
    public Theater findTheater(Theater theater) {
        for (Theater t : getTheaters()) {
            if (t.equals(theater)) {
                return t;
            }
        }
        return null;
    }

    /**
     * finds the room that is in the theater
     *
     * @param room
     * @return theater
     */
    public Theater findByRoom(int room) {
        for (Theater theater : getTheaters()) {
            if (theater.getRoom() == room) {
                return theater;
            }
        }
        return null;
    }

    /**
     * finds the theater
     *
     * @param isHandicap
     * @return theater
     */
    public Theater findByHandicap(boolean isHandicap) {
        for (Theater theater : getTheaters()) {
            if (theater.isHandicap() == isHandicap) {
                return theater;
            }
        }
        return null;
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
        Venue venue = (Venue) obj;
        return venue.getName().equalsIgnoreCase(getName()) && venue.getLocation()
            .equalsIgnoreCase(getLocation());
    }
}
