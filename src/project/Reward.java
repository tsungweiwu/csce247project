
public class Reward {
	private RegisteredUser user;
	private int points;
	
	public Reward(RegisteredUser user) {
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
