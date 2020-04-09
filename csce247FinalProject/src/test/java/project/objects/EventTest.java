package project.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {
	Event event;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    	event = new Event("00", "Name", Genre.ACTION, "movie", false, Type.MOVIE, 2.00);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetDate() {
        assertEquals("00", event.getDate());
    }

    @Test
    void testGetTitle() {
        assertEquals("Name", event.getTitle());
    }

    @Test
    void testGetGenre() {
        assertEquals(Genre.ACTION, event.getGenre());
    }

    @Test
    void testGetDescription() {
        assertEquals("movie", event.getDescription());
    }

    @Test
    void testIsExplicit() {
        assertEquals(false, event.isExplicit());
    }

    @Test
    void testGetType() {
        assertEquals(Type.MOVIE, event.getType());
    }

    @Test
    void testGetPrice() {
        assertEquals(2.00, event.getPrice());
    }

    @Test
    void testGetAvailable() {
        assertEquals(100, event.getAvailable());
    }

    @Test
    void testSetDate() {
        event.setDate("11");
        assertEquals("11", event.getDate());
    }

    @Test
    void testSetTitle() {
    	event.setTitle("Name2");
        assertEquals("Name2", event.getTitle());
    }

    @Test
    void testSetGenre() {
        event.setGenre(Genre.CLASSIC);
        assertEquals(Genre.CLASSIC, event.getGenre());
    }

    @Test
    void testSetDescription() {
        event.setDescription("second des");
        assertEquals("second des", event.getDescription());
    }

    @Test
    void testSetExplicit() {
        event.setExplicit(true);
        assertEquals(true, event.isExplicit());
    }

    @Test
    void testSetType() {
        event.setType(Type.CONCERT);
        assertEquals(Type.CONCERT, event.getType());
    }

    @Test
    void testSetPrice() {
        event.setPrice(3.00);
        assertEquals(3.00, event.getPrice());
    }

    @Test
    void testEqualsObject() {
        Event temp = new Event("00", "Name", Genre.ACTION, "movie", false, Type.MOVIE, 2.00);
        assertEquals(true, event.equals(temp));
    }

}
