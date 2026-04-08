import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/* IMPORTANT INFO
 * 1. The Treap is a max-heap, meaning the highest priority is at the root
 * 2. Priority takes precedence over keys when it comes to ordering
 * 3. The Treap itself is an ArrayList. Entries may be empty (null) meaning the size of the ArrayList is NOT the same
 *    as the number of nodes
 * 4. The final element of the ArrayList will always be a node. If the node is deleted, trailing nulls will also be
 *    deleted
 * 5. The rotation algorithm is described here: https://en.wikipedia.org/wiki/Tree_rotation#Inorder_invariance
 */

public class Treap<K, V> {
    /* == Nested Node Class == */
    private class TNode {
        private K key;
        private V value;
        private final int priority;

        public TNode() {
            priority = generator.nextInt();
        }

        public K getKey() {return key;}
        public V getValue() {return value;}
        public int getPriority() {return priority;}
        public void setKey(K key) {this.key = key;}
        public void setValue(V value) {this.value = value;}
    }

    /* == Instance Variables == */
    private final Random generator;
    private final ArrayList<TNode> treap;
    private final Comparator<K> comparator;

    /* == Constructors == */

    /* Constructs an empty Treap with the given key comparator */
    public Treap(Comparator<K> keyComp) {
        treap = new ArrayList<>();
        generator = new Random(System.nanoTime());
        comparator = keyComp;
    }

    /* Constructs a Treap with the given RNG seed for priority generation */
    public Treap(Comparator<K> keyComp, Comparator<V> valueComp, long seed) {
        treap = new ArrayList<>();
        generator = new Random(seed);
        comparator = keyComp;
    }

    /* == Helper Functions == */

    /* Recursive helper function for search() */
    private int searchRecursive(K key, int index) {
        // Node does not exist, return its intended position
        if (index >= treap.size() || treap.get(index) == null) {
            return index;
        }

        K currKey = treap.get(index).getKey();
        if (comparator.compare(key, currKey) == 0) {
            return index;
        } else if (comparator.compare(key, currKey) < 0) {
            // Key is smaller, so search the left subtree
            return searchRecursive(key, getLeft(index));
        } else {
            // Key is bigger, so search the right subtree
            return searchRecursive(key, getRight(index));
        }
    }

    /* == Private Functions == */

    /* Returns the parent index of the node at the given index */
    private int getParent(int index) {
        if (index == 0) {
            return -1;
        }
        int div = (int) Math.ceil((double) index / 2);
        return div - 1;
    }

    /* Returns the left child index of the node at the given index */
    private int getLeft(int index) {
        return (index * 2) + 1;
    }

    /* Returns the right child index of the node at the given index */
    private int getRight(int index) {
        return (index * 2) + 2;
    }

    /* Returns -1 if node1 < node2, 0 if node1 == node 2, and 1 if node1 > node2 */
    private int comparePriorities(TNode node1, TNode node2) {
        if (node1.getPriority() < node2.getPriority()) {
            return -1;
        } else if (node1.getPriority() > node2.getPriority()) {
            return 1;
        }
        return 0;
    }

    /* Searches the tree for the given key using binary search */
    /* Returns the node's index if it exists, or the index where it should be otherwise */
    private int search(K key) {
        if (treap.isEmpty()) {
            return 0;
        }
        return searchRecursive(key, 0);
    }

    /* Swaps the nodes at the two given indices */
    private void swap(int p, int q) {
        TNode node1 = treap.get(p);
        TNode node2 = treap.get(q);
        treap.set(p, node2);
        treap.set(q, node1);
    }

    /* Adds the given node to the ArrayList at the specified index */
    /* Instantiates missing indices as null, if the index is not contiguous */
    private void addAtIndex(int index, TNode node) {
        for (int i = treap.size(); i < index; i++) {
            treap.set(i, null);
        }
        treap.set(index, node);
    }

    /* Rotates the subtree starting at the root index to the right */
    /* Used in conjunction with rotateLeft() to maintain the heap invariant */
    private void rotateRight(int q) {
        int p = getLeft(q); // Let P be Q's left child
        int pRightChild = getRight(p); // Set Q's left child to be P's right child
        swap(p, pRightChild);
        swap(pRightChild, q); // Set P's right child to be Q
    }

    /* Rotates the subtree starting at the root index to the left */
    /* Used in conjunction with rotateRight() to maintain the heap invariant */
    private void rotateLeft(int p) {
        int q = getRight(p); // Let Q be P's right child
        int qLeftChild = getLeft(q); // Set P's right child to be Q's left child
        swap(q, qLeftChild);
        swap(qLeftChild, p); // Set Q's left child to be P
    }

    /* == Public Functions == */

    /* Returns the value stored in a specific key, or null if it does not exist */
    public V get(K key) {
        // TODO: Ramin
        return null;
    }

    /* Inserts the value into the Treap at the specified key */
    public void insert(K key, V value) {
        int index = search(key);

        // Node already exists; update it
        if (index < treap.size() && treap.get(index) != null) {
            treap.get(index).setValue(value);
            return;
        }

        // Create a new node, respecting ONLY the binary tree ordering
        TNode node = new TNode();
        node.setKey(key);
        node.setValue(value);
        addAtIndex(index, node);

        // Now we check heap invariant by rotating the subtree if necessary
        int parent = getParent(index);
        if (parent == -1) {
            // Node is the root, so ignore it
            return;
        }

        TNode parentNode = treap.get(parent);
        if (node.getPriority() > parentNode.getPriority()) {
            // Check which side the new node is on
            if (getLeft(parent) == index) {
                // New node is on the left, so rotate right
                rotateRight(parent);
            } else {
                // New node is on the right, so rotate left
                rotateLeft(parent);
            }
        }
    }
}
