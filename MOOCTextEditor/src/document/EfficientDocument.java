package document;

import java.util.List;

/**
 * A class that represents a text document
 * It does one pass through the document to count the number of syllables, words,
 * and sentences and then stores those values.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class EfficientDocument extends Document {

    private int numWords;  // The number of words in the document
    private int numSentences;  // The number of sentences in the document
    private int numSyllables;  // The number of syllables in the document

    public EfficientDocument(String text) {
        super(text);
        processText();
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
        return !(tok.indexOf("!") >= 0 || tok.indexOf(".") >= 0 || tok.indexOf("?") >= 0);
    }


    /**
     * Passes through the text one time to count the number of words, syllables
     * and sentences, and set the member variables appropriately.
     * Words, sentences and syllables are defined as described below.
     */
    private void processText() {
        // Call getTokens on the text to preserve separate strings that are
        // either words or sentence-ending punctuation.  Ignore everything
        // That is not a word or a sentence-ending punctuation.
        // MAKE SURE YOU UNDERSTAND THIS LINE BEFORE YOU CODE THE REST
        // OF THIS METHOD.
        List<String> tokens = getTokens("[!?.]+|[a-zA-Z]+");
        // TODO: Finish this method.  Remember the countSyllables method from
        // Document (super) class.  That will come in handy here.  isWord defined above will also help.
        boolean endOnSentence = true;
        for (String token : tokens) {
            if (isWord(token)) {
                endOnSentence = false;
                numWords++;
                numSyllables += countSyllables(token);
            } else {
                endOnSentence = true;
                numSentences++;
            }
        }
        // See definition of sentence.  If document ends w/o an end of sentence punctuations, count as sentence.
        if (!endOnSentence) numSentences++;
    }


    /**
     * Get the number of sentences in the document.
     * Sentences are defined as contiguous strings of characters ending in an
     * end of sentence punctuation (. ! or ?) or the last contiguous set of
     * characters in the document, even if they don't end with a punctuation mark.
     * <p>
     * Check the examples in the main method below for more information.
     * <p>
     * This method does NOT process the whole text each time it is called.
     * It returns information already stored in the EfficientDocument object.
     *
     * @return The number of sentences in the document.
     */
    @Override
    public int getNumSentences() {
        //TODO: write this method.  Hint: It's simple
        return numSentences;
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
     * This method does NOT process the whole text each time it is called.
     * It returns information already stored in the EfficientDocument object.
     *
     * @return The number of words in the document.
     */
    @Override
    public int getNumWords() {
        //TODO: write this method.  Hint: It's simple
        return numWords;
    }


    /**
     * Get the total number of syllables in the document (the stored text).
     * To calculate the  number of syllables in a word, it uses the following rules:
     * Each contiguous sequence of one or more vowels is a syllable,
     * with the following exception: a lone "e" at the end of a word
     * is not considered a syllable unless the word has no other syllables.
     * You should consider y a vowel.
     * <p>
     * Check the examples in the main method below for more information.
     * <p>
     * This method does NOT process the whole text each time it is called.
     * It returns information already stored in the EfficientDocument object.
     *
     * @return The number of syllables in the document.
     */
    @Override
    public int getNumSyllables() {
        //TODO: write this method.  Hint: It's simple
        return numSyllables;
    }

    // Can be used for testing
    // We encourage you to add your own tests here.
    public static void main(String[] args) {
        testCase(new EfficientDocument("This is a test.  How many???  "
                        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
                16, 13, 5);
        testCase(new EfficientDocument(""), 0, 0, 0);
        testCase(new EfficientDocument("sentence, with, lots, of, commas.!  "
                + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
        testCase(new EfficientDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
        testCase(new EfficientDocument("Here is a series of test sentences. Your program should "
                + "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
                + "the correct amount of syllables (example, for example), "
                + "but most of them will."), 49, 33, 3);
        testCase(new EfficientDocument("Segue"), 2, 1, 1);
        testCase(new EfficientDocument("Sentence"), 2, 1, 1);
        testCase(new EfficientDocument("Sentences?!"), 3, 1, 1);
        testCase(new EfficientDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
                32, 15, 1);
        testCase(new EfficientDocument("Testing the EfficientDocument now. This should be faster than the BasicDocument, but produce the same output."),
                29, 16, 2);
        testCase(new EfficientDocument("Time to get weeeeeeeeeeeird, with, the. Punctuation"),
                9, 7, 2);
        testCase(new EfficientDocument(", 3 out of 4 times. This is line 3? Yes.!"),
                8, 7, 3);
        testCase(new EfficientDocument("abcdefg???hijklmn???opq?rstuv?wxyz!"),
                6, 5, 5);
        testCase(new EfficientDocument("Lorem ipsum dolor sit amet, simul nominati definiebas sit in, senserit vituperatoribus cu his. In vix ullum saepe utamur, appetere oportere efficiendi pro in. Te mel habeo dicat democritum, nominati incorrupte per at. Utinam integre te his, singulis accommodare pro te, ea has cibo senserit periculis. Te mel movet paulo. An sensibus rationibus eam. Euismod oporteat consequuntur vel ea. Qui et agam nostrum oportere, eam ex novum civibus disputationi. Ut meis cetero duo. Cu quod consul comprehensam eos, mazim volumus accumsan sit et. Sea sumo brute temporibus id, nam movet facete id, probo justo evertitur vim id. Nec graeco neglegentur ut. Propriae accusata ut sit, ut mundi iuvaret eleifend ius. Cu habeo ponderum sea, eam quaeque assueverit no. Ei sit lorem feugiat, ea qualisque gubergren eam. Veri fugit id nam, at facete tacimates eos. Quis meis mnesarchum sea ad, vix affert principes interpretaris ex. Ex sit diam alia dicat, ex ius omnes ornatus. Erant persecuti ius at, ipsum invenire sit ne. Vix cu minim detraxit atomorum, no dico option postulant mea. Mei facer corpora no. Has at quando persequeris, delenit urbanitas vel ne, id fugit affert nominavi nam. Tacimates molestiae sea ad, sed no ponderum dissentias. Diam omnis mundi te nec. Id mea dolore audiam efficiantur, est te cibo admodum maiestatis. Ad mundi doctus debitis duo. In sit stet vero malis, per scripta nusquam intellegam at. Id nihil meliore fuisset per, etiam epicuri repudiandae ex pri. Nec eu modus eligendi delicatissimi, everti convenire id sea, eum ei imperdiet appellantur. Cum ei enim detracto, molestie dignissim cu eum. Te iriure inermis perpetua vix, in sed mandamus honestatis, veri velit nam et. Ea posse philosophia ius, assum laoreet vivendum ex sed, ei mei postulant qualisque. Odio clita conceptam ei per, no bonorum consetetur vis. Meliore periculis et sed, duis tollit periculis in sit, autem patrioque vis cu. Has hendrerit tincidunt at, id fastidii quaestio vim. Ei stet elitr commodo vel, latine impetus petentium sea te, quo nominati sensibus ea. At stet velit discere vel. Ut quaestio corrumpit eum. Cu quem esse per. Ei justo apeirian pro. Ne mel omnes nullam sadipscing. Affert legendos definitionem duo id, ex duo tibique molestie. Option evertitur per id, te dolorum disputationi has, altera vivendum eu pro. Ex invidunt intellegebat est, ius cu solum zril verear. Eos deseruisse adipiscing an. Mundi semper lucilius qui ex, iudico possit molestiae duo et. Ei audiam verterem erroribus usu, ius ad oratio inimicus torquatos, dicta lucilius honestatis pri in. Mel partem atomorum ne. Te feugiat philosophia sit, nusquam iracundia ei pro, sea ne magna mediocrem. Et cum sonet suavitate gloriatur. No indoctum suavitate eos. Discere delicata sit ut, iisque ceteros facilisi quo id, qui in volumus interpretaris. Sea saepe tantas principes id. Sea sale tamquam et, an vel inani oblique inimicus. Eam ad paulo essent ocurreret, in cum wisi recteque, ad ius sanctus posidonium. Insolens recteque ocurreret vix ea, vis eu natum mutat nonumy. Mel ex disputando omittantur, an sea saperet persecuti. Ut eripuit inimicus assentior vis, eu modo oratio assueverit nec. An modus option quaerendum nam. Ius porro iudicabit eu, mea graeco platonem mnesarchum te, alii platonem usu no. Vis ut fabulas laoreet suscipiantur. His ad stet indoctum disputando, ea mel eius movet recteque. Ex elit atomorum scribentur vel, has at falli soluta perpetua. Eu ius stet patrioque, natum aliquid ea usu, has legimus reformidans theophrastus id. Nonumy impedit honestatis ius in, no dolore necessitatibus qui. Te nullam saperet convenire est, ea pri sumo vivendum. Ad has inermis dissentiet comprehensam, in velit disputando has, appareat urbanitas id vix. Ei oblique dissentias his, no has quando dolorem voluptua. Vix id malis audiam, vix eu ipsum dicta utinam, mea choro urbanitas tincidunt an. Qui ea scribentur suscipiantur. Ea habemus inciderint sadipscing mea. Meis admodum adipiscing eam te. Duo ex malorum explicari disputationi, et qui postea petentium efficiendi. Ei tation facilisi deserunt est, cu sea vidit alterum sententiae. Erroribus scribentur disputando ad nec. No nisl mandamus postulant pro. Sed mazim malorum probatus ei, ne cum tamquam convenire. Sea facete erroribus et, mel ut partiendo sapientem. Vis quaeque singulis ne, an pro fabulas facilis volutpat. Sea ea ludus suscipiantur interpretaris. Aliquid expetenda nec ex. Mei cu idque dicunt, vel eu facilisi splendide. His eu enim veritus mediocritatem, his et modus menandri. Modo erroribus ut eam. Nostrud saperet forensibus nam id, sit id dolore fastidii ocurreret. Dicat honestatis ei duo. Quando contentiones vis ne, ad his purto insolens dissentiet, eu eos volutpat accusamus. Est ferri oratio numquam et, qui ut justo intellegat, te mel exerci deleniti oportere. Ea mea liberavisse delicatissimi, ne movet iracundia has. Sit diam legere eu. At tritani omittantur mei, pri magna simul phaedrum an. Per no utamur facilis, te vim erat eirmod. Ne malis solet praesent mea. Essent legimus disputationi ius ut, mundi cetero pri te. At elitr lucilius rationibus mea, sed eu inani aeque exerci. An dolores albucius cum. Duo paulo nullam ea. Vis simul neglegentur necessitatibus ut, paulo debitis elaboraret no eam, sea inani oportere an. Duo id movet munere scripta, laudem apeirian quaestio ad eos, in paulo quaeque accumsan eos. Id diam ubique graeco eam, duo no odio erant adipisci. Nam quaeque deserunt tractatos ex. Cu sea quod maiorum constituto. In quas convenire disputando per, omnium rationibus inciderint mea ei, accommodare necessitatibus nec at. Et est soleat tacimates, erroribus urbanitas sea at, an ferri sensibus nec. Modo commodo sit an, solum nonumy possim ut eam. Ad expetenda instructior pri. Ne utinam aliquando reprehendunt nec. Nec no dolorem consulatu. Mea nisl quaestio deseruisse in, velit repudiare moderatius ea eos. No duo summo scripserit. Eu prompta feugiat disputationi vix. His ne illud malorum dolorem. Ne sea illum nemore, pri lorem tacimates suavitate te. Nibh latine comprehensam est ut, ius vitae quaestio et. Cibo clita per ne, in nusquam albucius senserit usu, elit facer ut qui. No duo graeci scribentur referrentur. Illum solet vituperata an sit, ad quo ridens eripuit consectetuer. Nec aeterno aliquip ullamcorper id, ne postea possim sit."),
                1983, 989, 118);


        //Grader
        EfficientDocument doc = new EfficientDocument("Testing the EfficientDocument now. This should be faster than the BasicDocument, but produce the same output.");
        System.out.println(doc.getText() + "\nFlesch Score should be 97.0. Result is:  " + doc.getFleschScore());
        doc = new EfficientDocument("Time to get weeeeeeeeeeeird, with, the. Punctuation");
        System.out.println(doc.getText() + "\nFlesch Score should be 75.9. Result is:  " + doc.getFleschScore());
        doc = new EfficientDocument(", 3 out of 4 times. This is line 3? Yes.!");
        System.out.println(doc.getText() + "\nFlesch Score should be 96.1. Result is:  " + doc.getFleschScore());
        doc = new EfficientDocument("abcdefg???hijklmn???opq?rstuv?wxyz!");
        System.out.println(doc.getText() + "\nFlesch Score should be 57.8. Result is:  " + doc.getFleschScore());
        doc = new EfficientDocument("Lorem ipsum dolor sit amet, simul nominati definiebas sit in, senserit vituperatoribus cu his. In vix ullum saepe utamur, appetere oportere efficiendi pro in. Te mel habeo dicat democritum, nominati incorrupte per at. Utinam integre te his, singulis accommodare pro te, ea has cibo senserit periculis. Te mel movet paulo. An sensibus rationibus eam. Euismod oporteat consequuntur vel ea. Qui et agam nostrum oportere, eam ex novum civibus disputationi. Ut meis cetero duo. Cu quod consul comprehensam eos, mazim volumus accumsan sit et. Sea sumo brute temporibus id, nam movet facete id, probo justo evertitur vim id. Nec graeco neglegentur ut. Propriae accusata ut sit, ut mundi iuvaret eleifend ius. Cu habeo ponderum sea, eam quaeque assueverit no. Ei sit lorem feugiat, ea qualisque gubergren eam. Veri fugit id nam, at facete tacimates eos. Quis meis mnesarchum sea ad, vix affert principes interpretaris ex. Ex sit diam alia dicat, ex ius omnes ornatus. Erant persecuti ius at, ipsum invenire sit ne. Vix cu minim detraxit atomorum, no dico option postulant mea. Mei facer corpora no. Has at quando persequeris, delenit urbanitas vel ne, id fugit affert nominavi nam. Tacimates molestiae sea ad, sed no ponderum dissentias. Diam omnis mundi te nec. Id mea dolore audiam efficiantur, est te cibo admodum maiestatis. Ad mundi doctus debitis duo. In sit stet vero malis, per scripta nusquam intellegam at. Id nihil meliore fuisset per, etiam epicuri repudiandae ex pri. Nec eu modus eligendi delicatissimi, everti convenire id sea, eum ei imperdiet appellantur. Cum ei enim detracto, molestie dignissim cu eum. Te iriure inermis perpetua vix, in sed mandamus honestatis, veri velit nam et. Ea posse philosophia ius, assum laoreet vivendum ex sed, ei mei postulant qualisque. Odio clita conceptam ei per, no bonorum consetetur vis. Meliore periculis et sed, duis tollit periculis in sit, autem patrioque vis cu. Has hendrerit tincidunt at, id fastidii quaestio vim. Ei stet elitr commodo vel, latine impetus petentium sea te, quo nominati sensibus ea. At stet velit discere vel. Ut quaestio corrumpit eum. Cu quem esse per. Ei justo apeirian pro. Ne mel omnes nullam sadipscing. Affert legendos definitionem duo id, ex duo tibique molestie. Option evertitur per id, te dolorum disputationi has, altera vivendum eu pro. Ex invidunt intellegebat est, ius cu solum zril verear. Eos deseruisse adipiscing an. Mundi semper lucilius qui ex, iudico possit molestiae duo et. Ei audiam verterem erroribus usu, ius ad oratio inimicus torquatos, dicta lucilius honestatis pri in. Mel partem atomorum ne. Te feugiat philosophia sit, nusquam iracundia ei pro, sea ne magna mediocrem. Et cum sonet suavitate gloriatur. No indoctum suavitate eos. Discere delicata sit ut, iisque ceteros facilisi quo id, qui in volumus interpretaris. Sea saepe tantas principes id. Sea sale tamquam et, an vel inani oblique inimicus. Eam ad paulo essent ocurreret, in cum wisi recteque, ad ius sanctus posidonium. Insolens recteque ocurreret vix ea, vis eu natum mutat nonumy. Mel ex disputando omittantur, an sea saperet persecuti. Ut eripuit inimicus assentior vis, eu modo oratio assueverit nec. An modus option quaerendum nam. Ius porro iudicabit eu, mea graeco platonem mnesarchum te, alii platonem usu no. Vis ut fabulas laoreet suscipiantur. His ad stet indoctum disputando, ea mel eius movet recteque. Ex elit atomorum scribentur vel, has at falli soluta perpetua. Eu ius stet patrioque, natum aliquid ea usu, has legimus reformidans theophrastus id. Nonumy impedit honestatis ius in, no dolore necessitatibus qui. Te nullam saperet convenire est, ea pri sumo vivendum. Ad has inermis dissentiet comprehensam, in velit disputando has, appareat urbanitas id vix. Ei oblique dissentias his, no has quando dolorem voluptua. Vix id malis audiam, vix eu ipsum dicta utinam, mea choro urbanitas tincidunt an. Qui ea scribentur suscipiantur. Ea habemus inciderint sadipscing mea. Meis admodum adipiscing eam te. Duo ex malorum explicari disputationi, et qui postea petentium efficiendi. Ei tation facilisi deserunt est, cu sea vidit alterum sententiae. Erroribus scribentur disputando ad nec. No nisl mandamus postulant pro. Sed mazim malorum probatus ei, ne cum tamquam convenire. Sea facete erroribus et, mel ut partiendo sapientem. Vis quaeque singulis ne, an pro fabulas facilis volutpat. Sea ea ludus suscipiantur interpretaris. Aliquid expetenda nec ex. Mei cu idque dicunt, vel eu facilisi splendide. His eu enim veritus mediocritatem, his et modus menandri. Modo erroribus ut eam. Nostrud saperet forensibus nam id, sit id dolore fastidii ocurreret. Dicat honestatis ei duo. Quando contentiones vis ne, ad his purto insolens dissentiet, eu eos volutpat accusamus. Est ferri oratio numquam et, qui ut justo intellegat, te mel exerci deleniti oportere. Ea mea liberavisse delicatissimi, ne movet iracundia has. Sit diam legere eu. At tritani omittantur mei, pri magna simul phaedrum an. Per no utamur facilis, te vim erat eirmod. Ne malis solet praesent mea. Essent legimus disputationi ius ut, mundi cetero pri te. At elitr lucilius rationibus mea, sed eu inani aeque exerci. An dolores albucius cum. Duo paulo nullam ea. Vis simul neglegentur necessitatibus ut, paulo debitis elaboraret no eam, sea inani oportere an. Duo id movet munere scripta, laudem apeirian quaestio ad eos, in paulo quaeque accumsan eos. Id diam ubique graeco eam, duo no odio erant adipisci. Nam quaeque deserunt tractatos ex. Cu sea quod maiorum constituto. In quas convenire disputando per, omnium rationibus inciderint mea ei, accommodare necessitatibus nec at. Et est soleat tacimates, erroribus urbanitas sea at, an ferri sensibus nec. Modo commodo sit an, solum nonumy possim ut eam. Ad expetenda instructior pri. Ne utinam aliquando reprehendunt nec. Nec no dolorem consulatu. Mea nisl quaestio deseruisse in, velit repudiare moderatius ea eos. No duo summo scripserit. Eu prompta feugiat disputationi vix. His ne illud malorum dolorem. Ne sea illum nemore, pri lorem tacimates suavitate te. Nibh latine comprehensam est ut, ius vitae quaestio et. Cibo clita per ne, in nusquam albucius senserit usu, elit facer ut qui. No duo graeci scribentur referrentur. Illum solet vituperata an sit, ad quo ridens eripuit consectetuer. Nec aeterno aliquip ullamcorper id, ne postea possim sit.");
        System.out.println(doc.getText() + "\nFlesch Score should be 107.8. Result is:  " + doc.getFleschScore());



    }
}
