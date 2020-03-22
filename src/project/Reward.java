
public class Reward {
	private User user;
	private int points;
	
	public Reward(User user) {
		this.user = user;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void update() {
		//TODO update based on show points
	}
	public boolean redeem() {
		if(points >= 20) 
			points -=20;
		return points >= 20;
	}
}
