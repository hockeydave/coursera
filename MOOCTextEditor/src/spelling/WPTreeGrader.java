package spelling;

import java.io.PrintWriter;
import java.util.List;

// The specific grader used in this test is called "grader_dict.txt", 
// which is in the data folder. When submitting your WPTree.java file, 
// make sure it still points to data/dict.txt.

public class WPTreeGrader {
    public static String printPath(List<String> path) {
        if (path == null) {
            return "NULL PATH";
        }
        String ret = "";
        for (int i = 0; i < path.size(); i++) {
            ret += path.get(i);
            if (i < path.size() - 1) {
                ret += ", ";
            }
        }
        return ret;
    }

    public static void main (String[] args) {
        int incorrect = 0;
        int tests = 0;
        String feedback = "";

        PrintWriter out;
        try {
            out = new PrintWriter("grader_output/module5.part3.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {

            Dictionary dict = new DictionaryHashSet();
            DictionaryLoader.loadDictionary(dict, "data/grader_dict.txt");
            WPTree tree = new WPTree(new NearbyWords(dict)); 

            String w1 = "pool";
            String w2 = "spoon";
            List<String> path = tree.findPath(w1, w2);

            feedback += "** Test #1: Testing short path from " + w1 + " to " + w2 + "...";
            feedback += "Your path was: " + printPath(path) + ".\n";

            w1 = "stools";
            w2 = "moon";
            path = tree.findPath(w1, w2);

            feedback += "** Test #2: Testing long path from " + w1 + " to " + w2 + "...";
            feedback += "Your path was: " + printPath(path) + ".\n";

            w1 = "foal";
            w2 = "needless";
            path = tree.findPath(w1, w2);


            feedback += "** Test #3: Testing impossible path from " + w1 + " to " + w2 + "...";
            feedback += "Your path was: " + printPath(path) + ".\n";

            w1 = "needle";
            w2 = "kitten";
            path = tree.findPath(w1, w2);
            
            feedback += "** Test #4: Testing using a nonexistent word...";
            feedback += "Your path was: " + printPath(path) + ".\n";
        } catch (Exception e) {
            out.println(e);
            out.close();
            return;
        }

        out.println(feedback + "Tests complete. Make sure everything looks right.");
        out.close();
    }
}
