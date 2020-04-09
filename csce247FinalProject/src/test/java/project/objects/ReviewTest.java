package project.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReviewTest {
	Review review;
	
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    	review = new Review(3, "good");
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetRating() {
        assertEquals(3, review.getRating());
    }

    @Test
    void testGetReview() {
        assertEquals("good", review.getReview());
    }

    @Test
    void testSetRating() {
        review.setRating(4);
        assertEquals(4, review.getRating());
    }

    @Test
    void testSetReview() {
        review.setReview("better");
        assertEquals("better", review.getReview());
    }

}
