/**
 * @author Rory McGuire
 */

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;


public class MatchingBraces {
	
	public static void main(String[] args) {
		String filename = ".\\src\\test.txt";
		
		boolean isMatching = scanFile(filename);
		System.out.println(filename + " " + isMatching);
	}
	
	public static boolean scanFile(String filename) {
		//System.out.println(System.getProperty("user.dir")); 
		File f = new File(filename);
		Scanner s = null;
				
		try {
			s = new Scanner(f);
		} catch(FileNotFoundException e) {
			System.out.println(filename + " not Found");
			System.exit(0);
		}
		
		
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
