import java.util.Stack;

/**
 * The main class for the ExpressionTrees Project
 * A Binary Tree that represents a mathematical expression made up of Integers, "+", and "*"
 * Contains functionality for converting expressions into ExpressionTrees and vice-versa
 * Also can evaluate expressions in postfix for their overall Integer value
 * @author Rory McGuire
 */
public class ExpressionTree extends TreeNode implements Expressions {
	
	/**
	 * Constructor for an ExpressionTree object
	 * Calls the TreeNode(Object) constructor, creating a tree with a single value at its root
	 * @param o the value to be assigned to this tree's root
	 */
	public ExpressionTree(Object o) {
		super(o);
	}
	
	/**
	 * Will evaluate the expression tree and return the answer
	 */
	@Override
	public int evalTree() {
		return evalTree(this);
	}
	
	/**
	 * Helper method for evalTree()
	 * will evaluate the expression tree and return the answer
	 * @param tree the TreeNode which is being evaluated
	 * @return the evaluated expression's result
	 */
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
	/**
	 * Traverse the expression tree and return a string in prefix notation
	 */
	@Override
	public String toPrefixNotation() {
		return toPrefixNotation(this);
	}
	
	/**
	 * Helper method for toPrefixNotation
	 * Traverse the expression tree and return a string in prefix notation
	 * @param tree the TreeNode whose prefix expression is returned
	 * @return the prefix expression representing the given TreeNode
	 */
	private String toPrefixNotation(TreeNode tree) {
		String str = ""+tree.getValue();
		if(tree.getLeft() != null)
			str += " " + toPrefixNotation(tree.getLeft());
		if(tree.getRight() != null)
			str += " " + toPrefixNotation(tree.getRight());
		return str;
	}

	/**
	 * Traverse the expression tree and return a string in infix notation
	 */
	@Override
	public String toInfixNotation() {
		return toInfixNotation(this);
	}
	
	/**
	 * Helper method for toInfixNotation
	 * Traverse the expression tree and return a string in infix notation
	 * @param tree the TreeNode whose infix expression is returned
	 * @return the infix expression representing the given TreeNode
	 */
	private String toInfixNotation(TreeNode tree) {
		String str = "";
		if(tree.getLeft() != null)
			str += toInfixNotation(tree.getLeft()) + " ";		
		str += tree.getValue();
		if(tree.getRight() != null)
			str += " " + toInfixNotation(tree.getRight());
		return str;
	}
	
	/**
	 * Traverse the expression tree and return a string in postfix notation
	 */
	@Override
	public String toPostfixNotation() {
		return toPostfixNotation(this);
	}
	
	/**
	 * Helper method for toPostfixNotation
	 * Traverse the expression tree and return a string in postfix notation
	 * @param tree the TreeNode whose postfix expression is returned
	 * @return the postfix expression representing the given TreeNode
	 */
	private String toPostfixNotation(TreeNode tree) {
		String str = "";
		if(tree.getLeft() != null)
			str += toPostfixNotation(tree.getLeft()) + " ";
		if(tree.getRight() != null)
			str += toPostfixNotation(tree.getRight()) + " ";
		return str + tree.getValue();
	}
	////
	
	/**
	 * Will take in an array of strings in postfix notation order and build and return an expression tree of type TreeNode
	 * @param exp the array of Strings, with each value to be placed into its individual vertex in the tree
	 * @return the completed tree corresponding to the given postfix expression
	 */
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
	
	/**
	 * Static version of the buildTree() method;
	 * Will take in an array of strings in postfix notation order and build and return an expression tree of type TreeNode
	 * @param exp the array of Strings, with each value to be placed into its individual vertex in the tree
	 * @return the completed tree corresponding to the given postfix expression
	 */
	public static ExpressionTree buildTreeStatic(String[] exp) {
		ExpressionTree t = new ExpressionTree(null);
		return t.buildTree(exp);
	}
	
	/**
	 * will take in an array of strings that is an expression in post-fix notation and using a stack,
	 * evaluate the expression and return the answer.
	 * (Will <strong>not</strong> pass the expression through a tree first)
	 * @param exp the array of strings representing the postfix expression
	 * @return the int evaluation of the given postfix expression
	 */
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
	
	/**
	 * Returns a String representation of this ExpressionTree
	 * Will be in the form of a postfix expression
	 * @return the postfix expression equivalent to this object
	 */
	public String toString() {
		String s = ""+toPostfixNotation();
		return s;
	}

}
