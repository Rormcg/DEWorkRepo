

public interface Expressions {

	static ExpressionTree buildTree(String[] exp);
	int evalTree();
	String toPrefixNotation();
	String toInfixNotation();
	String toPostfixNotation();
	static int postfixEval(String[] exp);
}