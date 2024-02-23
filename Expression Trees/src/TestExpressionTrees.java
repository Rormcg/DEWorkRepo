import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestExpressionTrees {

	public static void main(String[] args) {
		String filename = "postFixExpressions.txt";
		if(args.length > 0) {
			filename = args[0];
		}
		
		File f = new File(filename);
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//String postFixExpressions[] = new String[];
		ArrayList<String[]> postfixExpressions = new ArrayList<String[]>();
		while(s.hasNext()) {
			String str = s.nextLine();
			postfixExpressions.add(str.split(" "));
		}
		//String[][] postfix = new String[][];
		
		for(String[] str : postfixExpressions) {
			TreeNode t = ExpressionTree.buildTreeStatic(str);
		}
	}

}
