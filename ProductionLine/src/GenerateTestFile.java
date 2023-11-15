/**
 * @author Rory McGuire
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateTestFile {

	public static void main(String[] args) {
		String filename = "input.txt";
		File f = new File(filename);
		PrintWriter p = null;
		try {
			p = new PrintWriter(f);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		p.close();
	}

}
