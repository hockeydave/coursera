/**
 *
 */
package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class NearbyWords implements SpellingSuggest {
    // THRESHOLD to determine how many words to look through when looking
    // for spelling suggestions (stops prohibitively long searching)
    // For use in the Optional Optimization in Part 2.
    private static final int THRESHOLD = 5000;

    private final Dictionary dict;

    public NearbyWords(Dictionary dict) {
        this.dict = dict;
    }

    /** Return the list of Strings that are one modification away
     * from the input string  to become another valid word.
     * @param s The original String
     * @param wordsOnly controls whether to return only words or any String
     * list of Strings which are nearby the original string
     */
    public List<String> distanceOne(String s, boolean wordsOnly) {
        List<String> retList = new ArrayList<>();
        insertions(s, retList, wordsOnly);
        substitution(s, retList, wordsOnly);
        deletions(s, retList, wordsOnly);
        return retList;
    }


    /** Add to the currentList Strings that are one character mutation away
     * from the input string  to become another valid word.
     * @param s The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly controls whether to return only words or any String
     * List<String> currentList is modified
     */
    public void substitution(String s, List<String> currentList, boolean wordsOnly) {
        // for each letter in the String (word) s and for all possible replacement characters (alphabet)
        for (int index = 0; index < s.length(); index++) {
            for (int charCode =  'a'; charCode <=  'z'; charCode++) {
                // use StringBuffer for an easy interface for permuting the letters in the String
                StringBuilder sb = new StringBuilder(s);
                sb.setCharAt(index, (char) charCode);

                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if (!currentList.contains(sb.toString()) && (!wordsOnly || dict.isWord(sb.toString())) && !s.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }

    /** Add to the currentList Strings that are one character insertion away
     * from the input string to become another valid word.
     * @param s The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly controls whether to return only words or any String
     * List<String> currentList is modified
     */
    public void insertions(String s, List<String> currentList, boolean wordsOnly) {
        // TODO: Implement this method
        // for each letter in the String (word) s and for all possible prepend/insert/append characters (alphabet)
        for (int index = 0; index < s.length() + 1; index++) {
            for (int charCode =  'a'; charCode <=  'z'; charCode++) {
                // use StringBuffer for an easy interface for permuting the letters in the String
                StringBuilder sb = new StringBuilder(s);
                sb.insert(index, (char) charCode);

                // if the item isn't already in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if (!currentList.contains(sb.toString()) && (!wordsOnly || dict.isWord(sb.toString())) && !s.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }

    /** Add to the currentList Strings that are one character deletion away
     * from the input string  to become another valid word.
     * @param s The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly controls whether to return only words or any String
     * List<String> currentList is modified
     */
    public void deletions(String s, List<String> currentList, boolean wordsOnly) {
        // TODO: Implement this method
        // for each letter in the String (word) s and for all possible replacement characters (alphabet)
        for (int index = 0; index < s.length(); index++) {

            // use StringBuffer for an easy interface for permuting the letters in the String
            StringBuilder sb = new StringBuilder(s);
            sb.deleteCharAt(index);

            // if the item isn't already in the list, isn't the original string, and
            // (if wordsOnly is true) is a real word, add to the list
            if (!currentList.contains(sb.toString()) && (!wordsOnly || dict.isWord(sb.toString())) && !s.equals(sb.toString())) {
                currentList.add(sb.toString());
            }

        }
    }

    /** Return a list of suggestions for a word up to the limit passed in.
     * The suggestions returned should be 1 character insert/replace/delete
     * from the input word.
     * Input:  word for which to provide number of spelling suggestions
     * Input:  number of maximum suggestions to provide
     * Output: list of spelling suggestions
     *
     * Create a queue to hold words to explore
     * Create a visited set to avoid looking at the same String repeatedly
     * Create list of real words to return when finished
     *
     * Add the initial word to the queue and visited
     *
     * while the queue has elements, and we need more suggestions
     *   remove the word from the start of the queue and assign to curr
     *   get a list of neighbors (strings one mutation away from curr)
     *   for each n in the list of neighbors
     *      if n is not visited
     *        add n to the visited set
     *        add n to the back of the queue
     *        if n is a word in the dictionary
     *           add n to the list of words to return
     *
     * return the list of real words
     * @param word The misspelled word
     * @param numSuggestions is the maximum number of suggestions to return
     * @return the list of spelling suggestions
     */
    @Override
    public List<String> suggestions(String word, int numSuggestions) {

        // initial variables
        List<String> queue = new LinkedList<>();     // String to explore
        HashSet<String> visited = new HashSet<>();   // to avoid exploring the same
        // string multiple times
        List<String> retList = new LinkedList<>();   // words to return
        if(word == null || !dict.isWord(word))
            return retList;

        // insert first node
        queue.add(word);
        visited.add(word);

        // TODO: Implement the remainder of this method, see assignment for algorithm
        int wordsTested = 0;
        while (!queue.isEmpty() && retList.size() < numSuggestions && wordsTested < THRESHOLD) {
            String curr = queue.remove(0);
            List<String> neighbors = distanceOne(curr, true);
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    wordsTested++;
                    visited.add(neighbor);
                    queue.add(neighbor);
                    if (!retList.contains(neighbor) && (dict.isWord(neighbor)) && !word.equals(neighbor)) {
                        retList.add(neighbor);
                    }
                }
            }
        }

        return retList;

    }
    public Dictionary getDict() {
        return dict;
    }

    public static void main(String[] args) {
        // basic testing code to get started
        String word = "i";
        // Pass NearbyWords any Dictionary implementation you prefer
        Dictionary d = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(d, "data/dict.txt");
        NearbyWords w = new NearbyWords(d);
        List<String> l = w.distanceOne(word, true);
        System.out.println("One away word Strings for for \"" + word + "\" are:");
        System.out.println(l + "\n");

        word = "be";
        l = w.distanceOne(word, true);
        System.out.println("One away word Strings for for \"" + word + "\" are:");
        System.out.println(l + "\n");

        word = "kangaro";
        l = w.distanceOne(word, true);
        System.out.println("One away word Strings for for \"" + word + "\" are:");
        System.out.println(l + "\n");

        word = "tailo";
        List<String> suggest = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest);

        word = "fare";
        suggest = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest);

        word = "kangaro";   // Requires THRESHOLD
        suggest = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest);

        d = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(d, "test_cases/dict2.txt");
        w = new NearbyWords(d);
        word = "dag";
        suggest = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest);

    }

}
