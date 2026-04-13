package Data_Structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeMapTest {

    private AVLTreeMap<Integer, String> avl;

    @BeforeEach
    public void setUp() {
        avl = new AVLTreeMap<>(Integer::compare);
    }


    @Test
    public void testPutAndGet() {
        avl.put(10, "ten");
        assertEquals("ten", avl.get(10));
    }

    @Test
    public void testGetNonExistingKey() {
        avl.put(10, "ten");
        assertNull(avl.get(99));
    }

    @Test
    public void testGetFromEmpty() {
        assertNull(avl.get(5));
    }

    @Test
    public void testPutDuplicateUpdatesValue() {
        avl.put(1, "one");
        avl.put(1, "ONE");

        assertEquals("ONE", avl.get(1));
        assertEquals(1, avl.size()); // size should not increase
    }

    @Test
    public void testSizeAfterInsert() {
        avl.put(1, "one");
        avl.put(2, "two");
        avl.put(3, "three");

        assertEquals(3, avl.size());
    }

    @Test
    public void testSizeAfterRemove() {
        avl.put(1, "one");
        avl.put(2, "two");

        avl.remove(1);

        assertEquals(1, avl.size());
    }

    @Test
    public void testSizeEmpty() {
        assertEquals(0, avl.size());
    }


    @Test
    public void testContainsExistingKey() {
        avl.put(7, "seven");
        assertTrue(avl.containsKey(7));
    }

    @Test
    public void testContainsNonExistingKey() {
        avl.put(7, "seven");
        assertFalse(avl.containsKey(99));
    }

    @Test
    public void testContainsAfterDelete() {
        avl.put(5, "five");
        avl.remove(5);

        assertFalse(avl.containsKey(5));
    }


    @Test
    public void testRemoveExistingKey() {
        avl.put(5, "five");

        String removed = avl.remove(5);

        assertEquals("five", removed);
        assertNull(avl.get(5));
        assertEquals(0, avl.size());
    }

    @Test
    public void testRemoveNonExistingKey() {
        avl.put(5, "five");

        assertNull(avl.remove(99));
        assertEquals(1, avl.size());
    }

    @Test
    public void testRemoveFromEmpty() {
        assertNull(avl.remove(1));
    }

    @Test
    public void testRemoveNodeWithTwoChildren() {
        avl.put(5, "five");
        avl.put(3, "three");
        avl.put(7, "seven");

        avl.remove(5);

        assertNull(avl.get(5));
        assertEquals(2, avl.size());
        assertNotNull(avl.get(3));
        assertNotNull(avl.get(7));
    }

    @Test
    public void testFirstKey() {
        avl.put(5, "five");
        avl.put(2, "two");
        avl.put(8, "eight");

        assertEquals(2, avl.firstKey());
    }

    @Test
    public void testLastKey() {
        avl.put(5, "five");
        avl.put(2, "two");
        avl.put(8, "eight");

        assertEquals(8, avl.lastKey());
    }

    @Test
    public void testFirstKeyEmpty() {
        assertNull(avl.firstKey());
    }

    @Test
    public void testLastKeyEmpty() {
        assertNull(avl.lastKey());
    }

    @Test
    public void testInOrderSorted() {
        avl.put(3, "three");
        avl.put(1, "one");
        avl.put(2, "two");

        List<Integer> keys = avl.inOrderKeys();

        assertEquals(List.of(1, 2, 3), keys);
    }

    @Test
    public void testInOrderAfterDeletes() {
        avl.put(5, "five");
        avl.put(3, "three");
        avl.put(7, "seven");

        avl.remove(5);

        List<Integer> keys = avl.inOrderKeys();

        assertEquals(List.of(3, 7), keys);
    }

    @Test
    public void testHeightEmpty() {
        assertEquals(-1, avl.height());
    }

    @Test
    public void testHeightSingleNode() {
        avl.put(1, "one");
        assertEquals(0, avl.height());
    }

    @Test
    public void testHeightBalanced() {
        avl.put(1, "one");
        avl.put(2, "two");
        avl.put(3, "three");

        assertTrue(avl.height() <= 2); // AVL should stay balanced
    }

    @Test
    public void testIsEmptyInitially() {
        assertTrue(avl.isEmpty());
    }

    @Test
    public void testIsEmptyAfterInsert() {
        avl.put(1, "one");
        assertFalse(avl.isEmpty());
    }

    @Test
    public void testIsEmptyAfterClear() {
        avl.put(1, "one");
        avl.clear();

        assertTrue(avl.isEmpty());
    }

    @Test
    public void testClear() {
        avl.put(1, "one");
        avl.put(2, "two");

        avl.clear();

        assertEquals(0, avl.size());
        assertTrue(avl.isEmpty());
        assertNull(avl.get(1));
    }

    @Test
    public void testPutNullKey() {
        assertThrows(IllegalArgumentException.class, () -> avl.put(null, "x"));
    }

    @Test
    public void testGetNullKey() {
        assertThrows(IllegalArgumentException.class, () -> avl.get(null));
    }

    @Test
    public void testRemoveNullKey() {
        assertThrows(IllegalArgumentException.class, () -> avl.remove(null));
    }
}