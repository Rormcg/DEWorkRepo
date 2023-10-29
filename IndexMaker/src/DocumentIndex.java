/**
 * DocumentIndex
 * @author Rory McGuire
 * Represents a collection of IndexEntrys<br>
 * Extends TreeMap&lt;String, IndexEntry&gt;, where the String shows the word associated with the IndexEntry
*/

import java.util.TreeMap;

public class DocumentIndex extends TreeMap<String, IndexEntry> {
   
   private static final long serialVersionUID = 1L;
   
   /**
    * No-args constructor - calls the no-args constructor of the superclass TreeMap
    */
   DocumentIndex() {
      super();
   }
   
   /**
    * Adds a number into the IndexEntry with word, if none exists create a new IndexEntry with num in it
    * @param word the word associated with the IndexEntry
    * @param num the number to be added into the IndexEntry
    */
   public void addWord(String word, int num) {
	   if(word == null) return;
	   
       if(keySet().contains(word.toUpperCase())) {
    	   get(word.toUpperCase()).add(num);
       } else {
    	   put(word.toUpperCase(), new IndexEntry(word.toUpperCase(), num));
       }
   }
   
   /**
    * Adds all of the words in a certain line of text into this
    * @param str the String to be broken up each word to be added to this
    * @param num the line number of the String (the number to be placed into the IndexEntrys)
	*/
	public void addAllWords(String str, int num) {   	
		if(str == null) return;
		
		String[] s = str.split("\\W+");
		for(String a: s) {
			if(!a.equals("")) {
				addWord(a, num);
			}
		}
	}
   
}