/**
 *
 */
package spelling;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * WPTree implements WordPath by dynamically creating a tree of words during a Breadth First
 * Search of Nearby words to create a path between two words. 
 *
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class WPTree implements WordPath {

    // this is the root node of the WPTree
    private WPTreeNode root;
    // used to search for nearby Words
    private final NearbyWords nw;
    private static final int THRESHOLD = 5000;


    // This constructor is used by the Text Editor Application
    // You'll need to create your own NearbyWords object here.
    public WPTree() {
        this.root = null;
        // TODO initialize a NearbyWords object
        Dictionary dictionary = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(dictionary, "data/dict.txt");
        this.nw = new NearbyWords(dictionary);
    }

    //This constructor will be used by the grader code
    public WPTree(NearbyWords nw) {
        this.root = null;
        this.nw = nw;
    }

    /**
     * Input:  word1 which is the start word
     * Input:  word2 which is the target word
     * Output: list of a path from word1 to word2 (or null)
     * Use BFS (Breath First Search) algorithm to find the path.
     *
     * Create a queue of WPTreeNodes to hold words to explore
     * Create a visited set to avoid looking at the same word repeatedly
     *
     * Set the root to be a WPTreeNode containing word1
     * Add the initial word to visited
     * Add root to the queue
     *
     * while the queue has elements, and we have not yet found word2
     *   remove the node from the start of the queue and assign to curr
     *   get a list of real word neighbors (one mutation from curr's word)
     *   for each n in the list of neighbors
     *      if n is not visited
     *        add n as a child of curr
     *        add n to the visited set
     *        add the node for n to the back of the queue
     *        if n is word2
     *           return the path from child to root
     *
     * return null as no path exists
     * @param word1 The first word
     * @param word2 The second word
     * @return list of Strings which are the path from word1 to word2
     *          including word1 and word2
     */
    public List<String> findPath(String word1, String word2) {
        // TODO: Override and implement this method.
        List<WPTreeNode> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();   // to avoid exploring the same word more than once
        List<String> path = new LinkedList<>();   // words to return
        // Make sure word2 is in the dict. If it's not we'll never find a path
        if (word1 == null || word2 == null) return path;

        // insert first node
        root = new WPTreeNode(word1, null);
        queue.add(root);
        visited.add(word1);
        int wordsTested = 0;
        while (!queue.isEmpty() && wordsTested < THRESHOLD) {
            WPTreeNode curr = queue.remove(0);
            String currWord = curr.getWord();
            List<String> permutations = nw.distanceOne(currWord, true);
            for (String permutation : permutations) {
                wordsTested++;
                if (!visited.contains(permutation)) {
                    WPTreeNode childNode = curr.addChild(permutation);
                    visited.add(permutation);
                    queue.add(childNode);
                    if (permutation.equals(word2)) {
                        return childNode.buildPathToRoot();
                    }
                }
            }
        }
        return path;
    }

    // Method to print a list of WPTreeNodes (useful for debugging)
    private String printQueue(List<WPTreeNode> list) {
        StringBuilder ret = new StringBuilder("[ ");

        for (WPTreeNode w : list) {
            ret.append(w.getWord()).append(", ");
        }
        ret.append("]");
        return ret.toString();
    }

    public static void main(String[] args) {
        WPTree wpTree = new WPTree();
        String w1 = "time";
        String w2 = "theme";
        List<String> path = wpTree.findPath(w1, w2);
        System.out.println(w1 + " to " + w2 + " path:  " + path);
        w1 = "spell";
        w2 = "mine";
        path = wpTree.findPath(w1, w2);
        System.out.println(w1 + " to " + w2 + " path:  " + path);
    }
}

/* Tree Node in a WordPath Tree. This is a standard tree with each
 * node having any number of possible children.  Each node should only
 * contain a word in the dictionary and the relationship between nodes is
 * that a child is one character mutation (deletion, insertion, or
 * substitution) away from its parent
 */
class WPTreeNode {

    private final String word;
    private final List<WPTreeNode> children;
    private final WPTreeNode parent;

    /** Construct a node with the word w and the parent p
     *  (pass a null parent to construct the root)  
     * @param w The new node's word
     * @param p The new node's parent
     */
    public WPTreeNode(String w, WPTreeNode p) {
        this.word = w;
        this.parent = p;
        this.children = new LinkedList<>();
    }

    /** Add a child of a node containing the String s
     *  precondition: The word is not already a child of this node
     * @param s The child node's word
     * @return The new WPTreeNode
     */
    public WPTreeNode addChild(String s) {
        WPTreeNode child = new WPTreeNode(s, this);
        this.children.add(child);
        return child;
    }

    /** Get the list of children of the calling object
     * @return List of WPTreeNode children
     */
    public List<WPTreeNode> getChildren() {
        return this.children;
    }

    /** Allows you to build a path from the root node to 
     *  the calling object
     * @return The list of strings starting at the root and 
     *         ending at the calling object
     */
    public List<String> buildPathToRoot() {
        WPTreeNode curr = this;
        List<String> path = new LinkedList<>();
        while (curr != null) {
            path.add(0, curr.getWord());
            curr = curr.parent;
        }
        return path;
    }

    /** Get the word for the calling object
     *
     * @return Getter for calling object's word
     */
    public String getWord() {
        return this.word;
    }

    /** toString method
     *
     * @return The string representation of a WPTreeNode
     */
    public String toString() {
        StringBuilder ret = new StringBuilder("Word: " + word + ", parent = ");
        if (this.parent == null) {
            ret.append("null.\n");
        } else {
            ret.append(this.parent.getWord()).append("\n");
        }
        ret.append("[ ");
        for (WPTreeNode curr : children) {
            ret.append(curr.getWord()).append(", ");
        }
        ret.append(" ]\n");
        return ret.toString();
    }

}

