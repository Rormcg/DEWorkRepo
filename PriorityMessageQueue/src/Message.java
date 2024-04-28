
/**
 * Represents a single Message to be transmitted through a MessageQueue
 * @author Rory McGuire
 */
public class Message implements Comparable<Message>{
	public static final int MAX_P = 4; //Priority goes from 0 -> MAX_P
	private int arrival;
	private int priority;
	
	/**
	 * Constructs a new Message with the given priority
	 * @param p the priority of the Message
	 */
	public Message(int p) {
		priority = p;
		arrival = 0;
	}
	
	/**
	 * Compares this and another Message; returns a positive number if this has a higher (closer to zero) priority than the other
	 * @param m the Message to compare to this
	 * @return the equivalent of (this - m); positive if this is greater, negative if m is greater, zero if they are functionally equal
	 */
	@Override
	public int compareTo(Message m) {
		return m.getPriority() - getPriority();
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
	
	/**
	 * Returns the priority of this message
	 * @return priority
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * Returns the time that has passed before this message "arrived"
	 * @return arrival
	 */
	public int getArrival() {
		return arrival;
	}
	
	/**
	 * Returns a String representation of this Message
	 * @return a String representation of this Message
	 */
	public String toString() {
		return "Priority: " + priority + ", Arrival Time: " + arrival;
	}
}
