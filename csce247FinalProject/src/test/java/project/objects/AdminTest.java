package project.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUsername() {
        Admin admin = new Admin("username", "password");
        assertEquals("username", admin.getUsername());
    }

    @Test
    void getPassword() {
    }

    @Test
    void setPassword() {
    }
}