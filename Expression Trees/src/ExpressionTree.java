import java.util.Stack;

public class ExpressionTree extends TreeNode implements Expressions {

	public ExpressionTree() {
		super(null);
		// TODO Auto-generated constructor stub
	}
	
	public ExpressionTree(Object o) {
		super(o);
	}
	
	//will evaluate the expression tree and return the answer
	@Override
	public int evalTree() {
		return evalTree(this);
	}
	
	private int evalTree(TreeNode tree) {
		if(tree.getValue().equals("+")) {
			return evalTree(tree.getLeft()) + evalTree(tree.getRight());
		} else if(tree.getValue().equals("*")){
			return evalTree(tree.getLeft()) * evalTree(tree.getRight());
		} else {
			return (int)tree.getValue();
		}
	}
	
	//TRAVERSALS//
	//traverse the expression tree and return a string in the appropriate notation
	@Override
	public String toPrefixNotation() {
		return toPrefixNotation(this);
	}
	
	private String toPrefixNotation(TreeNode tree) {
		String str = ""+tree.getValue();
		if(tree.getLeft() != null)
			str += " " + toPrefixNotation(tree.getLeft());
		if(tree.getRight() != null)
			str += " " + toPrefixNotation(tree.getRight());
		return str;
	}

	@Override
	public String toInfixNotation() {
		return toInfixNotation(this);
	}
	
	private String toInfixNotation(TreeNode tree) {
		String str = "";
		if(tree.getLeft() != null)
			str += toInfixNotation(tree.getLeft());		
		str += " " + tree.getValue();
		if(tree.getRight() != null)
			str += " " + toInfixNotation(tree.getRight());
		return str;
	}

	@Override
	public String toPostfixNotation() {
		return toPostfixNotation(this);
	}
	
	private String toPostfixNotation(TreeNode tree) {
		String str = "";
		if(tree.getLeft() != null)
			str += toPostfixNotation(tree.getLeft());
		if(tree.getRight() != null)
			str += " " + toPostfixNotation(tree.getRight());
		return str + " " + tree.getValue();
	}
	////
	
	
	//will take in an array of strings in postfix notation order and build 
	//and return an expression tree of type TreeNode
	@Override
	public ExpressionTree buildTree(String[] exp) {
		ExpressionTree tree = null;
		Stack<Object> stack = new Stack<Object>();
		
		for(int i = 0; i < exp.length; i++) {
			String n = exp[i].trim();
			if(n.equals("*") || n.equals("+")) {
				tree = new ExpressionTree(n);
				Object a = stack.pop(),
						b = stack.pop();
				tree.setRight((ExpressionTree)a);
				tree.setLeft((ExpressionTree)b);
				stack.push(tree);
			} else {
				try {
					//System.out.println("H");
					tree = new ExpressionTree(Integer.parseInt(n));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("exp[" + i + "] contains an unexpected value");
				}
				stack.push(tree);
			}
		}
		return tree;
		//return stack.pop();
	}
	
	public static ExpressionTree buildTreeStatic(String[] exp) {
		ExpressionTree t = new ExpressionTree();
		return t.buildTree(exp);
	}
	
	
	//will take in an array of strings that is an expression in post-fix 
	//notation and using a stack, evaluate the expression and return the answer.
	@Override
	public int postfixEval(String[] exp) {
		Stack<Integer> stack = new Stack<Integer> ();
		for(int i = 0; i < exp.length; i++) {
			if(exp[i].equals("+")) {
				int sum = (int)stack.pop() + (int)stack.pop();
				stack.push(sum);
			} else if(exp[i].equals("*")) {
				int product = (int)stack.pop() * (int)stack.pop();
				stack.push(product);
			} else {
				stack.push(Integer.parseInt(exp[i]));
			}
		}
		return stack.pop();
	}

}
