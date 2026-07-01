package textgen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class MarkovTextGeneratorLoLTest {

    MarkovTextGeneratorLoL gen;
    Random rand;
    @Before
    public void setUp() throws Exception {
        rand = new Random(499889980);
        gen = new MarkovTextGeneratorLoL(rand);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void train() {
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        gen.train(textString);
        Map<String, ListNode> listNodeMap = gen.getListNodeMap();
        assertEquals(10, listNodeMap.size());
        ListNode ln = listNodeMap.get("again.");
        assertEquals("train:  Wraparound happy path check listNodes. again","Hello.", ln.getRandomNextWord(rand) );
        ln = listNodeMap.get("Hello");
        assertEquals("train:  random", (double) (1.0/3.0), getProbability(ln, "Bob."),  0.01);
        assertEquals("train:  random", (double) (2.0/3.0), getProbability(ln, "there."),  0.01);

        ln = listNodeMap.get("there.");
        assertEquals("train:  random", (double) (1.0/2.0), getProbability(ln, "This"),  0.001);

    }

    @Test
    public void generateText() {
        try {
            gen.generateText(10);
            fail("GenerateText:  untrained MarkovTextGenerator should throw exception when untrained");
        } catch(IllegalStateException ise) {

        }
        gen.train("");
        try {
            String output = gen.generateText(10);
            fail("GenerateText:  empty string should throw exception");
        } catch(IllegalStateException ise) {

        }
        // Happy path
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        gen.train(textString);
        String output = gen.generateText(20);
        List<String> words = gen.getWords(output);
        assertEquals("GenerateText:  happy path 20 words generateText", 20, words.size());
        // Make sure 0 words output
        output = gen.generateText(0);
        words = gen.getWords(output);
        assertEquals("GenerateText:  happy path 0 words generateText",0, words.size());
    }

    @Test
    public void testToString() {
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        String expected = "Hello: there.->there.->Bob.->\n";
        gen.train(textString);
        String output = gen.getListNodeMap().get("Hello").toString();
        assertEquals("toString:  ",expected, output);
    }

    @Test
    public void retrain() {
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
        gen.train(textString2);
        Map<String, ListNode> listNodeMap = gen.getListNodeMap();
        assertEquals(29, listNodeMap.size());

        ListNode youList = listNodeMap.get("You");
        String say = youList.getRandomNextWord(rand);
        assertEquals("Retrain:  You say","say", say);

        String output = gen.generateText(20);
        List<String> words = gen.getWords(output);
        assertEquals("Retrain:  happy path 0 words generateText",21, words.size());

        ListNode ln = listNodeMap.get("say");
        assertEquals("Retrain:  random", (double) (2.0/39.0), getProbability(ln, "yes,"),  0.01);
        assertEquals("Retrain:  random", (double) (4.0/39.0), getProbability(ln, "goodbye"),  0.01);
        assertEquals("Retrain:  random", (double) (10.0/39.0), getProbability(ln, "hello,"),  0.01);
    }

    private double getProbability(ListNode listNode, String nextWord) {
        double total = 0;
        double matches = 0;
        for(int i = 0; i < 1000000; i++) {
            String word = listNode.getRandomNextWord(rand);
            if(word.equals(nextWord))
                matches++;
            total++;
        }
        double ratio = matches / total;
        return ratio;
    }
}