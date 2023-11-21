/**
 * @author Rory McGuire
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RunProduction {
	
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
	
	//For use with the non-graphical implementation
	public static void runProduction(ProductionLine production) {
		production.process();
		
		String out = "";
		while(!production.outputIsEmpty()) {
			out += production.removeTower() + "\n";
		}
		
		System.out.println(out.trim());
	}
	
	/**
	 * The method used to run the RunProduction program. Can run the program via command line or via Java graphics
	 * @param args The arguments passed to the program via command line args. Contains two values:<br>
	 * <str>args[0]</str>: The filename of the input file<br>
	 * <str>args[1]</str>: A boolean declaring whether or not the program will run with graphical components. If true, the program
	 * will run on the command line only. If false, the program will run with graphics.
	 */
	public static void main(String[] args) {
		if(args.length <= 0) throw new IllegalArgumentException("RunProduction.main() must run with an argument containing a filename");
		
		boolean runWithoutGraphics = false;
		if(args.length > 1) {
			runWithoutGraphics = Boolean.parseBoolean(args[1]);
		}
			
		ProductionLine production = new ProductionLine(runWithoutGraphics);
		
		readInDisks(production, args[0]);
		
		production.printInput();
		
		if(runWithoutGraphics) {
			runProduction(production);
		}
	}

}
