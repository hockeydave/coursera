package spelling;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;

public class NearbyWordsGraderOne {
    public static void main(String args[]) {
        int tests = 0;
        int incorrect = 0;
        String feedback = "";
        PrintWriter out;

        try {
            out = new PrintWriter("grader_output/module5.part1.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            Dictionary d = new DictionaryHashSet();
            DictionaryLoader.loadDictionary(d, "test_cases/dict.txt");
            NearbyWords nw = new NearbyWords(d);
            String testWord = "word";
            List<String> d1 = nw.distanceOne(testWord, true);
            
            feedback += "** Test 1: distanceOne list size for 'word'... Expected 5.  ";
            feedback += "distanceOne returned " + d1.size() + " words for 'word'.\n";

            feedback += "** Test 2: distanceOne words returned for 'word'... ";
            for (String i : d1) {
                feedback += i + ", ";
            }

            feedback += "\n** Test 3: distanceOne list size (allowing non-words)... Expected 230.  ";
            d1 = nw.distanceOne(testWord, false);
            feedback += "distanceOne with non-words returned " + d1.size() + " words.\n";
            
            d1 = new ArrayList<String>();

            testWord = "makers";
            feedback += "** Test 4: deletions list size for 'makers'... Expected 2.  ";
            nw.deletions(testWord, d1, true);
            feedback += "deletions returned " + d1.size() + " words.\n";

            feedback += "** Test 5: deletions words returned... Expected makes, maker.  ";
            feedback += "deletions returned: ";
            for (String i : d1) {
                feedback += i + ", ";
            }

            d1 = new ArrayList<String>();

            testWord = "or";
            feedback += "\n** Test 6: insertions list size for 'or'... Expected 3.  ";
            nw.insertions(testWord, d1, true);
            feedback += "insertions returned " + d1.size() + " words.\n";

            feedback += "** Test 7: insertions words returned for 'or'... ";
            feedback += "insertions returned: ";
            for (String i : d1) {
                feedback += i + ", ";
            }
            feedback += "\n";
            
        } catch (Exception e) {
            out.println("Runtime error: " + e);
            return;
        }

        out.println(feedback + "Tests complete. Check that everything looks right.");
        out.close();
    }
}
