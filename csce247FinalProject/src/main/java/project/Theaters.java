package project;

public class Theaters {
	private int room;
	private boolean handicap;
	private int capacity;
	private String[][] seats;
	private Venue venue;

	public Theaters(int room, boolean handicap, Venue venue) {
		this.setRoom(room);
		this.setHandicap(handicap);
		this.setVenue(venue);
		initRoom();
	}

	public void initRoom() {
		this.seats = new String[10][10];
		this.setCapacity(this.seats.length);

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

	/**
	 * @param room the room to set
	 */
	public void setRoom(int room) {
		this.room = room;
	}

	/**
	 * @return the handicap
	 */
	public boolean isHandicap() {
		return handicap;
	}

	/**
	 * @param handicap the handicap to set
	 */
	public void setHandicap(boolean handicap) {
		this.handicap = handicap;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param seats the seats to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = 100;
	}

	/**
	 * @return the seats
	 */
	public String[][] getSeats() {
		return seats;
	}

	/**
	 * @param seats the seats to set
	 */
	public void setSeats(String[][] seats) {
		this.seats = seats;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public Venue getVenue() {
		return this.venue;
	}
}