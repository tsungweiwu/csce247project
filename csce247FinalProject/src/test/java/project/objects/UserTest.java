package project.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    User user;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        user = new User("username");
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetUsername() {
        assertEquals("username", user.getUsername());
    }

    @Test
    void testSetUsername() {
        user.setUsername("default");
        assertEquals("default", user.getUsername());
    }

}
