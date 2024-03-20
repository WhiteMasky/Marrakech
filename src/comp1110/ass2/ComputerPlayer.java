package comp1110.ass2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** The ComputerPlayer class try to provide method for building a normal computer player for task 16
 * @author Haipeng Yan
 */
public class ComputerPlayer extends Player {

    private static final Random RANDOM = new Random();

    public ComputerPlayer(String color, int dirhams, int remainingRugs, char state, boolean isAi) {
        this.color=color;
        this.dirhams=dirhams;
        this.remainingRugs=remainingRugs;
        this.state=state;
        this.isAi=true;
    }
    /**
     * Chooses a random direction for Assam.
     *
     * @return An integer representing the chosen direction in degrees.
     * @author Haipeng Yan
     */
    public int chooseRandomDirectionForAssam() {
        int[] directions = {0, 90, 270};
        return directions[RANDOM.nextInt(directions.length)];
    }

    /**
     * Places a rug on the game board for the computer player.
     * <p>
     * This method determines the optimal rug placement based on the current game state and the position of Assam.
     * It considers all possible rug positions around Assam and checks if they are within the bounds of the game board.
     * The method then validates the potential rug placements and selects a valid placement at random.
     * </p>
     *
     * @param gameState The current state of the game represented as a string.
     * @return A Rug object representing the chosen rug placement for the computer player.
     * @throws IndexOutOfBoundsException If there are no valid rug placements found.
     * @author Haipeng Yan
     */
    public Rug placeRugForComputer(String gameState) {
        // Get the location of assam
        Assam testAssam = new Assam(GameString.getAssamString(gameState));
        Board testBoard = new Board(GameString.getBoardString(gameState));
        IntPair assamPosition = testAssam.getAssamCurrentLocation();

        // Get all possible rug around Assam
        IntPair[][] possibleRugPositions = {
                {new IntPair(1, 0), new IntPair(2, 0)},  // Vertically downwards
                {new IntPair(-1, 0), new IntPair(-2, 0)},  // Vertically upwards
                {new IntPair(0, 1), new IntPair(0, 2)},  // Horizontally to the right
                {new IntPair(0, -1), new IntPair(0, -2)},  // Horizontally to the left
                {new IntPair(1, 0), new IntPair(1, 1)},  // Downwards to the right
                {new IntPair(1, 0), new IntPair(1, -1)},  // Downwards to the left
                {new IntPair(-1, 0), new IntPair(-1, 1)},  // Upwards to the right
                {new IntPair(-1, 0), new IntPair(-1, -1)},  // Upwards to the left
                {new IntPair(0, 1), new IntPair(1, 1)},  // Vertically downwards, starting from the right
                {new IntPair(0, 1), new IntPair(-1, 1)},  // Vertically upwards, starting from the right
                {new IntPair(0, -1), new IntPair(1, -1)},  // Vertically downwards, starting from the left
                {new IntPair(0, -1), new IntPair(-1, -1)}  // Vertically upwards, starting from the left
        };


        List<Rug> validRugPlacements = new ArrayList<>();
        for (IntPair[] dir : possibleRugPositions) {
            int firstX = assamPosition.getX() + dir[0].getX();
            int firstY = assamPosition.getY() + dir[0].getY();
            int secondX = assamPosition.getX() + dir[1].getX();
            int secondY = assamPosition.getY() + dir[1].getY();

            // Print the calculated coordinates for debugging
            System.out.println("Checking coordinates: [" + firstX + ", " + firstY + "] and [" + secondX + ", " + secondY + "]");

            // Check if the new coordinates are within the bounds of the board
            if (firstX < 0 || firstX >= 7 || firstY < 0 || firstY >= 7 ||
                    secondX < 0 || secondX >= 7 || secondY < 0 || secondY >= 7) {
                System.out.println("Skipped due to out of bounds.");
                continue;  // Skip this direction if any of the coordinates are out of bounds
            }

            // Create the new rug with the updated coordinates
            IntPair[] newRugCoords = {new IntPair(firstX, firstY), new IntPair(secondX, secondY)};
            Rug possibleRug = new Rug(this.color, this.remainingRugs + 16, newRugCoords);
            System.out.println(possibleRug.rugString());
            // Check if the rug placement is valid
            if (Marrakech.isPlacementValid(gameState, possibleRug.rugString())) {
                validRugPlacements.add(possibleRug);
            } else {
                System.out.println("Rug placement at [" + firstX + ", " + firstY + "] and [" + secondX + ", " + secondY + "] is invalid.");
            }
        }
        for(Rug rug1 : validRugPlacements){
            System.out.println("Valid rug placement: " + rug1.rugString());
        }
        if (validRugPlacements.isEmpty()) {
            System.out.println("No valid rug placements found!");
        }
        return validRugPlacements.get(RANDOM.nextInt(validRugPlacements.size()));
    }

}
