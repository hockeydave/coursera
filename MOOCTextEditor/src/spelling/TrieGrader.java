package spelling;

import java.io.PrintWriter;
import java.lang.StringBuilder;
import java.util.List;

public class TrieGrader {
    StringBuilder feedback;


    public TrieGrader() {
        feedback = new StringBuilder();
    }


    public static void main(String[] args) {
        TrieGrader g = new TrieGrader();

        PrintWriter out;
        try {
            out = new PrintWriter("grader_output/module4.part2.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            AutoCompleteDictionaryTrie ac = new AutoCompleteDictionaryTrie();

            g.testAddWords(ac);

            g.testWordsInOut(ac);

            g.testPredictions(ac);

        } catch (Exception e) {
            out.println(g.getFeedback() + "Error during runtime: " + e);
            out.close();
            return;
        }

        StringBuilder feedback = g.getFeedback();


        out.println(feedback.toString());
        out.close();
    }


    private void testAddWords(AutoCompleteDictionaryTrie ac) {
        feedback.append( "//TESTING ADDING WORDS (addWord, insert)//");
        appendTestString(1, "Adding first word to dictionary...");
        feedback.append("addWord returned (true): " + ac.addWord("dog") + ".");

        appendTestString(2,"Adding two more words and testing size...");
        ac.addWord("downhill");
        ac.addWord("downhiller");

        feedback.append("Size should be 3: " + ac.size() + ".");

        appendTestString(3, "Adding 21 more words to dictionary trie (testing size after insertions)...");

        ac.addWord("doge");
        ac.addWord("dogg");
        ac.addWord("dawg");
        ac.addWord("dage");
        ac.addWord("doggo");
        ac.addWord("doggie");
        ac.addWord("doggos");
        ac.addWord("doggoes");
        ac.addWord("doggies");
        ac.addWord("test");
        ac.addWord("tester");
        ac.addWord("testing");
        ac.addWord("tested");
        ac.addWord("testin");
        ac.addWord("teston");
        ac.addWord("testone");
        ac.addWord("testine");
        ac.addWord("testell");
        ac.addWord("testcase");
        ac.addWord("testbase");
        ac.addWord("testcases");


        feedback.append("Dict size (3+21) is: " + ac.size() + ".");

        // get current size before trying to add duplicate word

        appendTestString(4,"Adding duplicate word...");
        feedback.append("Adding duplicate word returned (false): " + ac.addWord("dog") + ".");

        appendTestString(5, "Checking size (24)after try to add duplicate word...");
        feedback.append("Dict size is (24): " + ac.size()+ ".");
    }

    private void testWordsInOut(AutoCompleteDictionaryTrie ac) {

        feedback.append("\n\n\n//TESTING FOR WORDS IN/OUT OF DICTIONARY (isWord)//");
        appendTestString(6,"Checking empty string...");
        // test empty string
        feedback.append("Empty string in dictionary: " + ac.isWord("") + ".");

        appendTestString(7, "Checking for word in dictionary...");
        feedback.append("'doggoes' in dictionary (true): " + ac.isWord("doggoes") + ".");

        // test word only missing last letter
        appendTestString(8, "Testing word only missing last letter...");
        feedback.append("'downhil' in dictionary (false): " + ac.isWord("downhil") + ".");

        //test word with added letter
        appendTestString(9, "Testing word with one extra letter...");
        feedback.append("'downhille' in dictionary (false): " + ac.isWord("downhille") + ".");

        appendTestString(10, "Testing for more words in dictionary...");
        feedback.append("'test' in dictionary (true): " + ac.isWord("test") + ". 'testcases' in dictionary (true): " + ac.isWord("testcases") + ". 'testone' in dictionary: " + ac.isWord("testone") + ".");


        appendTestString(11, "Testing word with capital letters...");
        feedback.append("'TeSt' in dictionary (true): " + ac.isWord("TeSt") + ".");



    }

    private void testPredictions(AutoCompleteDictionaryTrie ac) {

        feedback.append("\n\n\n//TESTING AUTO COMPLETE FUNCTIONALITY (predictCompletions)//");
        List<String> auto = ac.predictCompletions("dog", 3);

        appendTestString(12, "3 completions requested for dog...");
        feedback.append("Autocomplete returned the following: ");
        for (String s : auto) {
            feedback.append(s + ", ");
        }

        appendTestString(13,"Testing size of list...");
        feedback.append("predictCompletions returned (3): " + auto.size() + " elements.");

        auto = ac.predictCompletions("soup", 6);
        appendTestString(14, "6 completions requested for (soup), 0 expected...");
        feedback.append("predictCompletions found " + auto.size() + " words.");

        auto = ac.predictCompletions("dogg", 10);
        appendTestString(15, "(dogg) 10 completions requested, 6 expected...");
        feedback.append("predictCompletions found " + auto.size() + " elements.");

        appendTestString(16, "Testing for correctness of 6 words...");
        feedback.append("Words returned by predictCompletions: ");
        for (String s : auto) {
            feedback.append(s + ", ");
        }

        auto = ac.predictCompletions("test", 7);

        appendTestString(17, "(test) 7 completions requested (test for size)...");
        feedback.append("predictCompletions returned " + auto.size() + " elements.");

        appendTestString(18, "Testing if (test) list is sorted from shortest to longest...");
        for (String s : auto) {
            feedback.append(s + ", ");
        }
        feedback.append("Check above output.");

        List<String> partialList = auto.subList(0, 5);

        appendTestString(19, "Testing if (test) list contains 5 correct shorter words...");
        for (String s : partialList) {
            feedback.append(s + ", ");
        }
        feedback.append("\nCheck above output for test, tester, tested, testin, teston.");


        appendTestString(20, "Testing for remaining (2 of 7 requested) words (test)...");
        partialList = auto.subList(5, auto.size());

        int count = 0;

        count = partialList.contains("testone") ? ++count:count ;
        count = partialList.contains("testine") ? ++count:count;
        count = partialList.contains("testell") ? ++count:count;
        count = partialList.contains("testing") ? ++count:count;

        feedback.append("Random 2 of 'testone', 'testine', 'testell', and 'testing' " + count + " words were found.");

    }

    private void appendTestString(int testNum, String description) {
        feedback.append("\n\n** Test #" + testNum + ": " + description + "\n");
    }

    private StringBuilder getFeedback() {
        return this.feedback;
    }


}

