package project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

	public static void saveUsers() {
		UserDatabase users = UserDatabase.getInstance();
		ArrayList<RegisteredUser> friends = users.getUsers();
		JSONArray jsonFriends = new JSONArray();

		// creating all the json objects
		for (int i = 0; i < friends.size(); i++) {
			jsonFriends.add(getUserJSON(friends.get(i)));
		}

		// Write JSON file
		try (FileWriter file = new FileWriter(USER_FILE_NAME)) {

			file.write(jsonFriends.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject getUserJSON(RegisteredUser user) {
		JSONObject userDetails = new JSONObject();
		userDetails.put(USERNAME, user.getUsername());
		userDetails.put(PASSWORD, user.getPassword());
		userDetails.put(STATUS, user.getStatus());

		return userDetails;
	}
	
	
	public static void saveAdmin() {
		UserDatabase admin = UserDatabase.getInstance();
		ArrayList<Admin> friends = admin.getAdmin();
		JSONArray jsonFriends = new JSONArray();

		// creating all the json objects
		for (int i = 0; i < friends.size(); i++) {
			jsonFriends.add(getAdminJSON(friends.get(i)));
		}

		// Write JSON file
		try (FileWriter file = new FileWriter(ADMIN_FILE_NAME)) {

			file.write(jsonFriends.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject getAdminJSON(Admin admin) {
		JSONObject userDetails = new JSONObject();
		userDetails.put(USERNAME, admin.getUsername());
		userDetails.put(PASSWORD, admin.getPassword());

		return userDetails;
	}
	
	public static void saveEvent() {
		EventDatabase event = EventDatabase.getInstance();
		ArrayList<Event> friends = event.getEvent();
		JSONArray jsonFriends = new JSONArray();

		// creating all the json objects
		for (int i = 0; i < friends.size(); i++) {
			jsonFriends.add(getEventJSON(friends.get(i)));
		}

		// Write JSON file
		try (FileWriter file = new FileWriter(EVENT_FILE_NAME)) {

			file.write(jsonFriends.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject getEventJSON(Event event) {
		JSONObject eventDetails = new JSONObject();
		eventDetails.put(VENUE, event.getVenue());
		eventDetails.put(DATE, event.getDate());
		eventDetails.put(TITLE, event.getTitle());
		eventDetails.put(GENRE, event.getGenre());
		eventDetails.put(DESCRIPTION, event.getDescription());
		eventDetails.put(EXPLICIT, event.isExplicit());
		eventDetails.put(TYPE, event.getType());

		return eventDetails;
	}
}
