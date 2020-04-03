/**
 * 
 */
package project;

import java.util.ArrayList;
/**
 *
 */
public class EventDatabase {
	private static EventDatabase eventDatabase = null;
	private static ArrayList<Event> events = new ArrayList<Event>();
	
	private EventDatabase() {
		events = DataLoader.loadEvent();
	}
	
	public static EventDatabase getInstance() {
		if(eventDatabase == null) {
			eventDatabase = new EventDatabase();
		}
		return eventDatabase;
	}
	
	public ArrayList<Event> getEvent() {
		return events;
	}
	
	
	public void addEvent(Venue venue, String date, String title, Genre genre, String description, boolean explicit, Type type) {
		events.add(new Event(venue, date, title, genre, description, explicit, type));
		DataWriter.saveEvent();
	}
	
}