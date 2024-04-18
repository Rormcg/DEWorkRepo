/**
 * @author Rory McGuire
 */
public class Message implements Comparable<Message>{
	public static final int MAX_P = 4; //Priority goes from 0 -> MAX_P
	private int arrival;
	private int priority;
	
	
	public Message(int p) {
		priority = p;
		arrival = 0;
	}
	
	@Override
	public int compareTo(Message m) {
		return getPriority() - m.getPriority();
	}
	
	/**
	 * Increments arrival by one 
	 * (represents one additional minute passing before this Message has been delivered)
	 */
	public void increment() {
		arrival ++;
	}
	
	/**
	 * Increments arrival by a given amount 
	 * (represents additional minutes passing before this Message has been delivered)
	 */
	public void increment(int a) {
		arrival += a;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public int getArrival() {
		return arrival;
	}
}
