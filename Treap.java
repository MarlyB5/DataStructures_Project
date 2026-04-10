import java.util.Comparator;
import java.util.Random;

/* IMPORTANT INFO
 * 1. The Treap is a max-heap, meaning the highest priority is at the root
 * 2. Priority takes precedence over keys when it comes to ordering
 * 3. The rotation algorithm is described here: https://en.wikipedia.org/wiki/Tree_rotation#Inorder_invariance
 */

public class Treap<K, V> {
    /* == Nested Node Class == */
    private class TNode {
        private K key;
        private V value;
        private final int priority;

        private TNode parent;
        private TNode left;
        private TNode right;

        public TNode() {
            priority = generator.nextInt();
        }

        public K getKey() {return key;}
        public V getValue() {return value;}
        public int getPriority() {return priority;}
        public TNode getParent() {return parent;}
        public TNode getLeft() {return left;}
        public TNode getRight() {return right;}

        public void setKey(K key) {this.key = key;}
        public void setValue(V value) {this.value = value;}
        public void setParent(TNode parent) {this.parent = parent;}
        public void setLeft(TNode left) {
            this.left = left;
            if (left != null) {
                left.parent = this;
            }
        }
        public void setRight(TNode right) {
            this.right = right;
            if (right != null) {
                right.parent = this;
            }
        }
    }

    /* == Nested Search Result Class == */
    /* Contains the value of the search result (null if not found) as well as additional information used for inserts */
    private class SearchResult {
        private final V value;
        private final TNode node;
        private final TNode parentNode;
        private final Boolean isLeftChild;

        public SearchResult(V value, TNode node) {
            this.value = value;
            this.node = node;
            parentNode = null;
            isLeftChild = null;
        }

        public SearchResult(TNode parentNode, Boolean isLeftChild) {
            value = null;
            node = null;
            this.parentNode = parentNode;
            this.isLeftChild = isLeftChild;
        }

        public V getValue() {return value;}
        public TNode getNode() {return node;}
        public TNode getParentNode() {return parentNode;}
        public Boolean isLeftChild() {return isLeftChild;}
    }

    /* == Instance Variables == */
    private final Random generator;
    private TNode root;
    private final Comparator<K> comparator;

    /* == Constructors == */

    /* Constructs an empty Treap with the given key comparator */
    public Treap(Comparator<K> keyComp) {
        root = null;
        generator = new Random(System.nanoTime());
        comparator = keyComp;
    }

    /* Constructs a Treap with the given RNG seed for priority generation */
    public Treap(Comparator<K> keyComp, long seed) {
        root = null;
        generator = new Random(seed);
        comparator = keyComp;
    }

    /* == Recursive Helpers == */
    private SearchResult searchRecursive(K key, TNode root, TNode previous) {
        // Return null and the theoretical position
        if (root == null) {
            if (previous == null) {
                return new SearchResult(null, false);
            }
            int comp = comparator.compare(key, previous.getKey()); // Left if comp < 0
            return new SearchResult(previous, (comp < 0));
        }

        int comp = comparator.compare(key, root.getKey());
        if (comp == 0) {
            // We found our value
            return new SearchResult(root.getValue(), root);
        } else if (comp < 0) {
            // Search left subtree
            return searchRecursive(key, root.getLeft(), root);
        } else {
            // Search right subtree
            return searchRecursive(key, root.getRight(), root);
        }
    }

    // inOrderHelper() - recursive helper for inOrder()
    // goes left, prints current node, goes right
    private void inOrderHelper(TNode node) {
        if (node == null)
            return;

        // goes left first (smaller keys)
        inOrderHelper(node.getLeft());

        // prints current nodes key and value
        System.out.print(node.getKey() + "=" + node.getValue() + " ");

        // then goes right (larger keys)
        inOrderHelper(node.getRight());
    }

    // countNodes() - helper method for size()
    // recursively counts all nodes
    private int countNodes(TNode node) {
        // if node is null, return 0
        if (node == null)
            return 0;

        // count this node + all nodes on left + all nodes on right
        return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
    }

    /* == Private Functions == */

    /* Searches for a specific key and returns a SearchResult object based on the result */
    /* If the key is found, the value and node will be returned */
    /* Otherwise, the parent node and whether the key would be the left or right child is returned */
    private SearchResult search(K key) {
        return searchRecursive(key, root, null);
    }

    /* Rotates the subtree starting at the root index to the right */
    /* Used in conjunction with rotateLeft() to maintain the heap invariant */
    private void rotateRight(TNode q) {
        TNode qParent = q.getParent();
        TNode p = q.getLeft(); // Let P be Q's left child
        if (p != null) {
            TNode pRightChild = p.getRight(); // Set Q's left child to be P's right child
            q.setLeft(pRightChild);
            p.setRight(q); // Set P's right child to be Q
        }

        // Rebuild connection to rest of tree
        if (qParent == null) {
            root = p;
        } else if (qParent.getLeft() == q) {
            qParent.setLeft(p);
        } else {
            qParent.setRight(p);
        }
    }

    /* Rotates the subtree starting at the root index to the left */
    /* Used in conjunction with rotateRight() to maintain the heap invariant */
    private void rotateLeft(TNode p) {
        TNode pParent = p.getParent();
        TNode q = p.getRight(); // Let Q be P's right child
        if (q != null) {
            TNode qLeftChild = q.getLeft(); // Set P's right child to be Q's left child
            p.setRight(qLeftChild);
            q.setLeft(p); // Set Q's left child to be P
        }

        // Rebuild connection to rest of tree
        if (pParent == null) {
            root = p;
        } else if (pParent.getLeft() == p) {
            pParent.setLeft(q);
        } else {
            pParent.setRight(q);
        }
    }

    /* == Public Functions == */

    /* Inserts the value into the Treap at the specified key */
    public void insert(K key, V value) {
        SearchResult result = search(key);

        // Node already exists; update it
        TNode existingNode = result.getNode();
        if (existingNode != null) {
            existingNode.setValue(value);
            return;
        }

        // Create a new node, respecting ONLY the binary tree ordering
        TNode node = new TNode();
        TNode parent = result.getParentNode();
        node.setKey(key);
        node.setValue(value);
        if (parent == null) {
            root = node;
        } else if (result.isLeftChild()) {
            parent.setLeft(node);
        } else {
            parent.setRight(node);
        }

        // Now we check heap invariant by repeatedly rotating the subtree as long as child priority > parent priority
        while (parent != null && (node.getPriority() > parent.getPriority())) {
            if (parent.getLeft() == node) {
                // Node is on the left, so rotate right
                rotateRight(parent);
            } else {
                // Node is on the right, so rotate left
                rotateLeft(parent);
            }
            parent = node.getParent();
        }
    }

    // get() - returns value if found, null if not
    public V get(K key) {
        SearchResult result = search(key);

        // if node exists, return value
        if (result.getNode() != null) {
            return result.getValue();
        }

        // if node not found return null
        return null;
    }

    // isEmpty() - returns true if tree has no nodes
    public boolean isEmpty() {
        // if root is null, tree is empty
        return root == null;
    }

    // size() - returns how many nodes in tree
    public int size() {
        // starts counting from root
        return countNodes(root);
    }

    // inOrder() - prints keys in sorted order (smallest to largest)
    public void inOrder() {
        inOrderHelper(root);
        System.out.println(); // new line after printing
    }

    // containsKey() - check if key exists
    public boolean containsKey(K key) {
        if (root == null) return false;
        SearchResult result = search(key);
        return result.getNode() != null;
    }

    // firstKey() - returns smallest key
    public K firstKey() {
        if (root == null) {
            return null;
        }

        TNode current = root;
        // goes left as far as possible
        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        return current.getKey();
    }

    // lastKey() - return largest key
    public K lastKey() {
        if (root == null) {
            return null;
        }

        TNode current = root;
        // goes right as far as possible
        while (current.getRight() != null) {
            current = current.getRight();
        }

        return current.getKey();
    }

    // delete() - removes node with given key
    public void delete(K key) {
        // if tree is empty, nothing to delete
        if (root == null) {
            return;
        }

        // finds node to delete
        SearchResult result = search(key);

        // if not found, does nothing
        if (result.getNode() == null) {
            return;
        }

        TNode node = result.getNode();
        // rotate node down until it becomes a leaf
        while (node.getLeft() != null || node.getRight() != null) {
            // if no left child, rotate left
            if (node.getLeft() == null) {
                rotateLeft(node);
            }

            // if no right child, rotate right
            else if (node.getRight() == null) {
                rotateRight(node);
            }

            // if both children exist, rotate with child with higher priority
            else if (node.getLeft().getPriority() > node.getRight().getPriority()) {
                rotateRight(node);
            } else {
                rotateLeft(node);
            }
        }

        // now node is leaf - remove it
        TNode parent = node.getParent();
        // if node is the root
        if (parent == null) {
            root = null;
        }

        // else remove it from its parent
        else if (parent.getLeft() == node) {
            parent.left = null;
        } else {
            parent.right = null;
        }
    }
}
