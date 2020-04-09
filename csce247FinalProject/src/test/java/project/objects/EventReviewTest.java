package project.objects;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventReviewTest {

    EventReview eReview;
    Review review;
    LinkedList<Review> reviews;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        reviews = new LinkedList<>();

        review = new Review(3, "good");
        reviews.add(review);

        eReview = new EventReview("title");
        eReview.setReviews(reviews);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetReviews() {
        assertEquals(reviews, eReview.getReviews());
    }

    @Test
    void testGetTitle() {
        assertEquals("title", eReview.getTitle());
    }

    @Test
    void testSetReviews() {
        reviews.add(new Review(5, "newRev"));
        eReview.setReviews(reviews);
        testGetReviews();
    }

    @Test
    void testSetTitle() {
        eReview.setTitle("newTitle");
        assertEquals("newTitle", eReview.getTitle());
    }

    @Test
    void testGetRating() {
        assertEquals(3, eReview.getRating());
    }

}
