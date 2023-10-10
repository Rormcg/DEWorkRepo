/*
Rory McGuire
1/27/2023
Lab: Index Maker
*/

import java.util.ArrayList;
import java.util.TreeMap;

public class DocumentIndex extends TreeMap<String, IndexEntry>{
   
   DocumentIndex() {
      super();
   }
   
   DocumentIndex(int i) {
      super(i);
   }
   
   private int findWord(String w) {
       w = w.toUpperCase();
       for(int i = 0; i < size(); i ++) {
           if(get(i).getWord().equals(w)) return i;
       }
       return -1;
   }

   public void addWord(String word, int num) {
       if(findWord(word) > -1) {
           //System.out.println("h");
           get(findWord(word)).add(num);
       } else {
            for(int i = 0; i < size(); i ++) {
                if(word.toUpperCase().compareTo(get(i).getWord()) < 0) {
                    add(i, new IndexEntry(word));
                    get(i).add(num);
                    return;
                }
                if(i == size() - 1) {
                    add(new IndexEntry(word));
                    get(size() - 1).add(num);
                    return;
                }
            }
            if(size() < 1) {
                add(new IndexEntry(word));
                get(0).add(num);
            }
       }
   }

   public void addAllWords(String str, int num) {
       /*String thisWord = "";
       boolean addingToWord = false;
       for(int i = 0; i < str.length(); i ++) {
           if(!Character.isLetterOrDigit(str.charAt(i))) {
               if(addingToWord) get(i).addWord(thisWord);
               addingToWord = false;
           } else {
               thisWord += str.charAt(i);
           }
       }*/
        String[] s = str.split("\\W+");
        for(String a: s) {
            if(!a.equals("")) {
                addWord(a, num);
                //System.out.print(a + ":" + num + " ");
            }
        }
        //System.out.println();
   }
   
}