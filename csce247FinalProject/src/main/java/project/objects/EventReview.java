package project.objects;

import java.util.LinkedList;

public class EventReview {

    private String title;
    private LinkedList<Review> reviews = new LinkedList<>();

    /**
     * Getters
     */
    public LinkedList<Review> getReviews() {
        return reviews;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Setters
     */
    public void setReviews(LinkedList<Review> reviews) {
        this.reviews = reviews;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Gets the average rating by dividing the sum by the total count
     *
     * @return average
     */
    public double getRating() {
        int total = reviews.size();
        int sum = 0;

        for (Review review : getReviews()) {
            sum += review.getRating();
        }

        return (double) sum / (double) total;

    }
}
