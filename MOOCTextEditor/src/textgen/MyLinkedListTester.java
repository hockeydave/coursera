/**
 *
 */
package textgen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author UC San Diego MOOC team
 * @author dcpeterson
 * Test guidelines:
 * 1.  Test high and low out of bounds on indexes throws IOOBE
 * 2.  Test null elements passed in to add or set throws NPE
 * 3.  Test happy paths
 * 4.  Test add(s), remove, get, set methods
 *
 */
public class MyLinkedListTester {

    private static final int LONG_LIST_LENGTH = 10;

    MyLinkedList<String> shortList;
    MyLinkedList<Integer> emptyList;
    MyLinkedList<Integer> longerList;
    MyLinkedList<Integer> list1;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Feel free to use these lists, or add your own
        shortList = new MyLinkedList<>();
        shortList.add("A");
        shortList.add("B");
        emptyList = new MyLinkedList<>();
        longerList = new MyLinkedList<>();
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            longerList.add(i);
        }
        list1 = new MyLinkedList<>();
        list1.add(65);
        list1.add(21);
        list1.add(42);
    }


    /** Test if the get method is working correctly.
     */
    /*You should not need to add much to this method.
     * We provide it as an example of a thorough test. */
    @Test
    public void testGet() {
        //test empty list, get should throw an exception
        try {
            emptyList.get(0);
            fail("Get:  Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }

        // test short list, first contents, then out of bounds
        assertEquals("Get:  Check first", "A", shortList.get(0));
        assertEquals("Get:  Check second", "B", shortList.get(1));

        try {
            shortList.get(-1);
            fail("Get:  Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            shortList.get(2);
            fail("Get:  Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        // test longer list contents
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            assertEquals("Get:  Check " + i + " element", (Integer) i, longerList.get(i));
        }

        // test off the end of the longer array
        try {
            longerList.get(-1);
            fail("Get:  Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            longerList.get(LONG_LIST_LENGTH);
            fail("Get:  Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        }

    }


    /** Test removing an element from the list.
     * We've included the example from the concept challenge.
     * You will want to add more tests.  */
    @Test
    public void testRemove() {
        try {
            list1.remove(-1);
            fail("Remove: Check low out of bounds on remove of non empty list");
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            emptyList.remove(0);
            fail("Remove: Check low out of bounds on remove of empty list");
        } catch (IndexOutOfBoundsException e) {
        }
        int a = list1.remove(0);
        assertEquals("Remove: check a is correct ", 65, a);
        assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
        assertEquals("Remove: check size is correct ", 2, list1.size());

        // TODO: Add more tests here
        a = list1.remove(1);
        assertEquals("Remove: check a is correct ", 42, a);
        assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
        assertEquals("Remove: check size is correct ", 1, list1.size());

        a = list1.remove(0);
        assertEquals("Remove: check a is correct ", 21, a);

        try {
            a = list1.remove(0);
            fail("Remove: Check out of bounds on remove of non empty list");
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            a = list1.remove(list1.size());
            fail("Remove: Check high out of bounds on remove of non empty list");
        } catch (IndexOutOfBoundsException e) {
        }
        assertEquals("Remove: check size is correct ", 0, list1.size());
    }

    /** Test adding an element into the end of the list, specifically
     *  public boolean add(E element)
     * */
    @Test
    public void testAddEnd() {
        // TODO: implement this test
        Integer a = null;
        try {
            list1.add(a);
            fail("AddEnd: Check adding null element");
        } catch (NullPointerException npe) {
        }
        a = 99;
        list1.add(a);
        assertEquals("AddEnd: check element 3 is correct ", a, list1.get(list1.size() - 1));
        assertEquals("AddEnd: check size is correct ", 4, list1.size());
        a = 100;
        list1.add(a);
        assertEquals("AddEnd: check element 4 is correct ", a, list1.get(list1.size() - 1));
        assertEquals("AddEnd: check size is correct ", 5, list1.size());
    }


    /** Test the size of the list */
    @Test
    public void testSize() {
        // TODO: implement this test
        assertEquals("Size:  test list1 ", 0, emptyList.size());
        assertEquals("Size:  test list1 ", LONG_LIST_LENGTH, longerList.size());
        assertEquals("Size:  test list1 ", 3, list1.size());
        list1.remove(2);
        list1.remove(1);
        list1.remove(0);
        assertEquals("Size:  test list1 ", 0, list1.size());
        list1.add(99);
        assertEquals("Size:  test list1 ", 1, list1.size());
    }


    /** Test adding an element into the list at a specified index,
     * specifically:
     * public void add(int index, E element)
     * */
    @Test
    public void testAddAtIndex() {
        // TODO: implement this test
        try {
            list1.add(0, null);
            fail("AddAtIndex: Check adding null element");
        } catch (NullPointerException npe) {
        }
        try {
            list1.add(-1, 99);
            fail("AddAtIndex: Check low out of bounds on add at negative index to list");
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            list1.add(list1.size() + 1, 99);
            fail("AddAtIndex: Check high out of bounds on add high index to list");
        } catch (IndexOutOfBoundsException e) {
        }

        emptyList.add(0, 99);
        emptyList.remove(0);
        emptyList.add(0, 100);
        assertEquals("AddAtIndex: check element 0 add/remove is correct ", (Integer) 100, emptyList.get(0));

        int l1size = list1.size();
        Integer b = list1.get(0);
        list1.add(0, 99);
        assertEquals("AddAtIndex: check element 0 is correct ", (Integer) 99, list1.get(0));
        assertEquals("AddAtIndex: check element 1 is correct ", b, list1.get(1));
        assertEquals("AddAtIndex:  test list1 ", l1size + 1, list1.size());
        b = list1.get(list1.size() - 1);
        l1size = list1.size();
        list1.add(list1.size() - 1, 100);
        assertEquals("AddAtIndex: check element 2 is correct ", (Integer) 100, list1.get(3));
        assertEquals("AddAtIndex: check element 3 is correct ", b, list1.get(4));
        assertEquals("AddAtIndex:  test list1 ", l1size + 1, list1.size());
    }

    /** Test setting an element in the list */
    @Test
    public void testSet() {
        // TODO: implement this test
        try {
            list1.set(0, null);
            fail("Set: set null element failed to throw exception");
        } catch (NullPointerException e) {
        }
        try {
            list1.set(-1, 99);
            fail("Set: Check low out of bounds on add at negative index to list");
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            list1.set(list1.size(), 99);
            fail("Set: Check high out of bounds on add beyond end of list");
        } catch (IndexOutOfBoundsException e) {
        }

        Integer a = list1.set(0, 99);
        assertEquals("Set: check element 0 is correct ", (Integer) 65, a);
        assertEquals("Set: check element 0 is correct ", (Integer) 99, list1.get(0));
        assertEquals("Set: check element 1 is correct ", (Integer) 21, list1.get(1));

        a = list1.set(list1.size() - 1, 100);
        assertEquals("Set: check element tail is correct ", (Integer) 100, list1.get(list1.size() - 1));
        assertEquals("Set:  test size ", 3, list1.size());
    }


    // TODO: Optionally add more test methods.

}
