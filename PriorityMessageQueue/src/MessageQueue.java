
/**
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

	public abstract void addMessage(Message m);

	public void addRandom(int n) {
		for (int i = 0; i < n; i++) {
			addMessage(new Message((int) (Math.random() * 5)));
		}
	}

	public abstract void processNext();

	/**
	 * 
	 * @return the Message that was processed. If no message was processed, return null
	 */
	public abstract Message update();

	/**
	 * 
	 * @return the number of remaining elements in the messages array
	 */
	public abstract int remaining();
}
