package project.databases;


import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import project.objects.Admin;
import project.objects.RegisteredUser;
import project.objects.Status;
import project.objects.User;

/**
 * Holds all functionality to read and write Users to JSON files. Also manages the data within the
 * program
 */
public class UserDatabase extends DataManager implements Database<User> {

    private HashSet<RegisteredUser> users = new HashSet<>();
    private HashSet<Admin> admins = new HashSet<>();

    public UserDatabase() {
        loadUsers();
        loadAdmins();
    }

    /**
     * Getters
     */
    public HashSet<RegisteredUser> getUsers() {
        return users;
    }

    public HashSet<Admin> getAdmins() {
        return admins;
    }

    /**
     * Setters
     */
    public void setUsers(HashSet<RegisteredUser> users) {
        this.users = users;
    }

    public void setAdmin(HashSet<Admin> admins) {
        this.admins = admins;
    }


    public void addUser(String username, String password, Status status) {
        getUsers().add(new RegisteredUser(username, password, status));
        saveUsers();
    }

    public void addAdmin(String username, String password) {
        getAdmins().add(new Admin(username, password));
        saveAdmins();
    }

    public void add(User user) {
        if (user instanceof RegisteredUser) {
            getUsers().add((RegisteredUser) user);
            saveUsers();
        } else if (user instanceof Admin) {
            getAdmins().add((Admin) user);
            saveAdmins();
        }
    }


    public boolean remove(User user) {
        if (user instanceof RegisteredUser) {
            getUsers().remove((RegisteredUser) user);
            saveUsers();
        } else if (user instanceof Admin) {
            getAdmins().remove((Admin) user);
            saveAdmins();
        }
        return false;
    }

    public User get(String username) {
        for (RegisteredUser user : getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        for (Admin user : getAdmins()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean hasUser(String username) {
        for (RegisteredUser user : getUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        for (Admin user : getAdmins()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void loadUsers() {
        if (!new File(USER_FILE_NAME).exists()) {
            try {
                Files.createDirectories(Paths.get(USER_FILE_NAME).getParent());
                Files.createFile(Paths.get(USER_FILE_NAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveUsers();
        }
        setUsers(read(USER_FILE_NAME, new TypeToken<HashSet<RegisteredUser>>() {
        }.getType()));
    }

    public void loadAdmins() {
        if (!new File(ADMIN_FILE_NAME).exists()) {
            try {
                Files.createDirectories(Paths.get(ADMIN_FILE_NAME).getParent());
                Files.createFile(Paths.get(ADMIN_FILE_NAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveAdmins();
        }
        setAdmin(read(ADMIN_FILE_NAME, new TypeToken<HashSet<Admin>>() {
        }.getType()));
    }

    public boolean login(String username, String password, HashSet<? extends User> hashSet,
        Class<? extends User> clazz) {
        if (clazz == RegisteredUser.class) {
            for (User user : hashSet) {
                RegisteredUser castedUser = (RegisteredUser) user;
                if (castedUser.getUsername().equals(username) && castedUser.getPassword()
                    .equalsIgnoreCase(password)) {
                    return true;
                }
            }
        } else if (clazz == Admin.class) {
            for (User user : hashSet) {
                Admin castedUser = (Admin) user;
                if (castedUser.getUsername().equals(username) && castedUser.getPassword()
                    .equalsIgnoreCase(password)) {
                    return true;
                }
            }
        } else {
            System.out.println("Invalid class");
        }
        return false;
    }

    public User getUser(String username, HashSet<? extends User> hashSet) {
        for (User user : hashSet) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUser(String username, HashSet<? extends User> hashSet) {
        for (User user : hashSet) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Saves all the data into the JSON file
     */
    public void saveUsers() {
        write(getUsers(), USER_FILE_NAME);
    }

    public void saveAdmins() {
        write(getAdmins(), ADMIN_FILE_NAME);
    }


}
