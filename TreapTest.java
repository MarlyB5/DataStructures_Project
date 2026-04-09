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

    // get() tests
    @Test
    public void testGetExistingKey() {
        // should return correct value for exisitng key
        treap.insert(10, "ten");
        assertEquals("ten", treap.get(10));
    }

    @Test
    public void testGetNonExistingKey() {
        // should return null
        treap.insert(10, "ten");
        assertNull(treap.get(99));
    }
}
