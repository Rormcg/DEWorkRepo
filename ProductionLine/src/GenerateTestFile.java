/**
 * @author Rory McGuire
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateTestFile {

	public static void main(String[] args) {
		int numDisks = 20;
		//int maxRadius = 40;
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
			p.print((int)(Math.random() * Disk.LARGEST + 1));
			if(i < numDisks - 1)
				p.println();
		}
		p.close();
		
		System.out.println(numDisks + " Disk radius values added to " + filename + " successfully");
	}

}
