package spelling;

import java.io.PrintWriter;

public class DictionaryGrader {
    public static void main(String args[]) {
        PrintWriter out;
        try {
            out = new PrintWriter("grader_output/module4.part1.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        int incorrect = 0;
        int tests = 0;
        String feedback = "";

        try {
            Dictionary dictLL = new DictionaryLL();

            feedback += "** LL: Test #1: Adding new word (tEst) to the LL dictionary...";
            feedback += "addWord returned " + dictLL.addWord("tEst") + ".\n";

            feedback += "** LL: Test #2: Adding a second (second) word...";
            dictLL.addWord("second");
            feedback += "Dictionary size is " + dictLL.size() + ".\n";

            feedback += "** LL: Test #3: Looking up word (tEst) from first test...";
            feedback += "isWord returned " + dictLL.isWord("teSt") + ".\n";

            Dictionary dictBST = new DictionaryBST();

            feedback += "** BST:  Test #4: Adding a new word (tEst) to the BST dictionary...";
            feedback += "addWord returned " + dictBST.addWord("tEst") + ".\n";
            
            feedback += "** BST:  Test #5: Adding second (second) word to BST dictionary...";
            dictBST.addWord("second");
            feedback += "Dictionary size is " + dictBST.size() + ".\n";
            
            feedback += "** BST:  Test #6: Retrieving the word (tEst) from the first test...";
            feedback += "isWord returned " + dictBST.isWord("teSt") + ".\n";


            feedback += "** Test #7: Adding lots (4 - seconds, seconded, secondhand, selma) of words and retrieving some...";
            dictBST.addWord("seconds");
            dictBST.addWord("seconded");
            dictBST.addWord("secondhand");
            dictBST.addWord("selma");
            feedback += "BST: isWord(seconded) returned " + dictBST.isWord("seconded") + "; isWord(selma) returned " + dictBST.isWord("selma") + ".\n";
    
            feedback += "** Test #8: Testing non-word in DictLL...";
            feedback += "LL: isWord(soup) returned " + dictLL.isWord("soup") + ".\n";

            feedback += "** Test #9: Testing non-word in DictBST...";
            feedback += "BST:  isWord(soup) returned " + dictBST.isWord("soup") + ".\n";

        } catch (Exception e) {
            out.println("Runtime error: " + e);
            out.close();
            return;
        }

        feedback += "Tests complete. Make sure everything looks right.";

        out.println(feedback);
        out.close();
    }
}
