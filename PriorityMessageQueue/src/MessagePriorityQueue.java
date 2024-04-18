import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Rory McGuire
 */
public class MessagePriorityQueue extends MessageQueue{
	public static final int PROCESS_TIME = 4; //number of minutes/iterations a message takes to be processed
											//(including the iteration on which it is initially selected
	
	private  ArrayList<LinkedList<Message>> messages;
	
	private int processingTime;
	private Message processedMessage;
	
	/**
	 * 
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
	 * 
	 */
	public MessagePriorityQueue() {
		this(0);
	}
	
	public void addMessage(Message m) {
		messages.get(m.getPriority()).add(m);
	}
	
	public void addRandom(int n) {
		for(int i = 0; i < n; i++) {
			addMessage(new Message((int)(Math.random() * 5)));
		}
	}
	
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
	 * 
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
	 * 
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
