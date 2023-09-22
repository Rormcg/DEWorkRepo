

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;


public class MatchingBraces {
	
	
	public static boolean scanFile(String filename) {
		File f = null;
		Scanner s = null;
		f = new File(filename);
		
		try {
			s = new Scanner(f);
		} catch(FileNotFoundException e) {
			System.out.println("File not Found");
		}
		
		
		int numBraces = 0;
		
		while(s.hasNext()) {
			String next = s.next();
			for(int i = 0; i < next.length(); i ++) {
				if(next.charAt(i) == '(') {
					numBraces ++;
				} else if(next.charAt(i) == ')') {
					if(numBraces > 0) {
						numBraces --;
					} else {
						return false;
					}
				}
			}
		}
		
		return numBraces == 0;
	}
}
