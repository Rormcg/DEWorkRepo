/**
 * @author Rory McGuire
 */
public class Message {
	public static final int MAX_P = 4; //Priority goes from 0 -> MAX_P
	private int arrival;
	private int priority;
	
	
	public Message(int p) {
		priority = p;
		arrival = 0;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public int getArrival() {
		return arrival;
	}
}
