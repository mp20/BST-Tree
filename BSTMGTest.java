//import Assignments.BinarySearchTreeImpl.BST;
//import Assignments.BinarySearchTreeImpl.BSTNode;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Miles Gordon
 *
 * These are the additional test cases that I used for my BST.
 * Please note that these are far from comprehensive (several
 * methods are never even tested).  However, I believe they are
 * a good supplement to the existing test cases that I would strongly
 * encourage you all to use as well.
 *
 */
public class BSTMGTest {

    private static final int TIMEOUT = 200;
    private BST<Integer> tree;

    @Before
    public void setup() {
        tree = new BST<>();
    }

    /**
     * Passes if adding, removing, and traversing all work as
     * expected in degenerate cases where every node has only
     * right children
     */
    @Test(timeout = TIMEOUT)
    public void testDegenerateRight() {
        tree.add(1);
        tree.add(tree.remove(1));
        tree.add(2);
        assertTrue(tree.getRoot().getRight() != null && tree.getRoot().getLeft() == null);
        tree.remove(2);
        assertFalse(tree.getRoot().getRight() != null && tree.getRoot().getLeft() == null);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);
        tree.add(8);
        tree.add(150);
        tree.add(2000);
        assertEquals(10, tree.size());
        Integer t = 150;
        assertEquals(tree.getRoot().getRight().getRight().getRight().getRight().getRight().getRight().getRight().getRight().getData(), t); // Should be 150
        List<Integer> order = new ArrayList<>();
        order.add(1);
        order.add(2);
        order.add(3);
        order.add(4);
        order.add(5);
        order.add(6);
        order.add(7);
        order.add(8);
        order.add(150);
        order.add(2000);
        assertEquals(order, tree.levelorder());
        assertEquals(order, tree.inorder());
        assertEquals(order, tree.preorder());
        Collections.reverse(order);
        assertEquals(order, tree.postorder());
        Collections.reverse(order);
        t = new Integer(5);
        assertNotSame(tree.remove(5), t);
        order.remove(4);
        assertEquals(order, tree.levelorder());
        assertEquals(order, tree.inorder());
        assertEquals(order, tree.preorder());
        Collections.reverse(order);
        assertEquals(order, tree.postorder());
        tree.clear();
        assertEquals(tree.size(), 0);
    }

    @Test(timeout = TIMEOUT)
      /**
     * Passes if adding, removing, and traversing all work as
     * expected in degenerate cases where every node has only
     * left children
     */
    public void testDegenerateLeft() {
        tree.add(2000);
        tree.add(150);
        tree.add(8);
        tree.add(7);
        tree.add(6);
        tree.add(5);
        tree.add(4);
        tree.add(3);
        tree.add(2);
        tree.add(1);
        tree.add(tree.remove(1));
        List<Integer> order = new ArrayList<>();
        order.add(1);
        order.add(2);
        order.add(3);
        order.add(4);
        order.add(5);
        order.add(6);
        order.add(7);
        order.add(8);
        order.add(150);
        order.add(2000);
        assertEquals(order, tree.inorder());
        assertEquals(order, tree.postorder());
        Collections.reverse(order);
        assertEquals(order, tree.levelorder());
        assertEquals(order, tree.preorder());
        Integer t = new Integer(5);
        assertNotSame(tree.remove(5), t);
        order.remove(5);
        assertEquals(order, tree.levelorder());
        assertEquals(order, tree.preorder());
        Collections.reverse(order);
        assertEquals(order, tree.inorder());
        assertEquals(order, tree.postorder());
        tree.remove(150);
        assertEquals("You passed—that's gr8 :D!", String.format("You passed—that's gr%d :D!", tree.getRoot().getLeft().getData()));
    }

     /**
     * Passes if adding, removing, and traversing all work as
     * expected in combined degenerate cases
     */
    @Test(timeout = TIMEOUT)
    public void testDegenerateCombo() {
        tree.add(2000);
        tree.add(150);
        tree.add(-6);
        tree.add(8);
        tree.add(7);
        tree.add(6);
        tree.add(5);
        tree.add(4);
        tree.add(3);
        tree.add(2);
        tree.add(1);
        tree.add(tree.remove(1));
        List<Integer> order = new ArrayList<>();
        order.add(1);
        order.add(2);
        order.add(3);
        order.add(4);
        order.add(5);
        order.add(6);
        order.add(7);
        order.add(8);
        order.add(-6);
        order.add(150);
        order.add(2000);
        assertEquals(order, tree.postorder());
        Collections.reverse(order);
        assertEquals(order, tree.levelorder());
        assertEquals(order, tree.preorder());
        Integer t = new Integer(5);
        assertNotSame(tree.remove(5), t);
        order.remove(6);
        assertEquals(order, tree.levelorder());
        assertEquals(order, tree.preorder());
        Collections.reverse(order);
        assertEquals(order, tree.postorder());
        assertTrue(tree.contains(150));
        tree.remove(150);
        assertFalse(tree.contains(150));
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(8);
        expected.add(2000);
        List<Integer> actual = tree.kLargest(2);
        assertEquals(expected, actual);
        assertEquals("You passed—that's gr8 :D!", String.format("You passed—that's gr%d :D!", tree.getRoot().getLeft().getRight().getData()));
    }

     /**
     * Runs add and remove operations and makes sure that they leave 
     * you with a proper tree at all times
     */
    @Test(timeout = 2500)
    public void testValidation() {
        HashSet<Integer> treedata = new HashSet<>();
        for(int x = 0; x < 10000; x++) {
            int t = (int) Math.round(Math.random() * 1000000);
            tree.add(t);
            treedata.add(t);
            assertTrue(bstValidator(tree));
            assertEquals(tree.size(), treedata.size());
        }
        Iterator<Integer> myiter = treedata.iterator();
        while (myiter.hasNext()) {
            int t = myiter.next();
            myiter.remove();
            tree.remove(t);
            assertTrue(bstValidator(tree));
            assertEquals(tree.size(), treedata.size());
        }
        assertNull(tree.getRoot());
    }

    private boolean bstValidator(BST<Integer> bst) {
        return bstValidatorHelper(bst.getRoot(), null, null);
    }

    private boolean bstValidatorHelper(BSTNode<Integer> root, Integer max, Integer min) {
        if (root == null) {
            return true;
        }
        Integer data = root.getData();
        if (((max == null || data.compareTo(max) < 0)) && (min == null || data.compareTo(min) > 0)) {
            return bstValidatorHelper(root.getRight(), max, root.getData())
                    && bstValidatorHelper(root.getLeft(), root.getData(), min);
        }
        return false;
    }
}
