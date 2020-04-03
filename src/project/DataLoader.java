package project;

import java.io.FileReader;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;;

public class DataLoader extends DataConstants {

	public static ArrayList<RegisteredUser> loadUsers() {
		ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();

		try {
			FileReader reader = new FileReader(USER_FILE_NAME);
			JSONParser parser = new JSONParser();
			JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);

			for (int i = 0; i < usersJSON.size(); i++) {
				JSONObject userJSON = (JSONObject) usersJSON.get(i);
				String username = (String) userJSON.get(USERNAME);
				String password = (String) userJSON.get(PASSWORD);
				Status status = (Status) userJSON.get(STATUS);

				users.add(new RegisteredUser(username, password, status));
			}

			return users;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static ArrayList<Admin> loadAdmin() {
		ArrayList<Admin> admin = new ArrayList<Admin>();

		try {
			FileReader reader = new FileReader(ADMIN_FILE_NAME);
			JSONParser parser = new JSONParser();
			JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);

			for (int i = 0; i < usersJSON.size(); i++) {
				JSONObject userJSON = (JSONObject) usersJSON.get(i);
				String username = (String) userJSON.get(USERNAME);
				String password = (String) userJSON.get(PASSWORD);

				admin.add(new Admin(username, password));
			}

			return admin;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static ArrayList<Event> loadEvent() {
		ArrayList<Event> event = new ArrayList<Event>();

		try {
			FileReader reader = new FileReader(EVENT_FILE_NAME);
			JSONParser parser = new JSONParser();
			JSONArray eventsJSON = (JSONArray) new JSONParser().parse(reader);

			for (int i = 0; i < eventsJSON.size(); i++) {
				JSONObject eventJSON = (JSONObject) eventsJSON.get(i);
				Venue venue = (Venue) eventJSON.get(VENUE);
				String date = (String) eventJSON.get(DATE);
				String title = (String) eventJSON.get(TITLE);
				Genre genre = (Genre) eventJSON.get(GENRE);
				String description = (String) eventJSON.get(DESCRIPTION);
				boolean explicit = (boolean) eventJSON.get(EXPLICIT);
				Type type = (Type) eventJSON.get(TYPE);

				event.add(new Event(venue, date, title, genre, description, explicit, type));
			}

			return event;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
