/**
 * 
 */
package spelling;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author dcpeterson
 *
 */
public class AutoCompleteMatchCaseTester {

	private String dictFile = "data/words.small.txt";

	AutoCompleteMatchCase emptyDict;
	AutoCompleteMatchCase smallDict;
	AutoCompleteMatchCase largeDict;
	
	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		emptyDict = new AutoCompleteMatchCase();
		smallDict = new AutoCompleteMatchCase();
		largeDict = new AutoCompleteMatchCase();

		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("hello");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		smallDict.addWord("Christine");
		smallDict.addWord("okay");

		
		DictionaryLoader.loadDictionary(largeDict, dictFile);
	}

	
	/** Test if the size method is working correctly.
	 */
	@Test
	public void testSize()
	{
		assertEquals("Testing size for empty dict", 0, emptyDict.size());
		assertEquals("Testing size for small dict", 10, smallDict.size());
		assertEquals("Testing size for large dict", 4438, largeDict.size());
	}
	
	/** Test the isWord method */
	@Test
	public void testIsWord()
	{
		assertFalse("Testing isWord on emptyDict: Hello",  emptyDict.isWord("Hello"));
		assertTrue("Testing isWord on smallDict: Hello", smallDict.isWord("Hello"));
		assertTrue("Testing isWord on largeDict: Hello",  largeDict.isWord("Hello"));

		assertTrue("Testing isWord on smallDict: hello",  smallDict.isWord("hello"));
		assertTrue("Testing isWord on largeDict: hello",  largeDict.isWord("hello"));

		assertFalse("Testing isWord on smallDict: hellow",  smallDict.isWord("hellow"));
		assertFalse("Testing isWord on largeDict: hellow",  largeDict.isWord("hellow"));

		assertFalse("Testing isWord on emptyDict: empty string",  emptyDict.isWord(""));
		assertFalse("Testing isWord on smallDict: empty string",  smallDict.isWord(""));
		assertFalse("Testing isWord on largeDict: empty string",  largeDict.isWord(""));

		assertFalse("Testing isWord on smallDict: no",  smallDict.isWord("no"));
		assertTrue("Testing isWord on largeDict: no",  largeDict.isWord("no"));

		assertTrue("Testing isWord on smallDict: subsequent",  smallDict.isWord("subsequent"));
		assertTrue("Testing isWord on largeDict: subsequent",  largeDict.isWord("subsequent"));

		assertTrue("Testing isWord on smallDict: Christine",  smallDict.isWord("Christine"));
		assertFalse("Testing isWord on smallDict: christine",  smallDict.isWord("christine"));

		assertTrue("Testing isWord on smallDict: OKAY",  smallDict.isWord("OKAY"));
		
	}
	
	/** Test the addWord method */
	@Test
	public void testAddWord()
	{


		assertFalse("Asserting hellow is not in empty dict", emptyDict.isWord("hellow"));
		assertFalse("Asserting hellow is not in small dict", smallDict.isWord("hellow"));
		assertFalse("Asserting hellow is not in large dict",  largeDict.isWord("hellow"));
		
		emptyDict.addWord("hellow");
		smallDict.addWord("hellow");
		largeDict.addWord("hellow");

		assertTrue("Asserting hellow is in empty dict after insertion", emptyDict.isWord("hellow"));
		assertTrue("Asserting hellow is in small dict after insertion",  smallDict.isWord("hellow"));
		assertTrue("Asserting hellow is in large dict after insertion",  largeDict.isWord("hellow"));

		assertFalse("Asserting xyzabc is not in empty dict",  emptyDict.isWord("xyzabc"));
		assertFalse("Asserting xyzabc is not in small dict",  smallDict.isWord("xyzabc"));
		assertFalse("Asserting xyzabc is in large dict",  largeDict.isWord("xyzabc"));

		
		emptyDict.addWord("XYZAbC");
		smallDict.addWord("XYZAbC");
		largeDict.addWord("XYZAbC");

		assertTrue("Asserting Xyzabc is in empty dict", emptyDict.isWord("Xyzabc"));
		assertTrue("Asserting Xyzabc is in small dict",  smallDict.isWord("Xyzabc"));
		assertTrue("Asserting Xyzabc is large dict",  largeDict.isWord("Xyzabc"));


		assertFalse("Testing isWord on empty: empty string",  emptyDict.isWord(""));
		assertFalse("Testing isWord on small: empty string",  smallDict.isWord(""));
		assertFalse("Testing isWord on large: empty string",  largeDict.isWord(""));

		assertFalse("Testing isWord on small: no",  smallDict.isWord("no"));
		assertTrue("Testing isWord on large: no",  largeDict.isWord("no"));
		
		assertTrue("Testing isWord on small: subsequent",  smallDict.isWord("subsequent"));
		assertTrue("Testing isWord on large: subsequent",  largeDict.isWord("subsequent"));
		
		
	}

	/** Test the addWord method */
	@Test
	public void testCaseSensitivity()  {
		emptyDict.addWord("hello");
		assertTrue("testCaseSensitivity lower-to-upper emptyDict: hello",  emptyDict.isWord("Hello"));
		emptyDict.addWord("ok");
		assertTrue("testCaseSensitivity isWord on emptyDict: OK",  emptyDict.isWord("OK"));
		emptyDict.addWord("wolverine");
		assertTrue("testCaseSensitivity isWord on emptyDict: Wolverine after add",  emptyDict.isWord("Wolverine"));
		emptyDict.addWord("Michigan");
		assertFalse("testCaseSensitivity isWord on emptyDict: michigan after add",  emptyDict.isWord("michigan"));
		assertTrue("Testing isWord on emptyDict: Michigan after add",  emptyDict.isWord("Michigan"));
	}
	
	@Test
	public void testPredictCompletions()
	{
		List<String> completions;
		completions = smallDict.predictCompletions("", 0);
		assertEquals(0, completions.size());
		
		completions = smallDict.predictCompletions("",  4);
		assertEquals(4, completions.size());
		assertTrue(completions.contains("a"));
		assertTrue(completions.contains("he"));
		boolean twoOfThree = completions.contains("hey") && completions.contains("hot") ||
				             completions.contains("hey") && completions.contains("hem") ||
				             completions.contains("hot") && completions.contains("hem");
		assertTrue(twoOfThree);
		
		completions = smallDict.predictCompletions("he", 2);
		boolean allIn = completions.contains("he") && 
				(completions.contains("hem") || completions.contains("hey"));
		assertEquals(2, completions.size());
		assertTrue(allIn);
		
		completions = smallDict.predictCompletions("hel", 10);
		assertEquals(2, completions.size());
		allIn = completions.contains("hello") && completions.contains("help");
		assertTrue(allIn);
	
		completions = smallDict.predictCompletions("x", 5);
		assertEquals(0, completions.size());
	}

	@Test
	public void testPredictCompletionsCaseSensitivity() {
		List<String> completions;

		emptyDict.addWord("Abel");
		emptyDict.addWord("Abelard");
		emptyDict.addWord("Abelian");
		emptyDict.addWord("abelia");
		emptyDict.addWord("abelias");
		completions = emptyDict.predictCompletions("abe", 5);
		assertEquals(5, completions.size());
		boolean allIn = completions.contains("abelia") && completions.contains("abelias");
		assertTrue("testPredictionsCompletionsCaseSensitivity:  lower case does pull in upper case words", allIn);
		completions = emptyDict.predictCompletions("Abe", 5);
		assertEquals(3, completions.size());
	}
	
	
	
}
