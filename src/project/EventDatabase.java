import java.util.LinkedList;

public class EventDatabase implements Database<Event>{
	private LinkedList<Event> eventList;
	
	public EventDatabase() {
		eventList = new LinkedList<Event>();
	}
	
	public void add(Event event) {
		eventList.add(event);
	}

	public boolean remove(Event event) {
		return eventList.remove(event);
	}
	
	public void search(String title) {
		for(Event e: eventList) {
			if(e.getTitle().contains(title))
				System.out.println(e);
		}
	}
	public void filterByGenre(Genre genre) {
		for(Event e: eventList) {
			if(e.getGenre().equals(genre))
				System.out.println(e);
		}
	}
	
	public void filterByVenue(String venueName) {
		for(Event e: eventList)
			if(e.getVenue().getName().equals(venueName))
				System.out.println(e);
	}
	
	public void filterByRating(int rating) {
		for(Event e: eventList) {
			if(e.getRating() == rating)
				System.out.println(e);
		}
	}
	public Event get(String title) {
		Event event = null;
		for(Event e: eventList)
			if(e.getTitle().equals(title))
				event = e;
		return event;
	}
	
	public void filterByEventType(Type type) {
		for(Event e: eventList) {
			if(e.getType().equals(type))
				System.out.println(e);
		}
	}
	
	public void printAll() {
		for(Event e: eventList)
			System.out.println(e);
	}
}
