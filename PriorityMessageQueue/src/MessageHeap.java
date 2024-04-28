import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Rory McGuire
 * Mirrors the MessagePriorityQueue class, this time implementing
 * the java-provided PriorityQueue as a heap, so as to compare the two
 */
public class MessageHeap extends MessageQueue{
	private PriorityQueue<Message> messages;

	private int processingTime;
	private Message processedMessage;

	/**
	 * Constructs a MessageHeap with a given number of Messages pre-loaded
	 * @param mes the number of Messages preloaded into the heap
	 */
	public MessageHeap(int mes) {
		messages = new PriorityQueue<Message>(Comparator.reverseOrder());
		addRandom(mes);
		processingTime = 0;
		processedMessage = null;
	}

	/**
	 * Constructs a MessageHeap without any Messages pre-loaded
	 */
	public MessageHeap() {
		this(0);
	}
	
	/**
	 * Adds a given Message to this object's Message queue
	 * @param m the Message to be added
	 */
	public void addMessage(Message m) {
		messages.add(m);
	}
	
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
	public void processNext() {
		processedMessage = messages.remove();
		processingTime = PROCESS_TIME - 1;
	}

	/**
	 * Moves one step forward in this object's processing; represents the passage of one minute:
	 * Updates the arrival times of all Messages in this and if necessary removes ("processes") a Message
	 * @return the Message that was processed. If no message was processed, return null
	 */
	public Message update() {
		for(Message m : messages) {
			m.increment();
		}
		
		if (processingTime <= 0) {
			Message temp = processedMessage;
			if(remaining() > 0) processNext();
			return temp;
		} else {
			processingTime--;
			processedMessage.increment();
		}
		return null;
	}

	/**
	 * Calculates and returns the number of remaining Messages in this object's queue of Messages
	 * @return the number of remaining elements in the messages array
	 */
	public int remaining() {
		return messages.size();
	}
}
