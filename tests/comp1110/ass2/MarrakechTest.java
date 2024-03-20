package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarrakechTest {

    @Test
    void makePlacement() {
        assertEquals("6",
                Marrakech.makePlacement("Pc03313iPy02713iPp03013iPr03013iA35WBn00n00c03c03n00n00n00n00n00n00n00n00n00n00y02n00r02r02n00n00n00y02c00n00n00n00n00n00c02c02n00n00n00n00n00y00n00r01r01n00n00n00y00n00n00p00n00n00n00","p080506"),"777");
}

    @Test
    void isRugValid() {
        Rug test1 = new Rug("y012326");
        for(IntPair intPair : test1.placementPosition){
            System.out.println(intPair.getX());
            System.out.println(intPair.getY());
        }
    }
}