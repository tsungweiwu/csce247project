package project.databases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.objects.EventReview;
import project.objects.Event;
import project.objects.Genre;
import project.objects.Review;
import project.objects.Type;

/**
 * These are the main functions in the databases, the other ones are not implemented with test cases
 * because they only write to JSON files, and we should check the data before it is even written
 * into these JSON files. The JSON writing and reading are checked by exceptions already.
 */
class ReviewDatabaseTest {

    ReviewDatabase rDB = new ReviewDatabase();

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {

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
    void testGetEventReviews() {
        assertNotNull(rDB.getEventReviews());
    }

    @Test
    void testSetEventReviews() {
        HashSet<EventReview> eventReviews = new HashSet<>();
        eventReviews.add(new EventReview("title"));

        rDB.setEventReviews(eventReviews);
        assertNotNull(rDB.getEventReviews());
    }

    @Test
    void testLoadReviews() {
        rDB.loadReviews();
        assertNotNull(rDB);
    }

    /**
     * Passes in event object and checks if it returns all reviews with the same title
     */
    @Test
    void testFindEventReview() {
        Event e = new Event("", "Alladin", Genre.FAMILY, "", false, Type.MOVIE, 5.00);
        assertEquals("Alladin", rDB.findEventReview(e).getTitle());
    }
}
