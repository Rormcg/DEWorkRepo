/**
 * IndexEntry
 * @author Rory McGuire
 * Represents an entry for a word in a DocumentIndex object<br>
 * Has a word attribute and a list of numbers representing the lines that the word is found on
*/

import java.util.TreeSet;

public class IndexEntry implements Comparable<IndexEntry> {
   private String word;
   private TreeSet<Integer> numsList;
   
   /**
    * Constructor that will assign w to word, and an empty TreeSet<Integer> to numsList
    * @param w the value to be assigned to word
    */
   IndexEntry(String w) {
	  if(w == null) throw new IllegalArgumentException("IndexEntry.word cannot be null");
      word = w.toUpperCase();
      numsList = new TreeSet<Integer> ();
   }
   
   /**
    * Constructor that will assign w to word, and an TreeSet<Integer> containing num to numsList
    * @param w the value to be assigned to word
    * @param num the value to be placed into numsList
    */
   IndexEntry(String w, int num) {
	  if(w == null) throw new IllegalArgumentException("IndexEntry.word cannot be null");
      word = w.toUpperCase();
      numsList = new TreeSet<Integer> ();
      add(num);
   }
   
   /**
    * Adds a number num into numsList if it is not already contained in numsList
    * @param num the value to be added into numsList
    */
   public void add(int num) {
      if(!numsList.contains(num)) numsList.add(num);
   }
   
   /**
    * Overrides the Comparable compareTo method<br>
    * Compares this to another IndexEntry by using the String.compareTo methods for the word fields for both objects
    * @param e the IndexEntry to be compared to this
    * @return int >0 if this > e, <0 if this < e, 0 if this==e
    */
   @Override
   public int compareTo(IndexEntry e) {
	   if(e == null) return 1;
	   return word.compareTo(e.word);
   }
   
   /**
    * Getter for the word field
    * @return word
    */
   public String getWord() {
      return word;
   }
   
   /**
    * Returns a String representation of this class in the format: "word: num1, num2, ..."
    * @return String representation of this IndexEntry
    */
   public String toString() {
      return word + ": " + numsList.toString().replaceAll("\\[|\\]", "");
   }
   
   
}