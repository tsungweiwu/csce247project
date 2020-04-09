package project.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TheaterTest {

    Theater theater;
    Event event;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        theater = new Theater(1, true);
        event = new Event("00", "Name", Genre.ACTION, "movie", false, Type.MOVIE, 2.00);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetRoom() {
        assertEquals(1, theater.getRoom());
    }

    @Test
    void testIsHandicap() {
        assertEquals(true, theater.isHandicap());
    }

    @Test
    void testSetRoom() {
        theater.setRoom(2);
        assertEquals(2, theater.getRoom());
    }

    @Test
    void testSetHandicap() {
        theater.setHandicap(false);
        assertEquals(false, theater.isHandicap());
    }

    @Test
    void testContainsEvent() {
        theater.addEvent(event);
        assertEquals(true, theater.containsEvent(event));
    }

    @Test
    void testAddEvent() {
        testContainsEvent();
    }

    @Test
    void testEqualsObject() {
        Theater temp = new Theater(1, true);
        assertEquals(true, theater.equals(temp));
    }

    @Test
    void testFindEvent() {
        theater.addEvent(event);
        assertEquals(event, theater.findEvent(event));
    }

}
