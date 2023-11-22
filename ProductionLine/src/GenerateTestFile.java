import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class to be used in generating new random test files for RunProductionLine.
 * Can be used independently, but is also used within the RunProductionLine.main() method when no filename is given.
 * Will generate a file containing a series of numbers representing Disk radii.
 * @author Rory McGuire
 */
public class GenerateTestFile {
	
	/**
	 * Entry point for the GenerateTestFile class.
	 * Will create a file containing a series of ints representing Disk radii.
	 * If parameters are provided, will use args[0] ints, otherwise, will default to 20.
	 * Each int value will be randomly generated to be between the max and min radius values found in the Disk class.
	 * @param args if available, args[0] will be taken as the number of int values to generate
	 */
	public static void main(String[] args) {
		int numDisks = 20;
		if(args.length > 0) {
			numDisks = Integer.parseInt(args[0]);
		}
		
		String filename = "input.txt";
		
		File f = new File(filename);
		PrintWriter p = null;
		try {
			p = new PrintWriter(f);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		for(int i = 0; i < numDisks; i ++) {
			p.print((int)(Math.random() * (Disk.LARGEST - Disk.SMALLEST + 1) + Disk.SMALLEST));
			if(i < numDisks - 1)
				p.println();
		}
		p.close();
		
		System.out.println(numDisks + " Disk radius values added to " + filename + " successfully");
	}

}
