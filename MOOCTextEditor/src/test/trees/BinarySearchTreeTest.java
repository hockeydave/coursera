package trees;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dcpeterson
 * Test guidelines:
 * 2.  Test null elements passed in to add or remove returns false
 * 3.  Test happy paths
 * 4.  Test add, remove, contains methods
 *
 */

public class BinarySearchTreeTest {
    BinarySearchTree<String> bst1;
    BinarySearchTree<Integer> emptyBST;

    @Before
    public void setUp() throws Exception {
        emptyBST = new BinarySearchTree<>();
        bst1 = new BinarySearchTree<>();
        bst1.insert("C");
        bst1.insert("A");
        bst1.insert("B");

        bst1.insert("D");
        bst1.insert("E");
        bst1.insert("F");
        // C(A()(B))(D()(E()(F)))
        //System.out.println(bst1.toString());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInsert() {
        assertFalse("insert empty BST null element:  ", emptyBST.remove(null));
        assertTrue("insert element10 into empty tree", emptyBST.insert(10));
        assertFalse("insert element 10 into BST again", emptyBST.insert(10));
        assertEquals("insert element 10 to empty BST Size", 1, emptyBST.getSize());
        emptyBST.insert(15);
        emptyBST.insert(5);
        emptyBST.insert(8);
        emptyBST.insert(7);
        emptyBST.insert(20);
        emptyBST.insert(13);
        emptyBST.insert(4);
        assertEquals("insert elements to empty BST Size", 8, emptyBST.getSize());
        //System.out.println("insert 10, 15, 5 8, 7, 20, 13, 4:  " + emptyBST.toString());
        assertEquals("Insert 10, 15, 5 8, 7, 20, 13, 4: elements toString", "10(5(4)(8(7)))(15(13)(20))", emptyBST.toString());
    }

    @Test
    public void testRemove() {
        assertFalse("remove empty BST:  ", emptyBST.remove(1));
        assertFalse("remove empty BST null:  ", emptyBST.remove(null));
    }

    @Test
    public void testRemoveLeaf() {
        assertTrue("RemoveLeaf BST:  ", bst1.remove("F"));
        // System.out.println("C(A()(B))(D()(E()(F))) RemoveLeaf F:  " + bst1.toString());
        assertEquals("RemoveLeaf F Size:  for BST C, A, B, D, E, F", 5, bst1.getSize());
        assertFalse("RemoveLeaf F Contains:   for BST C, A, B, D, E, F", bst1.contains("F"));
        assertEquals("RemoveLeaf F toString:  for BST C, A, B, D, E, F", "C(A()(B))(D()(E))", bst1.toString());
    }

    @Test
    public void testRemoveOneChild() {
        assertTrue("RemoveOneChild BST:  ", bst1.remove("A"));
        //System.out.println("C(A()(B))(D()(E()(F))) RemoveOneChild A:  " + bst1.toString());
        assertEquals("RemoveOneChild A Size:  for BST C, A, B, D, E, F", 5, bst1.getSize());
        assertFalse("RemoveOneChild A Contains", bst1.contains("A"));
        assertEquals("RemoveOneChild A toString:  for BST C, A, B, D, E, F", "C(B)(D()(E()(F)))", bst1.toString());
    }

    @Test
    public void testRemoveTwoChild() {
        bst1.remove("D");
        //System.out.println("C(A()(B))(D()(E()(F))) RemoveTwoChild D:  " + bst1.toString());
        assertEquals("RemoveTwoChild D Size:  for BST C, A, B, D, E, F", 5, bst1.getSize());
        assertFalse("RemoveTwoChild D Contains", bst1.contains("D"));
        assertEquals("RemoveTwoChild D toString:  for BST C, A, B, D, E, F", "C(A()(B))(E()(F))", bst1.toString());
    }

    @Test
    public void testSize() {
        assertEquals("Size:  for BST C, A, B, D, E, F", 6, bst1.getSize());
        assertEquals("Size:  for empty BST", 0, emptyBST.getSize());
        emptyBST.insert(5);
        emptyBST.insert(6);
        assertEquals("Size:  for empty BST", 2, emptyBST.getSize());
    }

    @Test
    public void testContains() {
        assertTrue("Contains:  for BST A", bst1.contains("A"));
        assertTrue("Contains:  for BST F", bst1.contains("F"));
        assertFalse("Contains:  for BST F", bst1.contains("G"));
        assertFalse("Contains:  for BST empty", emptyBST.contains(1));
        emptyBST.insert(1);
        assertTrue("Contains:  for BST post-empty", emptyBST.contains(1));
    }
}