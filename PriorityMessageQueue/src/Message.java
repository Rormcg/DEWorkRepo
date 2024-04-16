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
	
	/**
	 * Increments arrival by one 
	 * (represents one additional minute passing before this Message has been delivered)
	 */
	public void increment() {
		arrival ++;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public int getArrival() {
		return arrival;
	}
}
