/**
 *
 */
package project.databases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.objects.Status;
import project.objects.Venue;

class VenueDatabaseTest {

    VenueDatabase vDB;
    ReviewDatabase rDB;
    ArrayList<Venue> venues = new ArrayList<>();
    Venue venue;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        rDB = new ReviewDatabase();
        rDB.loadReviews();
        vDB = new VenueDatabase(rDB);

        venue = new Venue("location", "name");
        venues = vDB.getVenues();
        venues.add(venue);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetVenues() {
        assertNotNull(vDB.getVenues());
    }

    @Test
    void testSetVenues() {
        vDB.setVenues(venues);
        testGetVenues();
    }

    @Test
    void testFindVenue() {
        assertEquals(venue, vDB.findVenue(venue));
    }

    @Test
    void testContainsVenue() {
        assertTrue(vDB.containsVenue(venue));
    }

    @Test
    void testGetCeil() {
        assertEquals(5, vDB.getCeil(4));
    }

    @Test
    void testGetCeilRoundUp() {
        assertEquals(5, vDB.getCeil(4.5));
    }

    @Test
    void testGetEvent() {
        assertEquals("Frozen 2", vDB.getEvent("Frozen 2").getTitle());
    }

    @Test
    void testDiscountPercent92() {
        assertEquals(0.92, vDB.discountPercent(Status.STUDENT));
    }

    @Test
    void testDiscountPercent85() {
        assertEquals(85, vDB.discountPercent(Status.MILITARY));
    }

    @Test
    void testDiscountPercent90() {
        assertEquals(0.90, vDB.discountPercent(Status.TEACHER));
    }

    @Test
    void testDiscountPercentNone() {
        assertEquals(1, vDB.discountPercent(Status.NONE));
    }

    @Test
    void testLoadVenues() {
        vDB.loadVenues();
        assertNotNull(vDB);
    }

}
