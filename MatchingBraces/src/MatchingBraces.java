/**
 * Class that will scan a file to ensure that all of its braces are matching
 * @author Rory McGuire
 */

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;


public class MatchingBraces {
	public final static String SEP = File.separator;
	
	public static void main(String[] args) {
		String filename = "." + SEP + "src" + SEP + "test.txt";
		
		boolean isMatching = scanFile(filename);
		System.out.println(filename + " " + isMatching);
	}
	
	/**
	 * Scans the given file to determine whether all braces within the file are paired up and
	 * returns a boolean indicating the result
	 * @param filename the exact or relative path to the file to be scanned
	 * @return true if all braces in the file match up, false otherwise
	 */
	public static boolean scanFile(String filename) {
		//System.out.println(System.getProperty("user.dir")); 
		File f = new File(filename);
		Scanner s = null;
		
		//Initialize Scanner
		try {
			s = new Scanner(f);
		} catch(FileNotFoundException e) {
			System.out.println(filename + " not Found");
			System.exit(0);
		}
		
		//Scan the file for pairs of braces
		int numBraces = 0;
		while(s.hasNext()) {
			String next = s.next();
			for(int i = 0; i < next.length(); i ++) {
				if(next.charAt(i) == '{') {
					numBraces ++;
				} else if(next.charAt(i) == '}') {
					if(numBraces > 0) {
						numBraces --;
					} else {
						s.close();
						return false;
					}
				}
			}
		}
		
		s.close();
		
		return numBraces == 0;
	}
}
