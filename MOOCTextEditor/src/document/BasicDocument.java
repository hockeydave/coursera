package document;

import java.util.Arrays;
import java.util.List;

/**
 * A naive implementation of the Document abstract class.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class BasicDocument extends Document {
    /**
     * Create a new BasicDocument object
     *
     * @param text The full text of the Document.
     */
    public BasicDocument(String text) {
        super(text);
    }


    /**
     * Get the number of words in the document.
     * A "word" is defined as a contiguous string of alphabetic characters
     * i.e. any upper or lower case characters a-z or A-Z.  This method completely
     * ignores numbers when you count words, and assumes that the document does not have
     * any strings that combine numbers and letters.
     * <p>
     * Check the examples in the main method below for more information.
     * <p>
     * This method should process the entire text string each time it is called.
     *
     * @return The number of words in the document.
     */
    @Override
    public int getNumWords() {
        //TODO: Implement this method in week 2 according to the comments above.
        // See the Module 2 support videos if you need help.
        return getWords().size();
    }

    /**
     * get the words in the document
     *
     * @return a List of the words in the document
     */
    private List<String> getWords() {
        return getTokens("[a-zA-Z]+");
    }

    /**
     * Get the number of sentences in the document.
     * Sentences are defined as contiguous strings of characters ending in an
     * end of sentence punctuation (. ! or ?) or the last contiguous set of
     * characters in the document, even if they don't end with a punctuation mark.
     * <p>
     * Check the examples in the main method below for more information.
     * <p>
     * This method should process the entire text string each time it is called.
     *
     * @return The number of sentences in the document.
     */
    @Override
    public int getNumSentences() {
        //TODO: Implement this method.  See the Module 2 support videos
        // if you need help.
        return getTokens("[^.!?]+").size();
    }

    /**
     * Get the total number of syllables in the document (the stored text).
     * To count the number of syllables in a word, it uses the following rules:
     * Each contiguous sequence of one or more vowels is a syllable,
     * with the following exception: a lone "e" at the end of a word
     * is not considered a syllable unless the word has no other syllables.
     * You should consider y a vowel.
     * <p>
     * Check the examples in the main method below for more information.
     * <p>
     * This method should process the entire text string each time it is called.
     *
     * @return The number of syllables in the document.
     */
    @Override
    public int getNumSyllables() {
        //TODO: Implement this method in week 2.  See the Module 2 support videos
        // if you need help.  And note that there is no need to use a regular
        // expression for the syllable counting.  We recommend you implement
        // the helper function countSyllables in Document.java using a loop,
        // and then call it here on each word.
        List<String> words = getWords();
        int totalSyllables = 0;
        for (String word : words) {
            totalSyllables += countSyllables(word);
        }
        return totalSyllables;
    }


    /* The main method for testing this class.
     * You are encouraged to add your own tests.  */
    public static void main(String[] args) {
        /* Each of the test cases below uses the method testCase.  The first
         * argument to testCase is a Document object, created with the string shown.
         * The next three arguments are the number of syllables, words and sentences
         * in the string, respectively.  You can use these examples to help clarify
         * your understanding of how to count syllables, words, and sentences.
         */
        testCase(new BasicDocument("This is a test.  How many???  "
                        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
                16, 13, 5);
        testCase(new BasicDocument(""), 0, 0, 0);
        testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
                + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
        testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
        testCase(new BasicDocument("Here is a series of test sentences. Your program should "
                + "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
                + "the correct amount of syllables (example, for example), "
                + "but most of them will."), 49, 33, 3);
        testCase(new BasicDocument("Segue"), 2, 1, 1);
        testCase(new BasicDocument("Sentence"), 2, 1, 1);
        testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
        testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
                32, 15, 1);

        //Grader
        BasicDocument doc = new BasicDocument("This is a sentence.");
        System.out.println(doc.getText() + "\nFlesch Score should be 97.0. Result is:  " + doc.getFleschScore());
        doc = new BasicDocument("These are some sentences. These are some sentences. These are some sentences.");
        System.out.println(doc.getText() + "\nFlesch Score should be 75.9. Result is:  " + doc.getFleschScore());
        doc = new BasicDocument("This is test number threeeeeeeeeeeeeeeeeeeeeeeeee. I can do that with oooooooooooooooooooooother letters tooooooooooo");
        System.out.println(doc.getText() + "\nFlesch Score should be 96.1. Result is:  " + doc.getFleschScore());
        doc = new BasicDocument("test????? sentence,??????? probably!,        good");
        System.out.println(doc.getText() + "\nFlesch Score should be 57.8. Result is:  " + doc.getFleschScore());
        doc = new BasicDocument("i, challenge, you, to, 2.5, battle, of!? wits");
        System.out.println(doc.getText() + "\nFlesch Score should be 107.8. Result is:  " + doc.getFleschScore());
        doc = new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad. Vidit mnesarchum quo ut, tempor volutpat contentiones ex pro, quo at elitr dolore. Idque placerat id his, nisl dicit has cu, cu quo clita detracto prodesset. Est tale voluptua cu, in ornatus epicuri nam. Ut falli nobis vel, mei salutatus referrentur et. Quo nisl augue vituperata in, percipit intellegat usu in. Has esse sensibus splendide te, bonorum perpetua eum an. No sit mediocrem deseruisse, graece vocent eos ea, nibh summo praesent et sea. Melius maiorum persequeris vis an, usu ad eirmod definitionem, ne vis odio tamquam repudiare. Id est esse graeco oporteat. Pro et erat persius, his stet libris ridens et. Animal vulputate disputando eam at. Per an zril nostro suavitate, eum te exerci comprehensam. Et eos cetero labores, eam graeci tamquam ei. Eu pri perfecto ocurreret liberavisse. Et nulla feugiat philosophia vix, at mei oratio explicari deterruisset. Ea mea aliquip fuisset suscipiantur. Cum inani nonumes appetere at, nibh senserit his eu. Pri nusquam iudicabit signiferumque ut, his ex debet oportere deseruisse. Inermis reprimique ei pri. Summo salutatus sed at, cu vel enim quodsi definitiones. Quo facete eligendi iracundia in, pri vero prima quaerendum ad, solet gubergren cum eu. Duo te volumus singulis adipisci. Veri omnesque in vix. At mel stet erant ludus, id usu congue persequeris. Sit omnesque convenire ne. Vis an verterem reformidans, vim suas singulis definitionem eu. Has sale aeque efficiantur in, cu eam agam tota maiestatis, te sea repudiare mnesarchum. Eu maiorum epicuri sed, ullum affert omittantur at est. Eum dolorum reprehendunt ad. Vivendum invenire sea no, ne vis suas liber adipiscing. Consequat expetendis at sed, est eu labore aperiri ocurreret, est noster voluptua contentiones eu. Te nam lorem signiferumque, per te ludus quodsi. Sea vidit porro an, eos ea aeterno maiestatis consectetuer, te sed constituto ullamcorper philosophia. Dicta appareat maiestatis ius ut, vis illud viderer temporibus eu, quas elitr eos et. Hinc vidit possit ius id, altera adversarium eum ad. Cu debitis epicurei pro, postea deseruisse nec ad. No mel eruditi efficiantur. No sea meis imperdiet disputationi. Per labore atomorum tractatos an. Cum quot nostrum perpetua id, dico mundi nam ex. Eam an iriure vulputate voluptatum. Eu sit insolens iracundia contentiones. Nostrud tincidunt no usu, cum at cetero vivendo inimicus, no stet gubergren splendide has. Aliquip placerat reformidans te his, verterem convenire forensibus te pro, cu pri voluptua honestatis. Ex summo munere placerat sea, pro ei essent integre definitiones, per ex epicuri scripserit. Cu dico omnes sit, eu ius tale partem, mei quis errem eripuit ex. Et homero aliquam tibique pri. Mea ut utinam tamquam, at erat mucius sapientem qui. Te eam error euripidis accommodare, et quod nostrum vix. Ex quo tale ipsum vivendo. An persecuti liberavisse sed, eum cu viderer eruditi, id ius discere utroque aliquando. Regione vocibus assentior duo te. Meis legere vivendum vel ut, per repudiare vulputate ea, cum in offendit indoctum rationibus. Aeque affert forensibus cu vix, ea tibique neglegentur eam. Case civibus nam et. An mei dicit similique. Has in malorum scaevola omittantur. Vim et habemus detraxit, te sed dico invenire electram. Usu viderer fabellas intellegam an, no saepe facete vis, aliquip reformidans qui ne. Ad mea scaevola pertinax, has an amet aeque. Ut sit affert mandamus. Erant dicunt maiorum ea eam. Usu purto movet explicari et, inani legere facilis cum no. No his putant nonumes perpetua, cum fugit necessitatibus at. Eu has agam moderatius, eos ei omnis percipitur honestatis, verterem scribentur eloquentiam eam an. Ex vis quod reprimique, an vix utroque officiis. Cu sumo option rationibus mea, aliquid facilisis qui ne. Eirmod petentium conclusionemque mei te, sonet altera sadipscing per at. Aliquam corpora ut cum, cu hinc posse malorum usu, no quo graeci qualisque honestatis. Blandit accusata adipiscing ea his. Vix ea tale iuvaret periculis. Autem ignota ei pro, ad cum dicam timeam. Duo officiis phaedrum repudiandae id, usu ut brute inani tincidunt. Sit semper impedit cotidieque ei. Nostrud propriae voluptatum cum ea. Nec clita dolorum oportere et, sea ne accusamus efficiendi complectitur, nulla nostro ut mei. Aliquid fierent ex est, vis ullum tamquam dissentiet ne. Epicurei reformidans te mel, ius malis maluisset ex. Id perfecto sensibus mei, mei sanctus fastidii periculis id. Atomorum incorrupte an cum, elit nostrud sadipscing ad sed. Saepe nostrud ad nam. Est congue veritus percipit ut, te mundi populo oportere sea. Corpora probatus efficiantur ei eum. Altera pertinax postulant mei ne, error partem offendit sit cu. Modo semper tincidunt ius id, mei an stet imperdiet delicatissimi. Id mea homero efficiendi. Diam minim similique duo ut. Eu mucius delenit pri. Cu ridens expetenda corrumpit eum, id per impedit mediocrem, idque audiam te mel. Ad probo aliquid mel, eruditi inermis offendit ius te, voluptua conceptam pri id. In error invidunt aliquando vis. Veniam splendide assentior ad duo, an dicta vocent sit. Ad ubique indoctum philosophia usu, ea eum dolores dissentiunt. Mea salutandi temporibus ut, iusto oblique at est, tollit postea putent his te. Ad ius atqui liber latine. Porro abhorreant vituperata an pro, sit choro deserunt at, tale verear ceteros vim an. Per detracto facilisis signiferumque ex, vis et platonem eloquentiam, in mutat eruditi mei. Antiopam explicari ut sea, diam maiorum temporibus no est, vix cu consul dolorem menandri. His eu meis albucius adipiscing. Pri audire appareat complectitur ea, vis eu homero aperiam denique. Qui inimicus pertinacia ei, voluptua partiendo qui ei. Tacimates rationibus percipitur pri ea, ea duo wisi apeirian dissentiet. Per eu equidem repudiare. Modus saepe laoreet vix cu, mucius euripidis est ea, mea iudico noster quodsi et. Rebum idque disputando eam no. Mel quodsi audire ad, quo iuvaret denique vituperata ad. Et mea possim sapientem maiestatis, usu congue labore ornatus ad, consul ceteros conceptam per in. Ad eam amet etiam voluptaria. In cum labore timeam, ex putant dolorum usu. Usu offendit reprehendunt ex, cu eripuit nostrum gubergren his. Docendi democritum his et, cu eos mazim erant sapientem. Ad vim iriure philosophia. Dico voluptua ea sea, an mundi utamur iisque eum, justo aliquid ut ius. Mea et alia tritani.");
        System.out.println(doc.getText() + "\nFlesch Score should be 28.1. Result is:  " + doc.getFleschScore());

        // Some quiz crap.  Not part of project
        doc = new BasicDocument("one (1), two (2), three (3)");
        System.out.println(doc.getTokens("[^, ]+"));
        System.out.println(doc.getTokens("[a-z()0-9]+"));
        System.out.println(doc.getTokens("[a-z]+|[()0-9]+"));

        String s = "%one%%two%%%three%%%%";
        System.out.println(Arrays.toString( s.split("%+")) );
        System.out.println(Arrays.toString(s.split("[a-z]+") ));
        System.out.println(Arrays.toString(s.split("one|two|three") ));
        System.out.println(Arrays.toString(s.split("[one,two,three]") ));
    }

}
