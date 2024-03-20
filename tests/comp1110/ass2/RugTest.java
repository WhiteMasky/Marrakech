package comp1110.ass2;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**@Author Yichi Zhang
 */
public class RugTest {

    private Rug rug;

    public void setUp() {
        rug = new Rug("c", 0, new IntPair[]{new IntPair(0, 0), new IntPair(1, 1)});
    }

    @Test
    public void testIsValidRugString() {
        assertTrue(Rug.isValidRugString("c000011"));
        assertTrue(Rug.isValidRugString("y010120"));
        assertFalse(Rug.isValidRugString("x000011")); // Invalid color
        assertFalse(Rug.isValidRugString("c000077")); // Out of board bounds
        assertFalse(Rug.isValidRugString("c1234567")); // Out of board bounds
    }

    @Test
    public void testRugStringConstructor() {
        Rug rugFromString = new Rug("c000011");
        assertEquals("c", rugFromString.color);
        assertEquals(0, rugFromString.id);
        assertNotNull(rugFromString.placementPosition);
        assertEquals(2, rugFromString.placementPosition.length);
        assertEquals(new IntPair(0, 0), rugFromString.placementPosition[0]);
        assertEquals(new IntPair(1, 1), rugFromString.placementPosition[1]);
    }

}
