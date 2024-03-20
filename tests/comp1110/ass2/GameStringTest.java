package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GameStringTest {
    /**
     * Check if GameString.getPlayerStrings() is valid
     * Given a gameState String, then use GameString.getPlayerStrings() method to generate Player String. Check the result with the expected result.
     */
    @Test
    public void getPlayerStrings() {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/gameString_to_other3String.txt")));
        Stream<String> testLines = fr.lines();
        for (String line : testLines.toList()) {
            String[] splitLine = line.split("@");
            // Extract player strings and combine them into a single string
            String[] players = GameString.getPlayerStrings(splitLine[0]);
            String allPlayers = String.join("", players);
            // Compare the combined player string with the expected value
            Assertions.assertEquals(splitLine[1], allPlayers, splitLine[4]);
        }
    }
    /**
     * Check if GameString.getAssamStrings() is valid
     * Given a gameState String, then use GameString.getAssamStrings() method to generate Assam String. Check the result with the expected result.
     */
    @Test
    public void getAssamString() {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/gameString_to_other3String.txt")));
        Stream<String> testLines = fr.lines();
        for (String line : testLines.toList()) {
            String[] splitLine = line.split("@");
            // Use the getAssamString method to get the Assam string
            String assamString = GameString.getAssamString(splitLine[0]);
            // Compare the Assam string with the expected value
            Assertions.assertEquals(splitLine[2], assamString, splitLine[5]);
        }
    }

    /**
     * Check if GameString.getBoardStrings() is valid
     * Given a gameState String, then use GameString.getBoardStrings() method to generate Board String. Check the result with the expected result.
     */
    @Test
    public void getBoardString() {
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("testdata/gameString_to_other3String.txt")));
        Stream<String> testLines = fr.lines();
        for (String line : testLines.toList()) {
            String[] splitLine = line.split("@");
            String boardString = GameString.getBoardString(splitLine[0]);
            // For this test, there's one argument needed to the function
            Assertions.assertEquals(splitLine[3], boardString, splitLine[6]);
        }
    }
}