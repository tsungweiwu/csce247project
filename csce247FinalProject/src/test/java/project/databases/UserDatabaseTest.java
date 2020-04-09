package project.databases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.objects.Admin;
import project.objects.RegisteredUser;
import project.objects.Status;

/**
 * These are the main functions in the databases, the other ones are not implemented with test cases
 * because they only write to JSON files, and we should check the data before it is even written
 * into these JSON files. The JSON writing and reading are checked by exceptions already.
 */
class UserDatabaseTest {

    UserDatabase uDB;
    HashSet<RegisteredUser> users = new HashSet<>();
    HashSet<Admin> admins = new HashSet<>();

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        uDB = new UserDatabase();

        users = uDB.getUsers();
        admins = uDB.getAdmins();

        users.add(new RegisteredUser("testUser", "password", Status.NONE));
        admins.add(new Admin("testAdmin", "password"));
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    /**
     * Did not include many of the functions because of how redundant it would be to insert into the
     * JSON file and then remove it after testing it, especially when some of the functions require
     * you to check multiple instances of inputs to insert into the JSON file
     */

    @Test
    void testGetUsers() {
        assertNotNull(uDB.getUsers());
    }

    @Test
    void testGetAdmins() {
        assertNotNull(uDB.getAdmins());
    }

    @Test
    void testSetUsers() {
        uDB.setUsers(users);
        testGetUsers();
    }

    @Test
    void testSetAdmin() {
        uDB.setAdmin(admins);
        testGetAdmins();
    }

    @Test
    void testLoginUser() {
        assertTrue(uDB.login("testUser", "password", users, RegisteredUser.class));
    }

    @Test
    void testLoginFailUser() {
        assertFalse(uDB.login("NonExistingUser", "WrongPassword", users, RegisteredUser.class));
    }

    @Test
    void testLoginAdmin() {
        assertTrue(uDB.login("testAdmin", "password", admins, Admin.class));
    }

    @Test
    void testGetUser() {
        assertNotNull(uDB.getUser("testUser", users));
    }

    @Test
    void testIsUser() {
        assertTrue(uDB.isUser("testUser", users));
    }

}
