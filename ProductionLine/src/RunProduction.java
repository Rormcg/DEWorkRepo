/**
 * @author Rory McGuire
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RunProduction {

	public static void main(String[] args) {
		ProductionLine production = new ProductionLine();
		
		if(args.length <= 0) throw new IllegalArgumentException("RunProduction.main() must run with an argument filename");
		
		File f = new File(args[0]);
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
		
		production.process();
		
		String out = "";
		while(!production.outputIsEmpty()) {
			out += production.removeTower() + "\n";
		}
		
		System.out.println(out.trim());
	}

}
