package spelling;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;

public class NearbyWordsGraderTwo {
    public static void main(String args[]) {
        int tests = 0;
        int incorrect = 0;
        String feedback = "";
        PrintWriter out;

        try {
            out = new PrintWriter("grader_output/module5.part2.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            Dictionary d = new DictionaryHashSet();
            DictionaryLoader.loadDictionary(d, "test_cases/dict2.txt");
            NearbyWords nw = new NearbyWords(d);

            String testWord = "dag";
            feedback += "** Test 1: Request 4 suggestions for " ;
            List<String> d1 = nw.suggestions(testWord, 4);
            feedback += " " + testWord+ " (2) " + d1.size() + " suggestions returned.\n";

            feedback += "** Test 2: Checking suggestion correctness... " + testWord + " ";

            feedback += "Suggestions for 'dag': ";
            for (String i : d1) {
                feedback += i + ", ";
            }
            testWord = "fare";
            feedback += "\n** Test 3: Request 3 suggestions for 'fare'... ";
            d1 = nw.suggestions(testWord, 3);
            feedback += " " + testWord+ " (3) " +  + d1.size() + " suggestions returned for 'fare'.\n";

            feedback += "** Test 4: Checking suggestion correctness... ";
            feedback += "Suggestions: ";
            for (String i : d1) {
                feedback += i + ", ";
            }
            feedback += "\n";
            
        } catch (Exception e) {
            out.println(feedback + "Runtime error: " + e);
            return;
        }

        out.println(feedback += "Tests complete. Make sure everything looks right.");
        out.close();
    }
}
