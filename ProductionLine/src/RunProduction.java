import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Runs the ProductionLine project.
 * Contains functionality to run both the Graphical and non-Graphical implementations by instantiating a ProductionLine object and calling its methods.
 * Based on the given arguments to main, RunProduction will use a file for input or generate a new random file to use.
 * The second argument will decide whether to run with Graphics.
 * @author Rory McGuire
 */
public class RunProduction {
	
	/**
	 * Fills in a ProductionLine's input based on the contents of a file.
	 * Reads in the values of Disk radii from a given file, then uses them to instantiate Disk objects that it then places into the production.input InputLine.
	 * @param production the ProductionLine to be modified
	 * @param filename the relative filepath of the file to be read in
	 */
	public static void readInDisks(ProductionLine production, String filename) {
		File f = new File(filename);
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		while(s.hasNext()) {
			production.addDisk(new Disk(s.nextInt()));
		}
		
		s.close();
	}
	
	/**
	 * Runs the given ProductionLine's non-Graphical algorithm, then removes and prints out the output of the algorithm.
	 * @param production the ProductionLine to be run
	 */
	public static void runProduction(ProductionLine production) {
		production.process();
		///*
		String out = "Output: (FOF)\n";
		int count = 0;
		while(!production.outputIsEmpty()) {
			out += "Tower " + count + ": " + production.removeTower() + "\n";
			count++;
		}
		
		System.out.println(out.trim());
		//*/
		//production.printOutput();
		
	}
	
	/**
	 * The method used to run the RunProduction program. Can run the program via command line or via Java graphics
	 * @param args The arguments passed to the program via command line args. Contains two values:<br>
	 * <str>args[0]</str>: The filename of the input file<br>
	 * <str>args[1]</str>: A boolean declaring whether or not the program will run with graphical components. If true, the program
	 * will run on the command line only. If false, the program will run with graphics.
	 */
	public static void main(String[] args) {
		String filename;
		if(args.length <= 0) {
			filename = "input.txt";
			GenerateTestFile.main(new String[] {filename});
			//throw new IllegalArgumentException("RunProduction.main() must run with an argument containing a filename");
		} else {
			filename = args[0];
		}
		
		boolean runWithoutGraphics = false;
		if(args.length > 1) {
			runWithoutGraphics = Boolean.parseBoolean(args[1]);
		}
			
		ProductionLine production = new ProductionLine(!runWithoutGraphics);
		
		readInDisks(production, filename);
		
		production.printInput();
		
		if(runWithoutGraphics) {
			runProduction(production);
		} else {
			production.start();
		}
	}

}
