/*
Rory Mcguire
1/25/2023
Lab: IndexMaker
*/

import java.util.TreeSet;

public class IndexEntry {
   private String word;
   private TreeSet<Integer> numsList;
   
   IndexEntry(String w) {
      word = w.toUpperCase();
      numsList = new TreeSet<Integer> ();
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

   public static void main(String[] args) {
       /*IndexEntry in = new IndexEntry("hello");
       in.add(1);
       System.out.println(in);*/
   }
   
   public String getWord() {
      return word;
   }
   
   public String toString() {
      String str = word + " ";
      for(int i = 0; i < numsList.size(); i++) {
         str += numsList.get(i);
         if(i < numsList.size() - 1) str += ", ";
      }
      return str;
   }
   
   
}