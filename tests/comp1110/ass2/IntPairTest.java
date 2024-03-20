package comp1110.ass2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntPairTest {

    @Test
    public void testGetX() {
        IntPair pair = new IntPair(3, 4);
        assertEquals(3, pair.getX());
    }

    @Test
    public void testGetY() {
        IntPair pair = new IntPair(3, 4);
        assertEquals(4, pair.getY());
    }

    @Test
    public void testAdd() {
        IntPair pair1 = new IntPair(2, 3);
        IntPair pair2 = new IntPair(4, 5);
        IntPair result = pair1.add(pair2);
        assertEquals(6, result.getX());
        assertEquals(8, result.getY());
    }

    @Test
    public void testEquals() {
        IntPair pair1 = new IntPair(2, 3);
        IntPair pair2 = new IntPair(2, 3);
        IntPair pair3 = new IntPair(4, 5);
        assertTrue(pair1.equals(pair2));
        assertFalse(pair1.equals(pair3));
    }

    @Test
    public void testHashCode() {
        IntPair pair1 = new IntPair(2, 3);
        IntPair pair2 = new IntPair(2, 3);
        IntPair pair3 = new IntPair(4, 5);
        assertEquals(pair1.hashCode(), pair2.hashCode());
        assertNotEquals(pair1.hashCode(), pair3.hashCode());
    }

    @Test
    public void testIsAdjacent() {
        IntPair pair1 = new IntPair(2, 3);
        IntPair pair2 = new IntPair(2, 4);
        IntPair pair3 = new IntPair(4, 3);
        assertTrue(pair1.isAdjacent(pair2));
        assertFalse(pair1.isAdjacent(pair3));
        assertFalse(pair2.isAdjacent(pair3));
    }

    @Test
    public void testWithinBoard() {
        IntPair insideBoard = new IntPair(3, 4);
        IntPair outsideBoard1 = new IntPair(-1, 4);
        IntPair outsideBoard2 = new IntPair(7, 4);
        IntPair outsideBoard3 = new IntPair(3, -1);
        IntPair outsideBoard4 = new IntPair(3, 7);

        assertTrue(IntPair.withinBoard(insideBoard));
        assertFalse(IntPair.withinBoard(outsideBoard1));
        assertFalse(IntPair.withinBoard(outsideBoard2));
        assertFalse(IntPair.withinBoard(outsideBoard3));
        assertFalse(IntPair.withinBoard(outsideBoard4));
    }
}
