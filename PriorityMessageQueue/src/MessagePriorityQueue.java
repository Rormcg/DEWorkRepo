import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Rory McGuire
 */
public class MessagePriorityQueue {
	private  ArrayList<LinkedList<Message>> messages;
	
	public MessagePriorityQueue() {
		messages = new ArrayList<LinkedList<Message>> ();
		for(int i = 0; i < Message.MAX_P; i ++) {
			messages.add(new LinkedList<Message>());
		}
	}
	
	
	public void addMessage(Message m) {
		messages.get(m.getPriority()).add(m);
	}
}
