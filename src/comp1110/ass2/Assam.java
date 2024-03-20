package comp1110.ass2;

/**
 * This class represents an Assam, which is a game piece in a board game.
 * It stores the position, directions, and other attributes of the Assam.
 * And we got rotate and move methods that Assam object can implement
 *
 * @Author Yichi Zhang(u7748799), Haipeng Yan(u7757634)
 */

public class Assam {
    public IntPair position;
    public String directions;

    private boolean isOnTrack;
    private int moveSpace;
    private char facing;//Assam的朝向
    /**
     * Constructs an Assam object with the specified position and directions.
     *
     * @param x          The x-coordinate of the position.
     * @param y          The y-coordinate of the position.
     * @param directions The directions of the Assam.
     */
    public Assam (int x, int y, String directions){
        position = new IntPair(x,y);
        this.directions = directions;
    }
    /**
     * Constructs an Assam object from the given string representation.
     *
     * @param AssamState The string representation of the Assam.
     * @throws IllegalArgumentException if the AssamState is invalid.
     */
    public Assam(String AssamState) {
        // Check if the string starts with 'A' and is of length 4
        if (AssamState == null || AssamState.length() != 4 || AssamState.charAt(0) != 'A') {
            throw new IllegalArgumentException("Invalid AssamState");
        }

        // Extract x and y coordinates
        int x = Character.getNumericValue(AssamState.charAt(1));
        int y = Character.getNumericValue(AssamState.charAt(2));
        position = new IntPair(x, y);

        // Determine the facing direction using a switch statement
        directions = String.valueOf(AssamState.charAt(3));

    }

    public Assam(char facing) {//一个构造器，只含有assam的朝向
        this.facing = facing;
    }

    //turn assam left
    public void rotateLeft() {
        switch (facing) {
            case 'N':
                facing = 'W';
                break;
            case 'E':
                facing = 'N';
                break;
            case 'S':
                facing = 'E';
                break;
            case 'W':
                facing = 'S';
                break;
        }
    }
    //turn assam right
    public void rotateRight() {
        switch (facing) {
            case 'N':
                facing = 'E';
                break;
            case 'E':
                facing = 'S';
                break;
            case 'S':
                facing = 'W';
                break;
            case 'W':
                facing = 'N';
                break;
        }
    }
    /**
     * Moves the Assam object north by the specified die result.
     * There are some analogical methods follow.
     *
     * @param dieResult The result of the die roll.
     */
    public void moveNorth(int dieResult) {
        if (position.y - dieResult >= 0){
            position.y -= dieResult;
        }else{
            if (position.x == 6){

                position.x = 6 - (dieResult - position.y - 1);
                position.y = 0;
                directions = "W";
            }else if (position.x % 2 == 1){
                position.y = -(position.y - dieResult + 1);
                position.x -= 1;
                directions = "S";
            }else {
                position.y = -(position.y - dieResult + 1);
                position.x += 1;

                directions = "S";
            }
        }
    }

    public void moveSouth(int dieResult) {
        if (position.y + dieResult <= 6){
            position.y += dieResult;
        }else{
            if (position.x == 0){
                position.x = dieResult - (6 - position.y) - 1;
                position.y = 6;
                directions = "E";
            }else if (position.x % 2 == 1){
                position.y = 6 - (dieResult - (6 - position.y) - 1);
                position.x += 1;
                directions = "N";
            }else {
                position.y = 6 - (dieResult - (6 - position.y) - 1);
                position.x -= 1;
                directions = "N";
            }
        }
    }

    public void moveEast(int dieResult) {
        if (position.x + dieResult <= 6){
            position.x += dieResult;
        }else{
            if (position.y == 0){
                position.y = dieResult - (6 - position.x) - 1;
                position.x = 6;
                directions = "S";
            }else if (position.y % 2 == 1){
                position.x = 6 - (dieResult - (6 - position.x) - 1);
                position.y += 1;
                directions = "W";
            }else {
                position.x = 6 - (dieResult - (6 - position.x) - 1);
                position.y -= 1;
                directions = "W";
            }
        }
    }

    public void moveWest(int dieResult) {
        if (position.x - dieResult >= 0){
            position.x -= dieResult;
        }else{
            if (position.y == 6){
                position.y = 6 - (dieResult - position.x - 1) ;
                position.x = 0;
                directions = "N";
            }else if (position.y % 2 == 1){
                position.x = dieResult - position.x - 1;
                position.y -= 1;
                directions = "E";
            }else {
                position.x = dieResult - position.x - 1;
                position.y += 1;
                directions = "E";
            }
        }
    }


    public char getFacing() {//用来返回facing的
        return facing;
    }

    public  IntPair getAssamCurrentLocation() {
        return position;
    }

    public  String getDirections() {
        return directions;
    }


    public int getMoveSpace() {
        return moveSpace;
    }

    public boolean isOnTrack() {
        return isOnTrack;
    }


    public String getAssamString() {
        return "A"+ this.position.getX()+this.position.getY()+directions;
    }
}
