
/**
 * A queue of Messages, designed to take in a series of Messages and gradually process and remove them
 * @author Rory McGuire
 */
public abstract class MessageQueue {
	/**
	 * Number of minutes/iterations a message takes to be processed
	 * (including the iteration on which it is initially selected)
	 */
	public static final int PROCESS_TIME = 4; 
	
	//private int processingTime;
	//private Message processedMessage;
	
	/**
	 * Adds a given Message to this object's Message queue
	 * @param m the Message to be added
	 */
	public abstract void addMessage(Message m);
	
	/**
	 * Adds a given number of random Messages of random priority to this object's Message queue
	 * @param n the number of Messages to be added
	 */
	public void addRandom(int n) {
		for (int i = 0; i < n; i++) {
			addMessage(new Message((int) (Math.random() * 5)));
		}
	}
	
	/**
	 * Removes and saves the value of the next Message in this object's Message queue
	 */
	public abstract void processNext();

	/**
	 * Moves one step forward in this object's processing; represents the passage of one minute:
	 * Updates the arrival times of all Messages in this and if necessary removes ("processes") a Message
	 * @return the Message that was processed. If no message was processed, return null
	 */
	public abstract Message update();

	/**
	 * Calculates and returns the number of remaining Messages in this object's queue of Messages
	 * @return the number of remaining elements in the messages array
	 */
	public abstract int remaining();
}
