/**
 * @author Rory McGuire
 */

import java.util.Stack;

public class BrowserModel {
	private Stack<Integer> forwardLinks;
	private Stack<Integer> backLinks;
	private int curIndex;
	private BrowserView view;
	
	public BrowserModel(BrowserView view) {
		this.view = view;
		curIndex = 0;
		this.view.update(curIndex);
		
		forwardLinks = new Stack<Integer>();
		backLinks = new Stack<Integer>();
	}
	
	public void back() {
		forwardLinks.push(curIndex);
		curIndex = backLinks.pop();
	}
	
	public void forward() {
		backLinks.push(curIndex);
		curIndex = forwardLinks.pop();
	}
	
	public void followLink(int n) {
		backLinks.push(curIndex);
		curIndex = n;
		
		clearForward();
		
		view.update(n);
	}
	
	private void clearForward() {
		while(!forwardLinks.empty()) {
			forwardLinks.pop();
		}
	}
	
	public void home() {
		
	}
	
	public boolean hasBack() {
		return !backLinks.empty();
	}
	
	public boolean hasForward() {
		return !forwardLinks.empty();
	}
}
