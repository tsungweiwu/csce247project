package project;

import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;;

/**
 * Reads the JSON files and processes the data
 */
public class DataLoader extends DataConstants {

    /**
     * loads the users from the JSON file
     *
     * @return users
     */
    public static ArrayList<RegisteredUser> loadUsers() {
        ArrayList<RegisteredUser> users;

        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            users = new Gson().fromJson(reader, new TypeToken<ArrayList<RegisteredUser>>() {
            }.getType());

            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * loads the admin from the JSON file
     *
     * @return admin
     */
    public static ArrayList<Admin> loadAdmin() {
        ArrayList<Admin> admin;

        try {
            FileReader reader = new FileReader(ADMIN_FILE_NAME);
            admin = new Gson().fromJson(reader, new TypeToken<ArrayList<Admin>>() {
            }.getType());

            return admin;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * loads the event from the JSON file
     *
     * @return event
     */
    public static ArrayList<Event> loadEvent() {
        ArrayList<Event> event;
        try {
            FileReader reader = new FileReader(EVENT_FILE_NAME);
            event = new Gson().fromJson(reader, new TypeToken<ArrayList<Event>>() {
            }.getType());

            return event;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
