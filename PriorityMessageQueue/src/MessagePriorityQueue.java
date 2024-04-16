import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Rory McGuire
 */
public class MessagePriorityQueue {
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
		
		processingTime = 0;
		processedMessage = null;
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
				processedMessage = messages.get(i).remove(0);
				processingTime = PROCESS_TIME - 1;
			}
		}
	}
	
	public void update() {
		for(int i = 0; i < messages.size(); i++) {
			for(int j = 0; j < messages.get(i).size(); i++) {
				messages.get(i).get(j).increment();
			}
		}
		
		if(processingTime <= 0) {
			processNext();
		} else {
			processingTime --;
		}
	}
}
