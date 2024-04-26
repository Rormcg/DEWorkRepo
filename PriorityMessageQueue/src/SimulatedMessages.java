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
		int preloaded = args.length > 1 ? Integer.parseInt(args[1]) : 1000; //number of Messages preloaded into the queue
		System.out.println("Number of Preloaded Messages: " + preloaded + "\nNumber of \"Minutes\" conducted: " + iterations);
		
		MessagePriorityQueue queue = new MessagePriorityQueue(preloaded);
		MessageHeap heap = new MessageHeap(preloaded);
		
		sim(iterations, queue);
		sim(iterations, heap);
	}
	
	public static void sim(int iterations, MessageQueue queue) {
		ArrayList<ArrayList<Integer>> arrivals = new ArrayList<ArrayList<Integer>> ();
		for(int i = 0; i <= Message.MAX_P; i++) {
			arrivals.add(new ArrayList<Integer>());
		}
		
		for(int i = 0; i < iterations; i++) {
			if(Math.random() < 0.2) {
				queue.addRandom(1);
			}
			
			advanceTime(queue, arrivals);
		}
		
		printResults(queue, arrivals);
	}
	
	private static void printResults(MessageQueue queue, ArrayList<ArrayList<Integer>> arrivals) {
		System.out.println("Average Arrival Times for each priority:");
		double[] averages = averageArrivals(arrivals);
		for(int i = 0; i < averages.length; i++) {
			System.out.println("Priority " + i + " (" + arrivals.get(i).size() + " item(s) processed): " + averages[i]);
		}
		
		System.out.println("Unprocessed Items Remaining: " + queue.remaining());
	}
	
	public static double[] averageArrivals(ArrayList<ArrayList<Integer>> arrivals) {
		double[] ave = new double[arrivals.size()];
		for(int i = 0; i <= Message.MAX_P; i++) {
			double average = 0;
			for(int j = 0; j < arrivals.get(i).size(); j++) {
				average += arrivals.get(i).get(j);
			}
			average /= arrivals.get(i).size() > 0 ? arrivals.get(i).size() : 1;
			ave[i] = average;
		}
		
		return ave;
	}
	
	private static void advanceTime(MessageQueue m, ArrayList<ArrayList<Integer>> arr) {
		/*For Real Time:
		try {
			Thread.sleep(minutes * 60000);
		} catch(InterruptedException e) {
			
		}*/
		Message mes = m.update();
		if(mes != null) {
			arr.get(mes.getPriority()).add(mes.getArrival());
		}
	}

}
