import java.util.*;

/* IMPORTANT INFO
 * 1. The Treap is a max-heap, meaning the highest priority is at the root
 * 2. Keys determine the binary search tree placements
 * 3. Priority will determine whether the rotations are needed to maintain the heap invariant
 * 4. The rotation algorithm is described here: https://en.wikipedia.org/wiki/Tree_rotation#Inorder_invariance
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
            priority = generator.nextInt(Integer.MAX_VALUE);
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

    /* == Nested Printer Class == */
    /* A binary tree printer, taken from our lab work and modified to work with the treap */
    public class TreapPrinter {

        private final boolean squareBranches = true;
        private final boolean lrAgnostic = false;
        private final int hspace = 2;
        //private int tspace = 1;

        public TreapPrinter() {
        }

        public String print() {
            List<TreeLine> treeLines = buildTreeLines(root);
            return printTreeLines(treeLines);
        }

        private static class TreeLine {
            String line;
            int leftOffset;
            int rightOffset;

            TreeLine(String line, int leftOffset, int rightOffset) {
                this.line = line;
                this.leftOffset = leftOffset;
                this.rightOffset = rightOffset;
            }
        }

        private String printTreeLines(List<TreeLine> treeLines) {
            StringBuilder sb = new StringBuilder();

            if (!treeLines.isEmpty()) {
                int minLeftOffset = minLeftOffset(treeLines);
                int maxRightOffset = maxRightOffset(treeLines);
                for (TreeLine treeLine : treeLines) {
                    //System.out.println("line: " + treeLine.line);
                    int leftSpaces = -(minLeftOffset - treeLine.leftOffset);
                    int rightSpaces = maxRightOffset - treeLine.rightOffset;
                    //outStream.println(spaces(leftSpaces) + treeLine.line + spaces(rightSpaces));
                    sb.append(spaces(leftSpaces)).append(treeLine.line).append(spaces(rightSpaces));
                    sb.append("\n");
                }
            }
            return sb.toString();
        }

        private static int minLeftOffset(List<TreeLine> treeLines) {
            return treeLines.stream().mapToInt(l -> l.leftOffset).min().orElse(0);
        }

        private static int maxRightOffset(List<TreeLine> treeLines) {
            return treeLines.stream().mapToInt(l -> l.rightOffset).max().orElse(0);
        }

        private List<TreeLine> buildTreeLines(TNode root) {
            if (root == null) {
                //System.out.println("root: " + Collections.emptyList());
                return Collections.emptyList();
            } else {
                String rootLabel = root.getKey() + " (" + root.getPriority() + ")";//getLabel.apply(root);
                //System.out.println("rootLabel: " + rootLabel);

                //List<TreeLine> leftTreeLines = buildTreeLines(getLeft.apply(root));
                //List<TreeLine> rightTreeLines = buildTreeLines(getRight.apply(root));
                List<TreeLine> leftTreeLines = buildTreeLines(root.getLeft());
                List<TreeLine> rightTreeLines = buildTreeLines(root.getRight());

                int leftCount = leftTreeLines.size();
                int rightCount = rightTreeLines.size();
                int minCount = Math.min(leftCount, rightCount);
                int maxCount = Math.max(leftCount, rightCount);

                // The left and right subtree print representations have jagged edges, and we
                // essentially we have to
                // figure out how close together we can bring the left and right roots so that
                // the edges just meet on
                // some line. Then we add hspace, and round up to next odd number.
                int maxRootSpacing = 0;
                for (int i = 0; i < minCount; i++) {
                    int spacing = leftTreeLines.get(i).rightOffset - rightTreeLines.get(i).leftOffset;
                    if (spacing > maxRootSpacing)
                        maxRootSpacing = spacing;
                }
                int rootSpacing = maxRootSpacing + hspace;
                if (rootSpacing % 2 == 0)
                    rootSpacing++;
                // rootSpacing is now the number of spaces between the roots of the two subtrees

                List<TreeLine> allTreeLines = new ArrayList<>();

                // add the root and the two branches leading to the subtrees

                allTreeLines.add(new TreeLine(rootLabel, -(rootLabel.length() - 1) / 2, rootLabel.length() / 2));

                // also calculate offset adjustments for left and right subtrees
                int leftTreeAdjust = 0;
                int rightTreeAdjust = 0;

                if (leftTreeLines.isEmpty()) {
                    if (!rightTreeLines.isEmpty()) {
                        // there's a right subtree only
                        if (squareBranches) {
                            if (lrAgnostic) {
                                allTreeLines.add(new TreeLine("\u2502", 0, 0));
                            } else {
                                allTreeLines.add(new TreeLine("\u2514\u2510", 0, 1));
                                rightTreeAdjust = 1;
                            }
                        } else {
                            allTreeLines.add(new TreeLine("\\", 1, 1));
                            rightTreeAdjust = 2;
                        }
                    }
                } else if (rightTreeLines.isEmpty()) {
                    // there's a left subtree only
                    if (squareBranches) {
                        if (lrAgnostic) {
                            allTreeLines.add(new TreeLine("\u2502", 0, 0));
                        } else {
                            allTreeLines.add(new TreeLine("\u250C\u2518", -1, 0));
                            leftTreeAdjust = -1;
                        }
                    } else {
                        allTreeLines.add(new TreeLine("/", -1, -1));
                        leftTreeAdjust = -2;
                    }
                } else {
                    // there's a left and right subtree
                    if (squareBranches) {
                        int adjust = (rootSpacing / 2) + 1;
                        String horizontal = String.join("", Collections.nCopies(rootSpacing / 2, "\u2500"));
                        String branch = "\u250C" + horizontal + "\u2534" + horizontal + "\u2510";
                        allTreeLines.add(new TreeLine(branch, -adjust, adjust));
                        rightTreeAdjust = adjust;
                        leftTreeAdjust = -adjust;
                    } else {
                        if (rootSpacing == 1) {
                            allTreeLines.add(new TreeLine("/ \\", -1, 1));
                            rightTreeAdjust = 2;
                            leftTreeAdjust = -2;
                        } else {
                            for (int i = 1; i < rootSpacing; i += 2) {
                                String branches = "/" + spaces(i) + "\\";
                                allTreeLines.add(new TreeLine(branches, -((i + 1) / 2), (i + 1) / 2));
                            }
                            rightTreeAdjust = (rootSpacing / 2) + 1;
                            leftTreeAdjust = -((rootSpacing / 2) + 1);
                        }
                    }
                }

                // now add joined lines of subtrees, with appropriate number of separating
                // spaces, and adjusting offsets

                for (int i = 0; i < maxCount; i++) {
                    TreeLine leftLine, rightLine;
                    if (i >= leftTreeLines.size()) {
                        // nothing remaining on left subtree
                        rightLine = rightTreeLines.get(i);
                        rightLine.leftOffset += rightTreeAdjust;
                        rightLine.rightOffset += rightTreeAdjust;
                        allTreeLines.add(rightLine);
                    } else if (i >= rightTreeLines.size()) {
                        // nothing remaining on right subtree
                        leftLine = leftTreeLines.get(i);
                        leftLine.leftOffset += leftTreeAdjust;
                        leftLine.rightOffset += leftTreeAdjust;
                        allTreeLines.add(leftLine);
                    } else {
                        leftLine = leftTreeLines.get(i);
                        rightLine = rightTreeLines.get(i);
                        int adjustedRootSpacing = (rootSpacing == 1 ? (squareBranches ? 1 : 3) : rootSpacing);
                        TreeLine combined = new TreeLine(
                                leftLine.line + spaces(adjustedRootSpacing - leftLine.rightOffset + rightLine.leftOffset)
                                        + rightLine.line,
                                leftLine.leftOffset + leftTreeAdjust, rightLine.rightOffset + rightTreeAdjust);
                        allTreeLines.add(combined);
                    }
                }
                return allTreeLines;
            }
        }

        private static String spaces(int n) {
            return String.join("", Collections.nCopies(n, " "));
        }
    }

    /* == Instance Variables == */
    private final Random generator;
    private TNode root;
    private final Comparator<K> comparator;
    private int size;

    /* == Constructors == */

    /* Constructs an empty Treap with the given key comparator */
    public Treap(Comparator<K> keyComp) {
        if (keyComp == null) {
            throw new IllegalArgumentException("Key comparator cannot be null");
        }
        root = null;
        generator = new Random(System.nanoTime());
        comparator = keyComp;
        size = 0;
    }

    /* Constructs a Treap with the given RNG seed for priority generation */
    public Treap(Comparator<K> keyComp, long seed) {
        if (keyComp == null) {
            throw new IllegalArgumentException("Key comparator cannot be null");
        }
        root = null;
        generator = new Random(seed);
        comparator = keyComp;
        size = 0;
    }

    /* == Recursive Helpers == */
    private SearchResult searchRecursive(K key, TNode root, TNode previous) {
        // Return null and the theoretical position
        if (root == null) {
            if (previous == null) {
                // Tree is empty, so return an empty result
                return new SearchResult((V) null, null);
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

    /* inOrderEntriesHelper() - recursive helper for inOrderEntries()
     * visits nodes in sorted key order and stores each key-value pair in result
     */
    private void inOrderEntriesHelper(TNode node, List<Map.Entry<K, V>> result) {
        if (node == null) {
            return;
        }

        // visit left subtree first
        inOrderEntriesHelper(node.getLeft(), result);

        // store current node as a key-value entry
        result.add(new AbstractMap.SimpleEntry<>(node.getKey(), node.getValue()));

        // visit right subtree
        inOrderEntriesHelper(node.getRight(), result);
    }


    // inOrderHelper() - recursive helper for inOrder()
    // visits nodes in the order of their keys and stores the keys in a list (result)
    private void inOrderHelper(TNode node, List<K> result) {
        if (node == null)
            return;

        // goes left first (smaller keys)
        inOrderHelper(node.getLeft() , result);

        // store current noes key
        result.add(node.getKey());
        // then goes right (larger keys)
        inOrderHelper(node.getRight(), result);
    }

    public List<K> inOrderKeys() {
        List<K> result = new ArrayList<>();
        inOrderHelper(root, result);
        return result;
    }

    // heightHelper() - recursive helper for height()
    private int heightHelper(TNode node) {
        // empty tree has height -1
        if (node == null) return -1;

        // get height of left and right subtrees
        int leftHeight = heightHelper(node.getLeft());
        int rightHeight = heightHelper(node.getRight());

        // return the bigger side plus 1 for the current node
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /* == Private Functions == */

    // validate() - throws an exception if the key is null
    private void validate(K key){
        if(key == null){
            throw new IllegalArgumentException("Key cannot be null");
        }
    }

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
        TNode pRightChild = p.getRight(); // Set Q's left child to be P's right child
        q.setLeft(pRightChild);
        p.setRight(q); // Set P's right child to be Q

        // Rebuild connection to rest of tree
        if (qParent == null) {
            root = p;
            p.setParent(null);
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
        TNode qLeftChild = q.getLeft(); // Set P's right child to be Q's left child
        p.setRight(qLeftChild);
        q.setLeft(p); // Set Q's left child to be P

        // Rebuild connection to rest of tree
        if (pParent == null) {
            root = q;
            q.setParent(null);
        } else if (pParent.getLeft() == p) {
            pParent.setLeft(q);
        } else {
            pParent.setRight(q);
        }
    }

    /* == Public Functions == */


    // inOrderEntries() - returns a list of key-value pairs in sorted order
    public List<Map.Entry<K, V>> inOrderEntries() {
        List<Map.Entry<K, V>> result = new ArrayList<>();
        inOrderEntriesHelper(root, result);
        return result;
    }


    /* put() inserts a new key-value pair, or updates the value if the key already exists
     * Returns:
     * - null if the key was new
     * - the old value if the key already existed
     */
    public V put(K key, V value) {
        // reject null keys before doing anything else
        validate(key);

        // search for the key first
        SearchResult result = search(key);

        // if the key already exists, update the value and return the old one
        TNode existingNode = result.getNode();
        if (existingNode != null) {
            V oldValue = existingNode.getValue();
            existingNode.setValue(value);
            return oldValue;
        }
        // key does not exist, so create a brand-new node
        TNode node = new TNode();
        TNode parent = result.getParentNode();

        node.setKey(key);
        node.setValue(value);

        // attach the new node in BST position based on the key
        if (parent == null) {
            root = node;   // tree was empty
        } else if (result.isLeftChild()) {
            parent.setLeft(node);
        } else {
            parent.setRight(node);
        }

        // rotate the node upward while heap priority is violated
        while (parent != null && node.getPriority() > parent.getPriority()) {
            if (parent.getLeft() == node) {
                // node is a left child, so rotate right
                rotateRight(parent);
            } else {
                // node is a right child, so rotate left
                rotateLeft(parent);
            }

            // after rotation, check the node's new parent
            parent = node.getParent();
        }

        // a new node was inserted, so size increases
        size++;

        // return null because there was no previous value for this key
        return null;
    }

    /* insert() - compatibility wrapper
      now uses put() internally
     */
    public void insert(K key, V value) {
        put(key, value);
    }

    // get() - returns value if found, null if not
    public V get(K key) {
        validate(key);
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
        return size;
    }

    // inOrder() - prints keys in sorted order (smallest to largest)
    public void inOrder() {
    System.out.println(inOrderKeys());
     }

    // containsKey() - check if key exists
    public boolean containsKey(K key) {
        validate(key);
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
    public V remove(K key) {
        validate(key);
        if (root == null) {
            return null;
        }

        SearchResult result = search(key);
        if (result.getNode() == null) {
            return null;
        }

        TNode node = result.getNode();
        V oldValue = node.getValue();

        while (node.getLeft() != null || node.getRight() != null) {
            if (node.getLeft() == null) {
                rotateLeft(node);
            } else if (node.getRight() == null) {
                rotateRight(node);
            } else if (node.getLeft().getPriority() > node.getRight().getPriority()) {
                rotateRight(node);
            } else {
                rotateLeft(node);
            }
        }

        TNode parent = node.getParent();
        if (parent == null) {
            root = null;
        } else if (parent.getLeft() == node) {
            parent.setLeft(null);
        } else {
            parent.setRight(null);
        }

        size--;
        return oldValue;
    }
    // simple wrapper for compatability for tests etc.
    public void delete(K key){
        remove(key);
    }

    // height() - returns the height of the treap (number of edges from root down to the deepest leaf)
    public int height() {
        return heightHelper(root);
    }

    /* Prints a string representation of the treap, showing each node's key and priority */
    @Override
    public String toString() {
        TreapPrinter printer = new TreapPrinter();
        return printer.print();
    }
}
