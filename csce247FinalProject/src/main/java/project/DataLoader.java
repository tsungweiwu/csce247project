package project;

import java.io.FileReader;
import java.util.ArrayList;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;;

public class DataLoader extends DataConstants {

	public static ArrayList<RegisteredUser> loadUsers() {
		ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();

		try {
			FileReader reader = new FileReader(USER_FILE_NAME);
//			JSONParser parser = new JSONParser();
//			JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);
//
//			for (int i = 0; i < usersJSON.size(); i++) {
//				JSONObject userJSON = (JSONObject) usersJSON.get(i);
//				String username = (String) userJSON.get(USERNAME);
//				String password = (String) userJSON.get(PASSWORD);
//				String status = (String) userJSON.get(STATUS);
//				
//				Status stat2 = parseEnum(status, Status.class);
//
//				users.add(new RegisteredUser(username, password, stat2));
//			}
			users = new Gson().fromJson(reader, new TypeToken<ArrayList<RegisteredUser>>(){}.getType());

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
//			JSONParser parser = new JSONParser();
//			JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);
//
//			for (int i = 0; i < usersJSON.size(); i++) {
//				JSONObject userJSON = (JSONObject) usersJSON.get(i);
//				String username = (String) userJSON.get(USERNAME);
//				String password = (String) userJSON.get(PASSWORD);
//
//				admin.add(new Admin(username, password));
//			}

			admin = new Gson().fromJson(reader, new TypeToken<ArrayList<Admin>>(){}.getType());
			
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
		event = new Gson().fromJson(reader, new TypeToken<ArrayList<Event>>(){}.getType());
		
		return event;
		
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			FileReader reader = new FileReader(EVENT_FILE_NAME);
//			JSONParser parser = new JSONParser();
//			JSONArray eventsJSON = (JSONArray) new JSONParser().parse(reader);
//
//			for (int i = 0; i < eventsJSON.size(); i++) {
//				JSONObject eventJSON = (JSONObject) eventsJSON.get(i);
//				Theaters theater = (Theaters) eventJSON.get(THEATER);
//				String date = (String) eventJSON.get(DATE);
//				String title = (String) eventJSON.get(TITLE);
//				String genre = (String) eventJSON.get(GENRE);
//				String description = (String) eventJSON.get(DESCRIPTION);
//				boolean explicit = (boolean) eventJSON.get(EXPLICIT);
//				String type = (String) eventJSON.get(TYPE);
//				double price = (double) eventJSON.get(PRICE);
//				
//				Genre genre2 = parseEnum(genre, Genre.class);
//				Type type2 = parseEnum(type, Type.class);
//
//				event.add(new Event(theater, date, title, genre2, description, explicit, type2, price));
//			}
//
//			return event;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
		return null;
	}
	
//	public static <E extends Enum<E>> E parseEnum(String data, Class<E> enumClass){
//        for (Enum<E> enumVal: enumClass.getEnumConstants()){
//            if (enumVal.name().equals(data.trim())){
//                return (E) enumVal;
//            }
//        }
//        return null;
//    }
}
