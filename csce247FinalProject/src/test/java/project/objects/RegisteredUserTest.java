package project.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegisteredUserTest {
	RegisteredUser ru;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ru = new RegisteredUser("username", "password", Status.STUDENT);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetUsername() {
		assertEquals("username", ru.getUsername());
	}

	@Test
	void testGetStatus() {
		assertEquals(Status.STUDENT, ru.getStatus());
	}

	@Test
	void testGetPassword() {
		assertEquals("password", ru.getPassword());
	}

}
