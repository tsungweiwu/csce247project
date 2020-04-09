package project.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminTest {
	
	Admin admin;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		admin = new Admin("username", "password");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetUsername() {
        assertEquals("username", admin.getUsername(), "Username should be username");
	}

	@Test
	void testGetPassword() {
		assertEquals("password", admin.getPassword(), "Password should be password");
	}

	/**
	 * Tests if newly assigned password matches
	 */
	@Test
	void testSetPassword() {
		admin.setPassword("newPassword");
		assertEquals("newPassword", admin.getPassword(), "Password updated to newPassword");
	}

}
