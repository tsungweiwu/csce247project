package project.databases;

import java.io.File;

public abstract class DataConstants {

    protected static final String USER_FILE_NAME =
        System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
            + File.separator + "resources" + File.separator + "users.json";
    protected static final String ADMIN_FILE_NAME =
        System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
            + File.separator + "resources" + File.separator + "admin.json";
    protected static final String VENUE_FILE_NAME =
        System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
            + File.separator + "resources" + File.separator + "venues.json";
    protected static final String REVIEW_FILE_NAME =
        System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
            + File.separator + "resources" + File.separator + "reviews.json";

}