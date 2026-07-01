package document;

/**
 * A class that represents a text document
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

    private final String text;
    // Flesch score = 206.835 - 1.015(words/sentences)- 84.6 (syllables/words)
    private static final double fleschConstant1 = 206.835;
    private static final double fleschConstant2 = 1.015;
    private static final double fleschConstant3 = 84.6;

    /**
     * Create a new document from the given text.
     * Because this class is abstract, this is used only from subclasses.
     *
     * @param text The text of the document.
     */
    protected Document(String text) {
        this.text = text;
    }

    /**
     * Returns the tokens that match the regex pattern from the document
     * text string.
     *
     * @param pattern A regular expression string specifying the
     *                token pattern desired
     * @return A List of tokens from the document text that match the regex
     * pattern
     */
    protected List<String> getTokens(String pattern) {
        ArrayList<String> tokens = new ArrayList<>();
        Pattern tokSplitter = Pattern.compile(pattern);
        Matcher m = tokSplitter.matcher(text);

        while (m.find()) {
            tokens.add(m.group());
        }

        return tokens;
    }

    /**
     * This is a helper function that returns the number of syllables
     * in a word.  You should write this and use it in your
     * BasicDocument class.
     * <p>
     * You will probably NOT need to add a countWords or a countSentences
     * method here.  The reason we put countSyllables here because we'll
     * use it again next week when we implement the EfficientDocument class.
     * <p>
     * For reasons of efficiency you should not create Matcher or Pattern
     * objects inside this method. Just use a loop to loop through the
     * characters in the string and write your own logic for counting
     * syllables.
     *
     * @param word The word to count the syllables in
     * @return The number of syllables in the given word, according to
     * this rule: Each contiguous sequence of one or more vowels is a syllable,
     * with the following exception: a lone "e" at the end of a word
     * is not considered a syllable unless the word has no other syllables.
     * You should consider y a vowel.
     */
    protected int countSyllables(String word) {
        // TODO: Implement this method so that you can call it from the
        // getNumSyllables method in BasicDocument (module 2) and
        // EfficientDocument (module 3).
        int syllables = 0;
        int len = word.length();
        String wordLower = word.toLowerCase(Locale.ROOT);
        for (int i = 0; i < len; i++) {
            char c = wordLower.charAt(i);
            if (isVowel(c)) {
                if (i > 0) {
                    char b = wordLower.charAt(i - 1);
                    if (!isVowel(b)) {
                        if (c != 'e' || i < len - 1 || syllables == 0)
                            syllables++;
                    }
                } else syllables++;
            }
        }
        return syllables;
    }

    /**
     * Determine if the passed in char is a vowel or not
     *
     * @param c char to determine if a vowel
     * @return true if c is a vowel, false otherwise.
     */
    private boolean isVowel(char c) {
        String vowels = "aeiouy";
        return vowels.contains("" + c);
    }

    /**
     * A method for testing
     *
     * @param doc       The Document object to test
     * @param syllables The expected number of syllables
     * @param words     The expected number of words
     * @param sentences The expected number of sentences
     * @return true if the test case passed.  False otherwise.
     */
    public static boolean testCase(Document doc, int syllables, int words, int sentences) {
        System.out.println("Testing text: ");
        System.out.print(doc.getText() + "\n....");
        boolean passed = true;
        int syllFound = doc.getNumSyllables();
        int wordsFound = doc.getNumWords();
        int sentFound = doc.getNumSentences();
        if (syllFound != syllables) {
            System.out.println("\nIncorrect number of syllables.  Found " + syllFound
                    + ", expected " + syllables);
            passed = false;
        }
        if (wordsFound != words) {
            System.out.println("\nIncorrect number of words.  Found " + wordsFound
                    + ", expected " + words);
            passed = false;
        }
        if (sentFound != sentences) {
            System.out.println("\nIncorrect number of sentences.  Found " + sentFound
                    + ", expected " + sentences);
            passed = false;
        }

        if (passed) {
            System.out.println("passed.\n");
        } else {
            System.out.println("FAILED.\n");
        }
        return passed;
    }


    /**
     * Return the number of words in this document
     */
    public abstract int getNumWords();

    /**
     * Return the number of sentences in this document
     */
    public abstract int getNumSentences();

    /**
     * Return the number of syllables in this document
     */
    public abstract int getNumSyllables();

    /**
     * Return the entire text of this document
     */
    public String getText() {
        return this.text;
    }

    /**
     * Just a utility double rounding tool.
     *
     * @param value     double to be rounded to the precision
     * @param precision the precision to round the double
     * @return the double rounded to it's precision.
     */
    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


    /**
     * Determine the Flesch readability score of this document
     * The Flesch reading ease score indicates the understandability of a passage with a number that ranges from 0 to 100.
     * It shows how difficult it is to understand the content. The higher scores mean that the content is easy to read
     * and understand.
     * score = 206.835 - 1.015 × (total words ÷ total sentences) - 84.6 × (total syllables ÷ total words).
     *
     * @return the calculated Flesch score.
     */
    public double getFleschScore() {
        // TODO: You will play with this method in week 1, and
        // then implement it in week 2

        double z = (double) fleschConstant1
                - (fleschConstant2 * ((double) getNumWords() / (double) getNumSentences()))
                - (fleschConstant3 * ((double) getNumSyllables() / (double) getNumWords()));
        return round(z, 1);
    }


}
