package project.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VenueTest {
	Venue venue;
	Theater theater;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		venue = new Venue("location", "name");
		theater = new Theater(1, true);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetLocation() {
		assertEquals("location", venue.getLocation());
	}

	@Test
	void testGetName() {
		assertEquals("name", venue.getName());
	}

	@Test
	void testSetLocation() {
		venue.setLocation("nowhere");
		assertEquals("nowhere", venue.getLocation());
	}

	@Test
	void testSetName() {
		venue.setName("secondname");
		assertEquals("secondname", venue.getName());
	}

	@Test
	void testContainsTheater() {
		venue.addTheater(theater);
		assertEquals(true, venue.containsTheater(theater));
	}

	@Test
	void testFindTheater() {
		venue.addTheater(theater);
		assertEquals(theater, venue.findTheater(theater));
	}

	@Test
	void testFindByRoom() {
		venue.addTheater(theater);
		assertEquals(theater, venue.findByRoom(1));
	}

	@Test
	void testFindByHandicap() {
		venue.addTheater(theater);
		assertEquals(theater, venue.findByHandicap(true));
	}

	@Test
	void testEqualsObject() {
		Venue temp = new Venue("location", "name");
		assertEquals(true, venue.equals(temp));
	}

}
