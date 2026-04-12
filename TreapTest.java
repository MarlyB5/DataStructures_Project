import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class TreapTest {
    // treap used in all tests, integer keys and string values
    private Treap<Integer, String> treap;

    // runs before each test to start with empty treap
    @BeforeEach
    public void setUp() {
        treap = new Treap<>(Integer::compare);
    }

    // ---- get() tests ----
    @Test
    public void testGetExistingKey() {
        // should return correct value for existing key
        treap.insert(10, "ten");
        assertEquals("ten", treap.get(10));
    }

    @Test
    public void testGetNonExistingKey() {
        // should return null
        treap.insert(10, "ten");
        assertNull(treap.get(99));
    }

    @Test
    public void testGetFromEmptyTreap() {
        //should return null and not crash
        assertNull(treap.get(10));
    }

    @Test
    public void testGetAfterDelete() {
        // should return null after key is deleted
        treap.insert(10, "ten");
        treap.delete(10);
        assertNull(treap.get(10));
    }

    @Test
    public void testGetAfterUpdatingSameKey() {
        // inserting the same key with a new value should overwrite old value
        treap.insert(1, "one");
        treap.insert(1, "ONE");
        assertEquals("ONE", treap.get(1));
    }

    // ---- size() tests ----
    @Test
    public void testSizeAfterInsert() {
        // insert 3 nodes -> size = 3
        treap.insert(1, "one");
        treap.insert(2, "two");
        treap.insert(3, "three");
        assertEquals(3, treap.size());
    }

    @Test
    public void testSizeAfterDelete() {
        // size should decrease by 1
        treap.insert(1, "one");
        treap.insert(2, "two");
        treap.delete(1);
        assertEquals(1, treap.size());
    }

    @Test
    public void testSizeWhenEmpty() {
        // empty treap size should be 0
        assertEquals(0, treap.size());
    }

    @Test
    public void testSizeAfterDuplicate() {
        // inserting same key twice shouldnt increase size
        treap.insert(3, "three");
        treap.insert(3, "THREE");
        assertEquals(1, treap.size());
    }

    // ---- ordering tests ----
    @Test
    public void testOrderingAfterMultipleInserts() {
        // insert in random order, first and last key should still be correct
        treap.insert(3, "three");
        treap.insert(5, "five");
        treap.insert(9, "nine");
        treap.insert(1, "one");
        treap.insert(7, "seven");
        assertEquals(1, treap.firstKey());
        assertEquals(9, treap.lastKey());
    }

    @Test
    public void testOrderingAfterDeletes() {
        // insert a few nodes, delete some, ordering should still hold
        treap.insert(3, "three");
        treap.insert(5, "five");
        treap.insert(9, "nine");
        treap.insert(1, "one");
        treap.insert(7, "seven");
        treap.delete(1);
        treap.delete(9);
        // deleting smallest and largest -> new first = 3, new last = 7
        assertEquals(3, treap.firstKey());
        assertEquals(7, treap.lastKey());
    }

    // ---- delete() tests ----
    @Test
    public void testDeleteOnEmptyTreap() {
        // shouldnt crash when tree is empty
        treap.delete(1);
        assertEquals(0, treap.size());
    }

    @Test
    public void testDeleteOnlyNode() {
        // deleting the only node should make tree empty
        treap.insert(1, "one");
        treap.delete(1);
        assertTrue(treap.isEmpty());
    }

    @Test
    public void testDeleteKeyThatDoesNotExist() {
        // deleting a key that was never inserted should change nothing
        treap.insert(1, "one");
        treap.delete(99);
        assertEquals(1, treap.size());
    }

    @Test
    public void testDeleteNodeWithTwoChildren() {
        // delete a node that has two children, rest of tree should still work
        treap.insert(5, "five");
        treap.insert(3, "three");
        treap.insert(7, "seven");
        treap.delete(5);
        assertNull(treap.get(5));
        assertEquals(2, treap.size());
        // other nodes should still be there
        assertNotNull(treap.get(3));
        assertNotNull(treap.get(7));
    }

    @Test
    public void testDeleteLeafNode() {
        treap.insert(5, "five");
        treap.insert(3, "three");
        treap.delete(3);
        assertNull(treap.get(3));
        assertEquals(1, treap.size());
    }

    @Test
    public void testRemoveReturnsDeletedValue() {
        treap.insert(5, "five");

        assertEquals("five", treap.remove(5));
        assertNull(treap.get(5));
        assertEquals(0, treap.size());
    }

    // ---- height() tests ----
    @Test
    public void testHeightEmptyTreap() {
        // empty treap should have height -1
        assertEquals(-1, treap.height());
    }

    @Test
    public void testHeightIncreasesWithNodes() {
        // adding more nodes should increase height
        treap.insert(5, "five");
        treap.insert(3, "three");
        treap.insert(7, "seven");
        // height should be at least 1
        assertTrue(treap.height() >= 1);
    }

    @Test
    public void testHeightSingleNode() {
        // single node treap should have height 0
        treap.insert(1, "one");
        assertEquals(0, treap.height());
    }

    // ---- containsKey() tests ----
    @Test
    public void testContainsExistingKey() {
        treap.insert(7, "seven");
        assertTrue(treap.containsKey(7));
    }

    @Test
    public void testContainsNonExistingKey() {
        treap.insert(7, "seven");
        assertFalse(treap.containsKey(99));
    }

    @Test
    public void testContainsKeyAfterDelete() {
        // after deleting, should return false for that key
        treap.insert(3, "three");
        treap.delete(3);
        assertFalse(treap.containsKey(3));
    }

    @Test
    public void testContainsKeyOnEmptyTreap() {
        // should return false and not crash
        assertFalse(treap.containsKey(1));
    }

    // ---- isEmpty() tests ----
    @Test
    public void testNewIsEmpty() {
        // new treap should be empty
        assertTrue(treap.isEmpty());
    }

    @Test
    public void testEmptyAfterDeleteAll() {
        // insert then delete, should be empty again
        treap.insert(1, "one");
        treap.delete(1);
        assertTrue(treap.isEmpty());
    }

    @Test
    public void testEmptyAfterInsert() {
        // after inserting, should not be empty
        treap.insert(1, "one");
        assertFalse(treap.isEmpty());
    }

    // ---- firstKey() tests ----
    @Test
    public void testFirstKey() {
        treap.insert(5, "five");
        treap.insert(2, "two");
        treap.insert(8, "eight");
        // smallest key should be 2
        assertEquals(2, treap.firstKey());
    }

    @Test
    public void testFirstKeyOnEmptyTreap() {
        // should return null and not crash
        assertNull(treap.firstKey());
    }

    @Test
    public void testFirstKeyAfterDeleteSmallest() {
        // after deleting smallest, next smallest should be returned
        treap.insert(5, "five");
        treap.insert(2, "two");
        treap.insert(8, "eight");
        treap.delete(2);
        assertEquals(5, treap.firstKey());
    }

    // ---- lastKey() tests ----
    @Test
    public void testLastKey() {
        treap.insert(5, "five");
        treap.insert(2, "two");
        treap.insert(8, "eight");
        // largest key should be 8
        assertEquals(8, treap.lastKey());
    }

    @Test
    public void testInOrderKeysOnEmptyTreap() {
        assertEquals(Collections.emptyList(), treap.inOrderKeys());
    }

    @Test
    public void testLastKeyAfterDeleteLargest() {
        // after deleting largest, second largest should be returned
        treap.insert(5, "five");
        treap.insert(2, "two");
        treap.insert(8, "eight");
        treap.delete(8);
        assertEquals(5, treap.lastKey());
    }

    // ---- inOrder() tests ----
    @Test
    public void testInOrderOnEmptyTreap() {
        // should not crash on empty treap
        treap.inOrder();
    }

    @Test
    public void testInOrderDoesNotCrash() {
        // check inOrder runs without errors
        treap.insert(3, "three");
        treap.insert(1, "one");
        treap.insert(2, "two");
        treap.inOrder(); // should print 1=one 2=two 3=three

        assertEquals(Arrays.asList(1, 2, 3), treap.inOrderKeys());

    }
    @Test
    public void testInOrderEntriesReturnsSortedEntries() {
        treap.insert(3, "three");
        treap.insert(1, "one");
        treap.insert(2, "two");

        List<Map.Entry<Integer, String>> entries = treap.inOrderEntries();

        assertEquals(3, entries.size());

        assertEquals(Integer.valueOf(1), entries.get(0).getKey());
        assertEquals("one", entries.get(0).getValue());

        assertEquals(Integer.valueOf(2), entries.get(1).getKey());
        assertEquals("two", entries.get(1).getValue());

        assertEquals(Integer.valueOf(3), entries.get(2).getKey());
        assertEquals("three", entries.get(2).getValue());
    }

    @Test
    public void testToString() {
        Treap<Integer, String> treap2 = new Treap<>(Integer::compare, 0);
        for (int i = 0; i < 30; i++) {
            treap2.insert(i + 1, "" + (i + 1));
        }
        System.out.println(treap2); // Should print the same tree each time, as we have fixed the seed
        assertEquals(1, treap2.firstKey());
        assertEquals(30, treap2.lastKey());
    }

    // --- null key tests ---
    @Test
    public void testInsertNullKeyThrows() {
        assertThrows(IllegalArgumentException.class, () -> treap.insert(null, "x"));
    }

    @Test
    public void testGetNullKeyThrows() {
        assertThrows(IllegalArgumentException.class, () -> treap.get(null));
    }

    @Test
    public void testContainsNullKeyThrows() {
        assertThrows(IllegalArgumentException.class, () -> treap.containsKey(null));
    }

    @Test
    public void testRemoveNullKeyThrows() {
        assertThrows(IllegalArgumentException.class, () -> treap.remove(null));
    }
}
