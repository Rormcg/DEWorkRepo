
/**
 *  This program takes a text file, creates an index (by line numbers)
 *  for all the words in the file and writes the index
 *  into the output file.  The program takes input and output file names
 *  from the command-line args or prompts the user for the file names.
 *  @author Anonymous(?)
 *  @author Rory McGuire
 */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class IndexMaker {
	
	
	/**
	 * The Main entry point for the IndexMaker project
	 * Takes in an input and output file, then indexes the words found in the input, 
	 * placing the indexed words into the output file
	 * @param args args[0] is the filename for the input file, args[1] is the filename for the output file
	 * @throws IOException if the input file cannot be found
	 */
	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		String fileName;

		// Open input file:

		if (args.length > 0)
			fileName = args[0];
		else {
			System.out.print("\nEnter input file name: ");
			fileName = keyboard.nextLine().trim();
		}

		BufferedReader inputFile = new BufferedReader(new FileReader(fileName), 1024);

		// Create output file:

		if (args.length > 1)
			fileName = args[1];
		else {
			if(fileName.indexOf(".") == -1) {
				fileName += "Index.txt";
			} else {
				fileName = fileName.substring(0, fileName.indexOf(".")) + "Index.txt";
			}
			//System.out.print("\nEnter output file name: ");
			//fileName = keyboard.nextLine().trim();
		}

		PrintWriter outputFile = new PrintWriter(new FileWriter(fileName));

		// Create index:

		DocumentIndex index = new DocumentIndex();

		String line;
		int lineNum = 0;
		while ((line = inputFile.readLine()) != null) {
			lineNum++;
			index.addAllWords(line, lineNum);
		}
		// System.out.println(index);
		// Save index:

		int count = 0;
		for (String entry : index.keySet()) {
			outputFile.print(index.get(entry));
			if (count < index.keySet().size() - 1)
				outputFile.println();
			count++;
		}

		// Finish:

		inputFile.close();
		outputFile.close();

		keyboard.close();
		System.out.println("Done.");
	}
}
