package spelling;

import java.util.*;

/**
 * A trie data structure that implements the Dictionary and the AutoComplete ADT
 *
 * @author dpeterson
 */
public class AutoCompleteMatchCase implements Dictionary, AutoComplete {

    private final TrieNode root;
    private int size;


    public AutoCompleteMatchCase() {
        root = new TrieNode();
    }


    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should convert the
     * string to all lower case before you insert it.
     * <p>
     * This method adds a word by creating and linking the necessary trie nodes
     * into the trie, as described outlined in the videos for this week. It
     * should appropriately use existing nodes in the trie, only creating new
     * nodes when necessary. E.g. If the word "no" is already in the trie,
     * then adding the word "now" would add only one additional node
     * (for the 'w').
     *
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    public boolean addWord(String word) {
        //TODO: Implement this method.
        String lower = word.toLowerCase(Locale.ROOT);
        TrieNode trieNode = root;

        //convert String to char array
        char[] charArray = lower.toCharArray();
        char firstCharacter = '\0';
        if (word.length() > 0)
            firstCharacter = word.charAt(0);

        for (char c : charArray) {
            TrieNode tn = trieNode.getChild(c);
            if (tn == null) {
                trieNode = trieNode.insert(c);
            } else {
                trieNode = tn;
                if (tn.getText().equals(lower)) {
                    if (!tn.endsWord()) {    // Found word existing but is it marked as a word already?
                        break;
                    }
                    if (Character.isUpperCase(firstCharacter))
                        trieNode.setFirstCapital(true);
                    else trieNode.setFirstLower(true);
                    return false;   // Word already exists
                }
            }
        }
        trieNode.setEndsWord(true);
        if (Character.isUpperCase(firstCharacter))
            trieNode.setFirstCapital(true);
        else trieNode.setFirstLower(true);
        size++;
        return true;
    }


    /**
     * Returns whether the string is a word in the trie, using the algorithm
     * described in the videos for this week.
     *
     * @param s string word to determine if the word is in the dictionary Trie
     * @return true if the word is found in the dictionary Trie (not just the string, but it was a true word inserted)
     */
    @Override
    public boolean isWord(String s) {
        // TODO: Implement this method
        char firstCharacter = '\0';
        if (s.length() > 0)
            firstCharacter = s.charAt(0);
        String lower = s.toLowerCase(Locale.ROOT);
        TrieNode trieNode = root;
        //convert String to char array
        char[] charArray = lower.toCharArray();

        for (char c : charArray) {
            TrieNode tn = trieNode.getChild(c);
            if (tn == null) {
                return false;
            } else {
                trieNode = tn;
                // Check that it matches and that it was a true word inserted into dictionary (i.e. not partial word)
                if (tn.getText().equals(lower) && tn.endsWord()) {
                    // if word to lookup is lowercase, and we never inserted a lowercase version of this word, fail it
                    return !Character.isLowerCase(firstCharacter) || tn.isFirstLower();
                }
            }
        }
        return false;
    }


    /**
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions the shortest legal completions
     * of the prefix string. All legal completions must be valid words in the
     * dictionary. If the prefix itself is a valid word, it is included
     * in the list of returned words.  A BFS Search will lead to returns in order
     * of increasing word length.
     * <p>
     * The list of completions must contain
     * all the shortest completions, but when there are ties, it may break
     * them in any order. For example, if  the prefix string is "ste" and
     * only the words "step", "stem", "stew", "steer" and "steep" are in the
     * dictionary, when the user asks for 4 completions, the list must include
     * "step", "stem" and "stew", but may include either the word
     * "steer" or "steep".
     * <p>
     * If this string prefix is not in the trie, it returns an empty list. An empty string prefix
     * returns strings under root.
     * This method should implement the following algorithm:
     * 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
     * empty list
     * 2. Once the stem is found, perform a BFS (i.e. breadth first search) to generate completions
     * using the following algorithm:
     * Create a queue (LinkedList) and add the node that completes the stem to the back
     * of the list.
     * Create a list of completions to return (initially empty)
     * While the queue is not empty, and you don't have enough completions:
     * remove the first Node from the queue
     * If it is a word, add it to the completions list
     * Add all of its child nodes to the back of the queue
     * Return the list of completions
     *
     * @param prefix         The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        // TODO: Implement this method

        List<String> completions = new ArrayList<>();
        String lower = prefix.toLowerCase(Locale.ROOT);
        TrieNode curr = root;
        Character firstCharacter = '\0';
        if (lower.equals("")) {
            completions = bfsSearch(curr, numCompletions, firstCharacter);

        } else {
            firstCharacter = prefix.charAt(0);
            for (char c : lower.toCharArray()) {
                TrieNode tn = curr.getChild(c);
                if (tn == null) {
                    return completions;
                } else {
                    curr = tn;
                    if (tn.getText().equals(lower)) {
                        completions = bfsSearch(curr, numCompletions, firstCharacter);
                        break;
                    }
                }
            }
        }
        return completions;
    }

    /**
     * Conduct a BFS (breadth first search) to find the number (predictCount) of words that are under the start node.
     *
     * @param start           The node to start the prediction search at.
     * @param predictionCount the number of words (up to) requested to be returned
     * @return Up to predictionCount words under the start node in a List.
     */
    private List<String> bfsSearch(TrieNode start, int predictionCount, Character firstCharacter) {
        Queue<TrieNode> toExplore = new LinkedList<>();
        List<String> completions = new ArrayList<>();
        toExplore.add(start);
        int foundCount = 0;

        while (!toExplore.isEmpty() && foundCount < predictionCount) {
            TrieNode curr = toExplore.remove();
            if (curr.endsWord()) {
                // Check the upper/lower case rule
                if (!(Character.isUpperCase(firstCharacter) && !curr.isFirstCapital())) {
                    completions.add(curr.getText());
                    foundCount++;
                }
            }

            for (Character character : curr.getValidNextCharacters()) {
                toExplore.add(curr.getChild(character));
            }
        }
        return completions;
    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        //TODO: Implement this method
        return size;
    }

    // For debugging
    public void printTree() {
        printNode(root);
    }

    /**
     * Do a pre-order traversal from this node down
     */
    public void printNode(TrieNode curr) {
        if (curr == null)
            return;

        System.out.println(curr.getText());

        TrieNode next;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }


}