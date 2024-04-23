import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Rory McGuire Mirrors the MessagePriorityQueue class, this time
 *         implementing the java-provided PriorityQueue as a heap, so as to
 *         compare the two
 */
public class MessageHeap extends MessageQueue{
	public static final int PROCESS_TIME = 4; // number of minutes/iterations a message takes to be processed
	// (including the iteration on which it is initially selected

	private PriorityQueue<Message> messages;

	private int processingTime;
	private Message processedMessage;

	/**
	 * 
	 * @param mes the number of Messages preloaded into the ArrayList
	 */
	public MessageHeap(int mes) {
		messages = new PriorityQueue<Message>(Comparator.reverseOrder());
		addRandom(mes);
		processingTime = 0;
		processedMessage = null;
	}

	/**
	* 
	*/
	public MessageHeap() {
		this(0);
	}

	public void addMessage(Message m) {
		messages.add(m);
	}

	public void addRandom(int n) {
		for (int i = 0; i < n; i++) {
			addMessage(new Message((int) (Math.random() * 5)));
		}
	}

	public void processNext() {
		processedMessage = messages.remove();
		processingTime = PROCESS_TIME - 1;
	}

	/**
	 * 
	 * @return the Message that was processed. If no message was processed, return
	 *         null
	 */
	public Message update() {
		for(Message m : messages) {
			m.increment();
		}
		
		if (processingTime <= 0) {
			Message temp = processedMessage;
			processNext();
			return temp;
		} else {
			processingTime--;
			processedMessage.increment();
		}
		return null;
	}

	/**
	 * 
	 * @return the number of remaining elements in the messages array
	 */
	public int remaining() {
		return messages.size();
	}
}
