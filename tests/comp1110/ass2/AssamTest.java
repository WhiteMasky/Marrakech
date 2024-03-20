package comp1110.ass2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AssamTest {
    @Test
    public void testConstructorWithCoordinatesAndDirections() {
        Assam assam = new Assam(1, 2, "N");
        assertEquals(assam.getAssamString(), "A12N");
    }

    @Test
    public void testConstructorWithStringRepresentation() {
        Assam assam = new Assam("A34S");
        assertEquals(assam.getAssamString(), "A34S");
    }


    @Test
    public void testRotateRight() {
        Assam assam = new Assam(1, 1, "N");
        assam.rotateRight();
        assertEquals(assam.directions, "N");
    }

    @Test
    public void testMoveEast() {
        Assam assam = new Assam(1, 1, "E");
        assam.moveEast(2);
        assertEquals(assam.getAssamCurrentLocation().getX(), 3);
        assertEquals(assam.getAssamCurrentLocation().getY(), 1);
    }


    @Test
    public void testGetAssamCurrentLocation() {
        Assam assam = new Assam(3, 4, "N");
        IntPair location = assam.getAssamCurrentLocation();
        assertEquals(location.getX(), 3);
        assertEquals(location.getY(), 4);
    }

    @Test
    public void testGetDirections() {
        Assam assam = new Assam(1, 1, "E");
        assertEquals(assam.getDirections(), "E");
    }

    @Test
    public void testGetAssamString() {
        Assam assam = new Assam(1, 2, "S");
        assertEquals(assam.getAssamString(), "A12S");
    }
}
