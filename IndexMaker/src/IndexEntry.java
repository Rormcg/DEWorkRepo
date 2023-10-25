/*
Rory Mcguire
1/25/2023
Lab: IndexMaker
*/

import java.util.TreeSet;

public class IndexEntry implements Comparable<IndexEntry> {
   private String word;
   private TreeSet<Integer> numsList;
   
   IndexEntry(String w) {
      word = w.toUpperCase();
      numsList = new TreeSet<Integer> ();
   }
   
   IndexEntry(String w, int num) {
      word = w.toUpperCase();
      numsList = new TreeSet<Integer> ();
      add(num);
   }
   
   public void add(int num) {
      boolean a = true;
      for(Integer i: numsList) {
         if(i.equals(num)) {
            a = false;
            break;
         }
      }
      if(a) numsList.add(num);
   }
   
   @Override
   public int compareTo(IndexEntry e) {
	   return word.compareTo(e.word);
   }
   
   public String getWord() {
      return word;
   }
   
   public String toString() {
      return word + ": " + numsList.toString();
   }
   
   
}