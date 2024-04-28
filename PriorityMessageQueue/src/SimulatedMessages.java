import java.util.ArrayList;

/**
 * Simulates a transmission of messages and evaluates the performance of MessagePriorityQueue and MessageHeap
 * @author Rory McGuire
 */
public class SimulatedMessages {
	
	/**
	 * Run a simulated transmission of messages to a receiving MessagePriorityQueue.
	 * Runs and prints the results (for both MessagePriorityQueue and MessageHeap):<br>
	 * A single simulation to completion (until the heap or list is empty),
	 * A single simulation for a fixed number of iterations, and
	 * A number of simulations to completion (the average waiting times are returned)
	 * @param args[0] the number of "minutes" (iterations) each simulation will run for
	 * @param args[1] the number of messages preloaded in each simulation
	 * @param args[2] the number of complete simulations to be run 
	 */
	public static void main(String[] args) {
		int iterations = args.length > 0 ? Integer.parseInt(args[0]) : 100; //the number of "minutes" that this simulation will run for
		int preloaded = args.length > 1 ? Integer.parseInt(args[1]) : 1000; //number of Messages preloaded into the queue
		int simulations = args.length > 2 ? Integer.parseInt(args[2]) : 10; //the number of complete simulations to run on both implementations
		System.out.println("Number of Preloaded Messages: " + preloaded + "\nNumber of \"Minutes\" conducted: " + iterations + "\n");
		
		MessagePriorityQueue queue = new MessagePriorityQueue(preloaded);
		MessageHeap heap = new MessageHeap(preloaded);
		
		System.out.println("Example Simulation Until Empty:");
		System.out.println("Custom Implementation: ");
		sim(iterations, queue, true);
		System.out.println("PriorityQueue (Heap) Implementation: ");
		sim(iterations, heap, true);
		
		System.out.println("\nExample Simulation for a Fixed " + iterations + " Iterations:");
		System.out.println("Custom Implementation: ");
		queue = new MessagePriorityQueue(preloaded);
		sim(iterations, queue, true);
		System.out.println("PriorityQueue (Heap) Implementation: ");
		heap = new MessageHeap(preloaded);
		sim(iterations, heap, true);
		
		System.out.println("Run " + simulations + " simulations:");
		double[] averagesH = new double[Message.MAX_P + 1];
		double[] averagesQ = new double[Message.MAX_P + 1];
		for(int i = 0; i < simulations; i++) {
			MessageHeap h = new MessageHeap(preloaded);
			double[] tempH = simToCompletion(h, false);
			//double[] tempH = sim(iterations, h, false);

			
			MessagePriorityQueue q = new MessagePriorityQueue(preloaded);
			//double[] tempQ = sim(iterations, q, false);
			double[] tempQ = simToCompletion(q, false);
			for(int j = 0; j < averagesH.length; j++) {
				averagesH[j] += tempH[j];
				averagesQ[j] += tempQ[j];
				if(i >= simulations - 1) {
					averagesH[j] /= simulations;
					averagesQ[j] /= simulations;
				}
			}
		}
		System.out.println("Custom Implementation: ");
		for(int i = 0; i < averagesQ.length; i++) {
			System.out.println("Priority " + i + ": " + averagesQ[i]);
		}
		System.out.println("\nPriorityQueue (Heap) Implementation: ");
		for(int i = 0; i < averagesH.length; i++) {
			System.out.println("Priority " + i + ": " + averagesH[i]);
		}
	}
	
	/**
	 * Runs a simulation of messages and runs them through a MessageQueue until empty,
	 * returning the average wait times for each priority level
	 * @param queue the MessageQueue to be simulated
	 * @param print whether to print out the results
	 * @return the average wait times for each priority level
	 */
	public static double[] simToCompletion(MessageQueue queue, boolean print) {
		ArrayList<ArrayList<Integer>> arrivals = new ArrayList<ArrayList<Integer>> ();
		for(int i = 0; i <= Message.MAX_P; i++) {
			arrivals.add(new ArrayList<Integer>());
		}
		
		while(queue.remaining() > 0) {
			if(Math.random() < 0.2) {
				queue.addRandom(1);
			}
			
			advanceTime(queue, arrivals);
		}
		
		if(print)printResults(queue, arrivals);
		return averageArrivals(arrivals);
	}
	
	/**
	 * Runs a simulation of messages and runs them through a MessageQueue for a fixed number of iterations,
	 * returning the average wait times for each priority level
	 * @param iterations the number of "minutes" (iterations) to run the simulation for
	 * @param queue the MessageQueue to be simulated
	 * @param print whether to print out the results
	 * @return the average wait times for each priority level
	 */
	public static double[] sim(int iterations, MessageQueue queue, boolean print) {
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
		
		if(print)printResults(queue, arrivals);
		return averageArrivals(arrivals);
	}
	
	private static void printResults(MessageQueue queue, ArrayList<ArrayList<Integer>> arrivals) {
		System.out.println("Average Arrival Times for each priority:");
		double[] averages = averageArrivals(arrivals);
		for(int i = 0; i < averages.length; i++) {
			System.out.println("Priority " + i + " (" + arrivals.get(i).size() + " item(s) processed): " + averages[i]);
		}
		
		if(queue != null)System.out.println("Unprocessed Items Remaining: " + queue.remaining());
	}
	
	/**
	 * Takes a list of arrival times and returns an array of the averages for each respective priority level
	 * @param arrivals the ArrayList of ArrayLists containing the arrival times at multiple different priority levels
	 * @return array of the averages for each respective priority level
	 */
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
