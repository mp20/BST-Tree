import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Ariya Nazari Foroshani
 * @version 1.0
 * @userid aforoshani3
 * @GTID 903627990
 *
 * Collaborators: NONE
 *
 * Resources: NONE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("collection is null");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("element is null");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        root = rAdd(root, data);
    }

    /**
     *
     * @param curr the current node
     * @param data the data being added
     * @return the Nodes that are recursed through
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */

    public T remove(T data) {
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = rRemove(root, data, dummy);
        return dummy.getData();
    }

    /**
     *
     * @param root the current node in recursion
     * @param data the data being removed
     * @param dummy Node used to get the data in removed node
     * @return returns the node being removed
     */
    private BSTNode<T> rRemove(BSTNode<T> root, T data, BSTNode<T> dummy) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("the data is not in the tree");
        }
        if (root == null) {
            return root;
        } else if (data.compareTo(root.getData()) < 0) {
            root.setLeft(rRemove(root.getLeft(), data, dummy));
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(rRemove(root.getRight(), data, dummy));
        } else {
            dummy.setData(root.getData());
            size--;
            if (root.getLeft() == null && root.getRight() == null) {
                return null;
            } else if (root.getLeft() != null && root.getRight() == null) {
                return root.getLeft();
            } else if (root.getRight() != null && root.getLeft() == null) {
                return root.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                root.setRight(removeSuccessor(root.getRight(), dummy2));
                root.setData(dummy2.getData());
            }
        }
        return root;
    }

    /**
     * Helps to do the actual removing and setting the dummy nodes data
     *
     * @param root the node currently in recursion
     * @param dummy what contains the actual data from the removed node
     * @return returns the node to remove
     */

    private BSTNode<T> removeSuccessor(BSTNode<T> root, BSTNode<T> dummy) {
        if (root.getLeft() == null) {
            dummy.setData(root.getData());
            return root.getRight();
        } else {
            root.setLeft(removeSuccessor(root.getLeft(), dummy));
        }
        return root;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */

    public T get(T data) {
        return rGet(root, data);
    }

    /**
     *
     * @param root the current node
     * @param data the data we are searching for
     * @return returns the data
     */
    private T rGet(BSTNode<T> root, T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (root == null) {
            throw new NoSuchElementException("data is not in the tree");
        } else if (root.getData() == data) {
            return root.getData();
        } else if (root.getData().compareTo(data) > 0) {
            return rGet(root.getLeft(), data);
        } else if (root.getData().compareTo(data) < 0) {
            return rGet(root.getRight(), data);
        }
        return root.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */

    public boolean contains(T data) {
        return rContains(root, data);
    }

    /**
     *
     * @param root the current node
     * @param data the data we are searching for
     * @return returns wether or not the data was found/ is in the tree
     */
    private boolean rContains(BSTNode<T> root, T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (root == null) {
            return false;
        } else if (root.getData() == data) {
            return true;
        } else if (root.getData().compareTo(data) > 0) {
            return rContains(root.getLeft(), data);
        } else if (root.getData().compareTo(data) < 0) {
            return rContains(root.getRight(), data);
        }
        return true;
    }


    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>(size);
        preorderHelper(root, list);
        return list;
    }

    /**
     *
     * @param root current node
     * @param list the list being added to for return on the main method
     */
    private void preorderHelper(BSTNode<T> root, List<T> list) {
        List<T> listHelper = list;
        if (root == null) {
            return;
        }
        listHelper.add(root.getData());
        preorderHelper(root.getLeft(), listHelper);
        preorderHelper(root.getRight(), listHelper);
        return;
    }


    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */

    public List<T> inorder() {
        List<T> list = new ArrayList<>(size);
        inorderHelper(root, list);
        return list;
    }

    /**
     * used to help inorder by doing the process recursively.
     *
     * @param root the current node
     * @param list the list being returned in the actual method at the end
     */
    private void inorderHelper(BSTNode<T> root, List<T> list) {
        List<T> listHelper = list;
        if (root == null) {
            return;
        }
        inorderHelper(root.getLeft(), listHelper);
        listHelper.add(root.getData());
        inorderHelper(root.getRight(), listHelper);
        return;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */

    public List<T> postorder() {
        List<T> list = new ArrayList<>(size);
        postorderHelper(root, list);
        return list;
    }

    /**
     * used to help postorder by doing the process recursively.
     *
     * @param root the current node
     * @param list the list being added to, so that it can later be returned by the actual method
     */
    private void postorderHelper(BSTNode<T> root, List<T> list) {
        List<T> listHelper = list;
        if (root == null) {
            return;
        }
        postorderHelper(root.getLeft(), listHelper);
        postorderHelper(root.getRight(), listHelper);
        listHelper.add(root.getData());
        return;
    }


    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */

    public List<T> levelorder() {
        List<T> list = new ArrayList<>(size);
        if (root == null) {
            return list;
        }
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            root = q.poll();
            list.add(root.getData());
            if (root.getLeft() != null) {
                q.add(root.getLeft());
            }
            if (root.getRight() != null) {
                q.add(root.getRight());
            }
        }
        return list;
    }


    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */

    public int height() {
        return rHeight(root);
    }

    /**
     * used to get the height of the tree recursively.
     *
     * @param root the current node
     * @return the number representing the height
     */
    private int rHeight(BSTNode<T> root) {
        if (root == null) {
            return -1;
        }
        return Math.max(rHeight(root.getLeft()), rHeight(root.getRight())) + 1;
    }


    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */

    public void clear() {
        root = null;
        size = 0;
    }



    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */

    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("not a valid request");
        }
        List<T> list = new LinkedList<>();
        kHelper(root, k, list);
        return list;
    }

    /**
     * Used to assist kLargest by using recursion.
     *
     * @param root the current node
     * @param k how many of the largest elements we want
     * @param list the list being returned at the end
     */
    private void kHelper(BSTNode<T> root, int k, List<T> list) {
        List<T> listHelper = list;
        while (k > listHelper.size()) {
            if (root == null) {
                return;
            }
            kHelper(root.getRight(), k, listHelper);
            if (k > listHelper.size()) {
                listHelper.add(0, root.getData());
            }
            kHelper(root.getLeft(), k, listHelper);
            return;
        }
        return;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
