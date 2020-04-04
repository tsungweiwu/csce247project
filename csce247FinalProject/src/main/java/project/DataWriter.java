package project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Writes the data into JSON file
 */
public class DataWriter extends DataConstants {

    /**
     * Gets an instance of users, retrieves the users, saves the user into a string and writes it
     * into file
     */
    public static void saveUsers() {
        UserDatabase users = UserDatabase.getInstance();
        ArrayList<RegisteredUser> regList = users.getUsers();
        String input = new Gson().toJson(regList);

        // Write JSON file
        try (FileWriter file = new FileWriter(USER_FILE_NAME)) {
            file.write(input);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets an instance of user, retrieves the admin, saves the admin into a string and writes it
     * into file
     */
    public static void saveAdmin() {
        UserDatabase admin = UserDatabase.getInstance();
        ArrayList<Admin> adminList = admin.getAdmin();
        String input = new Gson().toJson(adminList);

        // Write JSON file
        try (FileWriter file = new FileWriter(ADMIN_FILE_NAME)) {
            file.write(input);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets an instance of event, retrieves the events, saves the events into a string and writes it
     * into file
     */
    public static void saveEvent() {
        EventDatabase event = EventDatabase.getInstance();
        ArrayList<Event> eventList = event.getEvent();
        String input = new Gson().toJson(eventList);

        // Write JSON file
        try (FileWriter file = new FileWriter(EVENT_FILE_NAME)) {
            file.write(input);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
