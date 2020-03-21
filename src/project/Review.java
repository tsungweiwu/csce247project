package project;
/**
 * Review Class
 */

public class Review {
	private int rating;
	private String review;
	
	public Review(int rating, String review) {
		this.rating = rating;
		this.review = review;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRating() {
		return this.rating;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getReview() {
		return this.review;
	}
}
