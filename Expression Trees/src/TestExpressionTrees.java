import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TestExpressionTrees {
	
//	public final static int
//	MAXVAL = 30,
//	MAXNODES = 10; 
	
	public static void main(String[] args) {
		//READ IN INPUT
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
		
		ArrayList<String[]> postfixExpressions = new ArrayList<String[]>();
		while(s.hasNext()) {
			String str = s.nextLine();
			postfixExpressions.add(str.split(" "));
		}
		
		//READ IN ANSWERS
		filename = "answers.txt";
		if(args.length > 1) {
			filename = args[1];
		}
		
		f = new File(filename);
		try {
			s = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		ArrayList<String> answers = new ArrayList<String>();
		while(s.hasNext()) {
			answers.add(s.nextLine());
		}
		
		//Write Output
		PrintWriter p = null;
		try {
			p = new PrintWriter("myAnswers.txt");
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		for(int i = 0; i < postfixExpressions.size(); i++) {
			String[] str = postfixExpressions.get(i);
			System.out.println(printArr(str));
			p.println("Test: " + printArr(str));
			try {
				ExpressionTree t = ExpressionTree.buildTreeStatic(str);
				int ans = t.evalTree();
				
				p.println("Evaluation: " + ans);
				p.println("Expected Evaluation: " + answers.get(i));
				p.println("Prefix: " + t.toPrefixNotation());
				p.println("Infix: " + t.toInfixNotation());
				p.println("Postfix: " + t.toPostfixNotation());
				//(No Binary Tree Middle Man):
				p.println("postfixEval(): " + t.postfixEval(str) + "\n\n");
			} catch (IllegalArgumentException e) {
				p.println("IllegalArgumentException\n\n");
				System.out.println("Exception on test: " + printArr(str));
				e.printStackTrace();
			}
		}
		p.close();
	}
	
	private static String printArr(String[] arr) {
		String str = "[";
		for(String s : arr) {
			str += s + ", ";
		}
		return str.substring(0, str.length() - 2) + "]";
	}

}
