package project;

/**
 * Defines the address of the event
 */
public class Venue {

    private String location;
    private String name;

    public Venue(String location, String name) {
        this.setLocation(location);
        this.setName(name);
    }

    /**
     * Getters
     */
    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
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

}
