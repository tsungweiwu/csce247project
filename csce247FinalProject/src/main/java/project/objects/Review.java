package project.objects;

/**
 * Review Class holds the reviews of an event
 */
public class Review {

    private int rating;
    private String review;

    public Review(int rating, String review) {
        this.setRating(rating);
        this.setReview(review);
    }

    /**
     * Getters
     */
    public int getRating() {
        return this.rating;
    }

    public String getReview() {
        return this.review;
    }

    /**
     * Setters
     */
    public void setRating(int rating) {
        if (rating <= 5) {
            this.rating = rating;
        }
    }

    public void setReview(String review) {
        this.review = review;
    }
}
