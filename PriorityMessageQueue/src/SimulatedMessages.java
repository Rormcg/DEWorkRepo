/**
 * @author Rory McGuire
 */
public class SimulatedMessages {
	
	/**
	 * Run a simulated transmission of messages to a receiving MessagePriorityQueue
	 * @param args
	 */
	public static void main(String[] args) {
		MessagePriorityQueue queue = new MessagePriorityQueue();
		int iterations = args.length > 0 ? Integer.parseInt(args[0]) : 100;
		
		for(int i = 0; i < iterations; i++) {
			if(Math.random() < 0.2) {
				queue.addRandom(1);
			}
			
			advanceTime(queue);
		}
		
	}
	
	public static void advanceTime(MessagePriorityQueue m) {
		/*For Real Time:
		try {
			Thread.sleep(minutes * 60000);
		} catch(InterruptedException e) {
			
		}*/
		m.update();
	}

}
