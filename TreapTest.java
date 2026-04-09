import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

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
}
