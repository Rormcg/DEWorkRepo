import java.util.ArrayList;

/**
 * @author Rory McGuire
 */
public class SimulatedMessages {
	
	/**
	 * Run a simulated transmission of messages to a receiving MessagePriorityQueue
	 * @param args
	 */
	public static void main(String[] args) {
		int iterations = args.length > 0 ? Integer.parseInt(args[0]) : 100; //the number of "minutes" that this simulation will run for
		int preloaded = args.length > 1 ? Integer.parseInt(args[1]) : 100; //number of Messages preloaded into the queue
		MessagePriorityQueue queue = new MessagePriorityQueue(preloaded);
		ArrayList<ArrayList<Integer>> arrivals = new ArrayList<ArrayList<Integer>> ();
		
		for(int i = 0; i < iterations; i++) {
			if(Math.random() < 0.2) {
				queue.addRandom(1);
			}
			
			advanceTime(queue, arrivals);
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
