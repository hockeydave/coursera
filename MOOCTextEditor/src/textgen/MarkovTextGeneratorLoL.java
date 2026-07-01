package textgen;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team (basic file)
 * @author dcpeterson TODOs
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {


    // The list of words with their next words
    private Map<String, ListNode> listNodeMap;

    // The starting "word"
    private String starter;

    // The random number generator
    private final Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator) {
        listNodeMap = new HashMap<>();
        starter = null;
        rnGenerator = generator;
    }

    /**
     * Take a string that either contains only alphabetic characters,
     * or only sentence-ending punctuation.  Return true if the string
     * contains only alphabetic characters, and false if it contains
     * end of sentence punctuation.
     *
     * @param tok The string to check
     * @return true if tok is a word, false if it is punctuation.
     */
    private boolean isWord(String tok) {
        // Note: This is a fast way of checking whether a string is a word
        // You probably don't want to change it.
        return !(tok.contains("!") || tok.contains(".") || tok.contains("?"));
    }

    /**
     * Returns the tokens that match the regex pattern from the document
     * text string.
     *
     * @return A List of tokens from the document text that match the regex
     * pattern
     */
    private List<String> getTokens(String text) {
        // Regex to break out words from a String of text
        String pattern = "[!?.]+|[a-zA-Z]+";
        ArrayList<String> tokens = new ArrayList<>();
        Pattern tokSplitter = Pattern.compile(pattern);
        Matcher m = tokSplitter.matcher(text);

        while (m.find()) {
            tokens.add(m.group());
        }

        return tokens;
    }

    protected List<String> getWords(String text) {
        List<String> tokens = getTokens(text);
        List<String> words = new ArrayList<>();
        for (String token : tokens) {
            if (isWord(token)) {
                words.add(token);
            }
        }
        return words;
    }


    /**
     * Train the generator by adding the sourceText
     * set "starter" to be the first word in the text
     * set "prevWord" to be starter
     * for each word "w" in the source text starting at the second word
     * check to see if "prevWord" is already a node in the list
     * if "prevWord" is a node in the list
     * add "w" as a nextWord to the "prevWord" node
     * else
     * add a node to the list with "prevWord" as the node's word
     * add "w" as a nextWord to the "prevWord" node
     * set "prevWord" = "w"
     * <p>
     * add starter to be a next word for the last word in the source text
     */
    @Override
    public void train(String sourceText) throws IllegalStateException {
        // TODO: Implement this method
        // If train called twice in a row ignore.
        if(starter == null) {
            listNodeMap = new HashMap<>();
            if (sourceText.length() > 0) {
                //List<String> words = getWords(sourceText);
                String[] w = sourceText.split("\\s+");

                List<String> words = new ArrayList(Arrays.asList(w));
                if (words.size() > 0) {
                    starter = words.get(0);
                    words.add(starter);
                    String prevWord = starter;
                    ListNode node;
                    String word;
                    for (int i = 1; i < words.size(); i++) {
                        word = words.get(i);
                        if ((node = listNodeMap.get(prevWord)) == null) {
                            node = new ListNode(prevWord);
                            node.addNextWord(word);
                            listNodeMap.put(prevWord, node);
                        } else {
                            node.addNextWord(word);
                        }
                        prevWord = word;
                    }
                }
            }
        }
    }

    /**
     * Generate the number of words requested.
     * set "currWord" to be the starter word
     * set "output" to be ""
     * add "currWord" to output
     * while you need more words
     * find the "node" corresponding to "currWord" in the list
     * select a random word "w" from the "wordList" for "node"
     * add "w" to the "output"
     * set "currWord" to be "w"
     * increment number of words added to the list
     *
     * @throws IllegalStateException when called before training
     */
    @Override
    public String generateText(int numWords) throws IllegalStateException {
        // TODO: Implement this method

        String currWord = starter;
        StringBuilder output = new StringBuilder();

        if (listNodeMap.size() > 0 && numWords > 0 && starter != null) {
            output.append(currWord).append(" ");
            for (int i = 0; i < numWords - 1; i++) {
                ListNode node = listNodeMap.get(currWord);
                String randomWord = node.getRandomNextWord(rnGenerator);
                output.append(randomWord).append(" ");
                currWord = randomWord;
            }
        }
        return output.toString();
    }


    // Can be helpful for debugging
    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        for (ListNode n : listNodeMap.values()) {
            toReturn.append(n.toString());
        }
        return toReturn.toString();
    }

    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
        listNodeMap = new HashMap<>();
        starter = null;
        train(sourceText);
    }

    // TODO: Add any private helper methods you need here.

    Map<String, ListNode> getListNodeMap() {
        return listNodeMap;
    }

    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println("Test1: " + textString);
        gen.train(textString);
        System.out.println("MarkovGen:  " + gen);
        System.out.println("MarkovGenText:  " + gen.generateText(20));
        System.out.println("-----------------------------------------\n");
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println("Text2:  " + textString2);
        gen.retrain(textString2);
        System.out.println("MarkovGen2:  " + gen);
        System.out.println("MarkovGen2Text:  " + gen.generateText(20));
    }

}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private final String word;

    // The next words that could follow it
    private final List<String> nextWords;

    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<>();
    }

    public String getWord() {
        return word;
    }

    public void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }

    /**
     * @param generator Random number generator created and pre-seeded
     *                  Returns an effectively unlimited stream of pseudorandom int values, each conforming to the given
     *                  origin (inclusive) and bound (exclusive).
     * @return String for the next word randomly chosen.
     */
    public String getRandomNextWord(Random generator) {
        // TODO: Implement this method
        // Returns a pseudorandom, uniformly distributed int value between 0 (inclusive)
        // and the specified value (exclusive), drawn from this random number generator's sequence.
        int index = generator.nextInt(nextWords.size());
        return nextWords.get(index);
    }

    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(word).append(": ");
        for (String s : nextWords) {
            toReturn.append(s).append("->");
        }
        return toReturn.append("\n").toString();
    }

}


