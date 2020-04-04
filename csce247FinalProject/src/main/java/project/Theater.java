package project;

/**
 * Defines the place the event is holding place in
 */
public class Theater {

    private int room;
    private boolean handicap;
    private String[][] seats;
    private Venue venue;

    public Theater(int room, boolean handicap, Venue venue) {
        this.setRoom(room);
        this.setHandicap(handicap);
        this.setVenue(venue);
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
     * @return the room
     */
    public int getRoom() {
        return room;
    }

    public boolean isHandicap() {
        return handicap;
    }

    public String[][] getSeats() {
        return seats;
    }

    public Venue getVenue() {
        return this.venue;
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

    public void setSeats(String[][] seats) {
        this.seats = seats;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }


}
