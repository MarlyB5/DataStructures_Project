package Data_Structures;

import java.util.*;

public class AVLTreeMap<K, V> {


    private class Node {
        K key;
        V value;
        Node left;
        Node right;
        int height;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 0;
        }
    }

// the instance variables
    private Node root;
    private final Comparator<K> comparator;
    private int size;

 // constructor
    public AVLTreeMap(Comparator<K> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

//validation class
    private void validate(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
    }

    // Height Helper Methods
    private int height(Node node) {
        return node == null ? -1 : node.height;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    // rotation methods
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private Node rebalance(Node node) {
        updateHeight(node);
        int balance = balanceFactor(node);

        // L HEAVY
        if (balance > 1) {
            if (balanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left); // LR case
            }
            return rotateRight(node); // LL case
        }

        // R HEAVY
        if (balance < -1) {
            if (balanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right); // RL case
            }
            return rotateLeft(node); // RR case
        }

        return node;
    }

  // searching methods
    private Node find(Node node, K key) {
        if (node == null) return null;

        int cmp = comparator.compare(key, node.key);

        if (cmp == 0) return node;
        else if (cmp < 0) return find(node.left, key);
        else return find(node.right, key);
    }

    //put methods
    private Node put(Node node, K key, V value, Holder<V> oldValue, Holder<Boolean> inserted) {
        if (node == null) {
            inserted.value = true;
            return new Node(key, value);
        }

        int cmp = comparator.compare(key, node.key);

        if (cmp == 0) {
            oldValue.value = node.value;
            node.value = value;
            return node;
        } else if (cmp < 0) {
            node.left = put(node.left, key, value, oldValue, inserted);
        } else {
            node.right = put(node.right, key, value, oldValue, inserted);
        }

        return rebalance(node);
    }

    public V put(K key, V value) {
        validate(key);

        Holder<V> oldValue = new Holder<>();
        Holder<Boolean> inserted = new Holder<>(false);

        root = put(root, key, value, oldValue, inserted);

        if (inserted.value) {
            size++;
            return null;
        }

        return oldValue.value;
    }

    public void insert(K key, V value) {
        put(key, value);
    }

    // get method
    public V get(K key) {
        validate(key);
        Node node = find(root, key);
        return node == null ? null : node.value;
    }

    public boolean containsKey(K key) {
        validate(key);
        return find(root, key) != null;
    }

    // remove method
    private Node min(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private Node remove(Node node, K key, Holder<V> removed) {
        if (node == null) return null;

        int cmp = comparator.compare(key, node.key);

        if (cmp < 0) {
            node.left = remove(node.left, key, removed);
        } else if (cmp > 0) {
            node.right = remove(node.right, key, removed);
        } else {
            removed.value = node.value;

            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node successor = min(node.right);
            node.key = successor.key;
            node.value = successor.value;

            node.right = remove(node.right, successor.key, new Holder<>());
        }

        return rebalance(node);
    }

    public V remove(K key) {
        validate(key);

        Holder<V> removed = new Holder<>();
        root = remove(root, key, removed);

        if (removed.value != null) {
            size--;
        }

        return removed.value;
    }

    public void delete(K key) {
        remove(key);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public K firstKey() {
        if (root == null) return null;
        Node cur = root;
        while (cur.left != null) cur = cur.left;
        return cur.key;
    }

    public K lastKey() {
        if (root == null) return null;
        Node cur = root;
        while (cur.right != null) cur = cur.right;
        return cur.key;
    }

    //traversals
    private void inOrder(Node node, List<K> result) {
        if (node == null) return;
        inOrder(node.left, result);
        result.add(node.key);
        inOrder(node.right, result);
    }

    public List<K> inOrderKeys() {
        List<K> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    public int height() {
        return height(root);
    }

    private static class Holder<T> {
        T value;

        Holder() {}
        Holder(T value) { this.value = value; }
    }
}