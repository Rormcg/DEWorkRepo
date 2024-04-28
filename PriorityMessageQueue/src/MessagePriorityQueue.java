import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A custom-made structure modeling the functionality of a PriorityQueue of Messages,
 * designed to take in a series of Messages and gradually process and remove them
 * @author Rory McGuire
 */
public class MessagePriorityQueue extends MessageQueue{
	private  ArrayList<LinkedList<Message>> messages;
	
	private int processingTime;
	private Message processedMessage;
	
	/**
	 * Constructs a MessagePriorityQueue with a given number of Messages pre-loaded
	 * @param mes the number of Messages preloaded into the ArrayList
	 */
	public MessagePriorityQueue(int mes) {
		messages = new ArrayList<LinkedList<Message>> ();
		for(int i = 0; i <= Message.MAX_P; i ++) {
			messages.add(new LinkedList<Message>());
		}
		addRandom(mes);
		processingTime = 0;
		processedMessage = null;
	}
	
	/**
	 * Constructs a MessagePriorityQueue with a given number of Messages pre-loaded
	 */
	public MessagePriorityQueue() {
		this(0);
	}
	
	/**
	 * Adds a given Message to this object's Message queue
	 * @param m the Message to be added
	 */
	public void addMessage(Message m) {
		messages.get(m.getPriority()).add(m);
	}
	
	/**
	 * Adds a given number of random Messages of random priority to this object's Message queue
	 * @param n the number of Messages to be added
	 */
	public void addRandom(int n) {
		for(int i = 0; i < n; i++) {
			addMessage(new Message((int)(Math.random() * 5)));
		}
	}
	
	/**
	 * Removes and saves the value of the next Message in this object's Message queue
	 */
	public void processNext() {
		for(int i = 0; i < messages.size(); i++) {
			if(messages.get(i).size() > 0) {
				processedMessage = messages.get(i).remove();
				processingTime = PROCESS_TIME - 1;
				return;
			}
		}
	}
	
	/**
	 * Moves one step forward in this object's processing; represents the passage of one minute:
	 * Updates the arrival times of all Messages in this and if necessary removes ("processes") a Message
	 * @return the Message that was processed. If no message was processed, return null
	 */
	public Message update() {
		for(int i = 0; i < messages.size(); i++) {
			for(int j = 0; j < messages.get(i).size(); j++) {
				messages.get(i).get(j).increment();
			}
		}
		
		if(processingTime <= 0) {
			Message temp = processedMessage;
			processNext();
			return temp;
		} else {
			processingTime --;
			processedMessage.increment();
		}
		return null;
	}
	
	/**
	 * Calculates and returns the number of remaining Messages in this object's queue of Messages
	 * @return the number of remaining elements in the messages array
	 */
	public int remaining() {
		int sum = 0;
		for(int i = 0; i < messages.size(); i++) {
			sum += messages.get(i).size();
		}
		return sum;
	}
}
