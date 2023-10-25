/*
Rory McGuire
1/27/2023
Lab: Index Maker
*/

import java.util.TreeMap;

public class DocumentIndex extends TreeMap<String, IndexEntry>{
   
   private static final long serialVersionUID = 1L;

   DocumentIndex() {
      super();
   }

   public void addWord(String word, int num) {
       if(keySet().contains(word.toUpperCase())) {
    	   get(word.toUpperCase()).add(num);
       } else {
    	   put(word.toUpperCase(), new IndexEntry(word.toUpperCase(), num));
       }
   }

   public void addAllWords(String str, int num) {
		String[] s = str.split("\\W+");
		for(String a: s) {
		    if(!a.equals("")) {
		        addWord(a, num);
		    }
		}
   }
   
}