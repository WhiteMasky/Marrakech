package comp1110.ass2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * @Author Yichi Zhang, Yiping Yan
 */
public class PlayerTest {

    private Player player;

    public void setUp() {
        player = new Player("c", 30, 15, 'i',false);
    }

    @Test
    public void testCreatePlayersFromStrings() {
        String[] playerStrings = {"Pc001150i", "Py010501o"};
        ArrayList<Player> players = Player.createPlayersFromStrings(playerStrings);

        assertEquals(2, players.size());
        assertEquals("c", players.get(0).color);
        assertEquals(1, players.get(0).dirhams);
        assertEquals(15, players.get(0).remainingRugs);
        assertEquals('0', players.get(0).state);

        assertEquals("y", players.get(1).color);
        assertEquals(10, players.get(1).dirhams);
        assertEquals(50, players.get(1).remainingRugs);
        assertEquals('1', players.get(1).state);
    }

}
