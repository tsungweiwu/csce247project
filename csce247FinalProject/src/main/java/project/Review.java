package project;
/**
 * Review Class
 */

public class Review {
	private int rating;
	private String review;

	public Review(int rating, String review) {
		this.setRating(rating);
		this.setReview(review);
	}

	/**
	 * 
	 * @return
	 */
	public int getRating() {
		return this.rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		if (rating <= 5) {
			this.rating = rating;
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getReview() {
		return this.review;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
	}
}
