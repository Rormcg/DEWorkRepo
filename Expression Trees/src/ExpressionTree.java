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
	public int evalTree(TreeNode tree) {
		if(!tree.getValue().equals("+") || !tree.getValue().equals("*")) {
			return (int)tree.getValue();
		}
		if(tree.getValue() == "+") {
			return (int)tree.getLeft().getValue() + (int)tree.getRight().getValue();
		} else {
			return (int)tree.getLeft().getValue() * (int)tree.getRight().getValue();
		}
	}
	
	//TRAVERSALS//
	//traverse the expression tree and return a string in the appropriate notation
	@Override
	public String toPrefixNotation(TreeNode tree) {
//		if(!tree.getValue().equals("+") || !tree.getValue().equals("*")) {
//			return tree.getValue().toString();
//		}
		return tree.getValue() + " " + toPostfixNotation(tree.getLeft()) + " " + toPostfixNotation(tree.getRight());
	}

	@Override
	public String toInfixNotation(TreeNode tree) {
//		if(!tree.getValue().equals("+") || !tree.getValue().equals("*")) {
//			return tree.getValue().toString();
//		}
		return toPostfixNotation(tree.getLeft()) + " " + tree.getValue() + " " + toPostfixNotation(tree.getRight());
	}

	@Override
	public String toPostfixNotation(TreeNode tree) {
//		if(!tree.getValue().equals("+") || !tree.getValue().equals("*")) {
//			return tree.getValue().toString();
//		}
		return toPostfixNotation(tree.getLeft()) + " " + toPostfixNotation(tree.getRight()) + " " + tree.getValue();
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
			if(n == "*" || n == "+") {
				tree = new ExpressionTree(n);
				Object a = stack.pop(),
						b = stack.pop();
				if(!(a instanceof TreeNode)) {
					a = new ExpressionTree(a);
				}
				if(!(b instanceof TreeNode)) {
					b = new ExpressionTree(b);
				}
				tree.setRight((ExpressionTree)a);
				tree.setLeft((ExpressionTree)b);
				stack.push(tree);
			} else {
				stack.push(tree);
			}
		}
		return tree;
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
