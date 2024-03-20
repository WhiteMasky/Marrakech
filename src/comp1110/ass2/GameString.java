package comp1110.ass2;

import java.util.ArrayList;
/**
 * This class provides methods for extracting information from a game string in a board game.
 *
 * @Author Haipeng Yan(u7757634),Yichi Zhang(u7748799)
 */
public class GameString {
    private String currentGame;
    private char[] allPlayersState;

    // Given a full game string, extract the Player Strings

    // c y p r
    /**
     * Given a full game string, extracts the player strings.
     *
     * @param gameStr The full game string.
     * @return An array of player strings.
     * @Author Haipeng Yan
     */
    public static String[] getPlayerStrings(String gameStr) {
        int startIndex = 0;
        ArrayList<String> playerStringsList = new ArrayList<>();

        while (startIndex < gameStr.length() && gameStr.charAt(startIndex) == 'P') {
            playerStringsList.add(gameStr.substring(startIndex, startIndex + 8));
            startIndex += 8;
        }

        //
        String[] playerStrings = new String[playerStringsList.size()];
        playerStrings = playerStringsList.toArray(playerStrings);

        return playerStrings;
    }


    // Given a full game string, extract the Assam String
    /**
     * Given a full game string, extracts the Assam string.
     *
     * @param gameStr The full game string.
     * @return The Assam string.
     * @Author Haipeng Yan
     */
    public static String getAssamString(String gameStr) {
        int assamIndex = gameStr.indexOf("A");
        if (assamIndex != -1) {
            return gameStr.substring(assamIndex, assamIndex + 4);
        }
        return null;
    }


    /**
     * Given a full game string, extracts the board string.
     *
     * @param gameStr The full game string.
     * @return The board string.
     * @Author Haipeng Yan
     */
    public static String getBoardString(String gameStr) {
        int boardIndex = gameStr.indexOf("B");
        if (boardIndex != -1) {
            return gameStr.substring(boardIndex);
        }
        return null;
    }

    //get the players String part
    /**
     * Constructs a GameString object with the given game string.
     *
     * @param currentGame The current game string.
     * @Author Yichi Zhang
     */
    public GameString(String currentGame) {
        this.currentGame = currentGame;
        this.allPlayersState = currentGame.substring(0, 32).toCharArray();
    }

    //count all the players in the game
    /**
     * Counts the number of players in the game.
     *
     * @return The number of players in the game.
     * @Author Yichi Zhang
     */
    public int countPlayers() {
        int playersAmount = 0;
        for (int i = 7; i < 32; i += 8) {
            if (allPlayersState[i] == 'i') {
                playersAmount++;
            }
        }
        return playersAmount;
    }

    //count the number of players who is in the game and have no rugs in the game
    /**
     * Counts the number of players in the game who have no rugs.
     *
     * @return The number of players with no rugs in the game.
     * @Author Yichi Zhang
     */
    public int countPlayersWithNoRugs() {
        int cnt = 0;
        for (int i = 7; i < 32; i += 8) {
            if (allPlayersState[i] == 'i' && allPlayersState[i - 1] == '0' && allPlayersState[i - 2] == '0') {
                cnt++;
            }
        }
        return cnt;
    }
    /**
     * Modifies the game string by replacing the board string with the given boardString.
     *
     * @param gameString  The original game string.
     * @param boardString The new board string.
     * @return The modified game string.
     */
    public static String modifyGameStringFromBoardString(String gameString, String boardString) {
        // Extract the part of the gameString before the board string (i.e., player strings and Assam string)
        String playerStringAndAssamString = gameString.substring(0, gameString.indexOf("B"));

        // Concatenate the extracted part with the new boardString to form the new gameString
        String newGameString = playerStringAndAssamString + boardString;

        return newGameString;
    }

}

