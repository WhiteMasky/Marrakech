package comp1110.ass2;

import java.util.*;
/**
 * This class represents a game board for a board game.
 * It stores the grid, rugs on the board, and provides various methods for manipulating the board.
 *
 * @Author Haipeng Yan(u7757634)
 */
public class Board {
    public HashMap<String, List<Rug>> rugsOnBoard;
    public Grid[][] grid;
    public final int gridSize = 7;
    /**
     * Constructor for the Board class.
     * Initializes a new instance of the Board class.
     * It creates a grid for the board, sets the grid size, and initializes the rugsOnBoard HashMap.
     */
    public Board() {
        grid = new Grid[gridSize][gridSize];
        rugsOnBoard = new HashMap<>();
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                grid[x][y] = new Grid(x, y, 92);
                grid[x][y].hasRug = false;
            }
        }
    }

    /**
     * Constructor for the Board class.
     * Initializes a new instance of the Board class based on a provided boardString.
     * It constructs the board and places rugs on the grid based on the information in the provided string.
     *
     * @param boardString A string representing the state of the board with rug abbreviations.
     * @Author Haipeng Yan
     */
    public Board(String boardString) {
        if(boardString.startsWith("B")) {
            boardString = boardString.substring(1);  // skip the initial 'B'
        }

        grid = new Grid[gridSize][gridSize];
        rugsOnBoard = new HashMap<>();  // Initialize the HashMap

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                grid[x][y] = new Grid();

                // Extract the current rugAbbreviatedString
                String rugAbbreviatedString = boardString.substring(0, 3);

                // If the rug is not a null rug (n00), then place it on the Grid
                if (!rugAbbreviatedString.equals("n00")) {
                    // Create a new Rug using the rugString
                    Rug rug = new Rug(rugAbbreviatedString);
                    grid[x][y].setPosition(new IntPair(x,y));
                    grid[x][y].setTopRug(rug);
                    grid[x][y].setHasRug(true);

                    // Update the HashMap
                    rugsOnBoard
                            .computeIfAbsent(rug.getColor(), k -> new ArrayList<>())
                            .add(rug);

                } else {
                    grid[x][y].setHasRug(false);
                }

                // Move to next 3-character sequence
                boardString = boardString.substring(3);
            }
        }
    }
    /**
     * Depth-First Search (DFS) helper method.
     * Performs a depth-first search starting from the currentGrid to count the number of connected grids with the same targetColor.
     *
     * @param currentGrid    The current grid to start the DFS from.
     * @param targetColor    The color of the rugs to count.
     * @param countedIntPair A set of IntPair objects to keep track of the grids that have already been counted.
     * @return The number of connected grids with the same targetColor.
     * @Author Haipeng Yan
     */
    private int dfs(Grid currentGrid, String targetColor, Set<IntPair> countedIntPair) {
        if (currentGrid == null || !currentGrid.getHasRug() || countedIntPair.contains(currentGrid.position) || !currentGrid.getTopRug().getColor().equals(targetColor)) {
            return 0;
        }

        countedIntPair.add(currentGrid.position);
        int count = 1;

        count += dfs(getUpGrid(currentGrid), targetColor, countedIntPair);
        count += dfs(getRightGrid(currentGrid), targetColor, countedIntPair);
        count += dfs(getDownGrid(currentGrid), targetColor, countedIntPair);
        count += dfs(getLeftGrid(currentGrid), targetColor, countedIntPair);

        return count;
    }

    /**
     * Gets the number of connected grids with the same color as the startGrid.
     *
     * @param startGrid The starting grid to count the connected grids from.
     * @return The number of connected grids with the same color as the startGrid.
     * @Author Haipeng Yan
     */
    public int getConnectedGridAmount(Grid startGrid) {
        if (!startGrid.getHasRug()) {
            return 0;
        }

        Set<IntPair> countedIntPair = new HashSet<>();
        String targetColor = startGrid.getTopRug().getColor();
        return dfs(startGrid, targetColor, countedIntPair);  // Corrected the order of parameters
    }

    /**
     * Gets the grid above the currentGrid. and the following are analogical
     *
     * @param currentGrid The current grid.
     * @return The grid above the currentGrid, or null if it's on the top edge.
     * @Author Haipeng Yan
     */
    public Grid getUpGrid(Grid currentGrid) {
        int x = currentGrid.position.getX();
        int y = currentGrid.position.getY();

        if (y == 6) return null;  // If on the top edge, return null

        return grid[x][y+1];
    }


    /**
     * Give a grid, return the up gird of this grid
     * @param currentGrid the original grid
     * @return Grid is located at the up of the original gird
     * @Author Haipeng Yan
     */
    public Grid getRightGrid(Grid currentGrid) {
        int x = currentGrid.position.getX();
        int y = currentGrid.position.getY();

        if (x == 6) return null;  // If on the right edge, return null

        return grid[x+1][y];
    }


    /**
     * Give a grid, return the bottom gird of this grid
     * @param currentGrid the original grid
     * @return Grid is located at the bottom of the original gird
     * @Author Haipeng Yan
     */
    public Grid getDownGrid(Grid currentGrid) {
        int x = currentGrid.position.getX();
        int y = currentGrid.position.getY();

        if (y == 0) return null;  // If on the bottom edge, return null

        return grid[x][y-1];
    }

    /**
     * Give a grid, return the left gird of this grid
     * @param currentGrid the original grid
     * @return Grid is located at the left of the original gird
     * @Author Haipeng Yan
     */
    public Grid getLeftGrid(Grid currentGrid) {
        int x = currentGrid.position.getX();
        int y = currentGrid.position.getY();

        if (x == 0) return null;  // If on the left edge, return null

        return grid[x-1][y];
    }

    /**
     * get a board string
     * @return a board string
     * @Author Haipeng Yan
     */
    public String toBoardString() {
        StringBuilder boardString = new StringBuilder("B");  // Start with the character 'B'

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (grid[x][y].getHasRug()) {
                    boardString.append(grid[x][y].getTopRug().rugAbbreviatedString());
                } else {
                    boardString.append("n00");
                }
            }
        }

        return boardString.toString();
    }

    //Check if there is an indenticalrug on the board. If exsit, return true.
    /**
     * Checks if there is an identical rug on the board.
     *
     * @param rug The rug to check for.
     * @return True if an identical rug exists on the board, false otherwise.
     * @Author Haipeng Yan
     */
//    public boolean checkIdenticalRug(Rug rug) {
//        List<Rug> sameColorRugs = rugsOnBoard.get(rug.getColor());
//        if (sameColorRugs == null) {
//            return false;
//        }
//        for (Rug r : sameColorRugs) {
//            if (r.getId()==rug.getId()) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean checkIdenticalRug(Rug rug) {
        return rugsOnBoard.getOrDefault(rug.getColor(), Collections.emptyList())
                .stream()
                .anyMatch(r -> r.getId() == rug.getId());
    }

}
