package comp1110.ass2;



import comp1110.ass2.gui.Game;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import java.net.URL;

/**
 * This class represents a grid in the game.
 * It extends the Polygon class and contains methods for setting and getting the position,
 * setting and getting the top rug, updating the color based on the rug,
 * and setting whether the grid has a rug or not.
 *
 * @Author Haipeng Yan(u7757634)
 */
public class Grid extends Polygon {


    public IntPair position;
    public int initial_x=350;
    public int initial_y=50;
    double sideLength;
    public Rug topRug;
    public Boolean hasRug;

    //This constructor initializes a grid with the given position (x, y) and side length.
    public Grid(int x, int y, double sideLength){
        this.sideLength=sideLength;
        position = new IntPair(x,y);
        getPoints().addAll(
                initial_x+sideLength*x, initial_y+sideLength*y,
                initial_x+sideLength*x, initial_y+sideLength*y+sideLength,
                initial_x+sideLength*x+sideLength, initial_y+sideLength*y+sideLength,
                initial_x+sideLength*x+sideLength, initial_y+y*sideLength
        );
//        URL url = getClass().getResource("./GridImage.jpg");
//        Image image = new Image(url.toString());
        Image image = new Image("comp1110/ass2/GridImage.jpg");
        this.setFill(new ImagePattern(image));
        this.setStroke(Color.BLACK);

    }

    public Grid(){

    }

    public Grid(Rug rug){
        topRug = rug;
        hasRug = true;

    }


    public Rug getTopRug() {
        return topRug;
    }

    public void updateColorBasedOnRug() {
        if (hasRug && topRug != null) {
            switch (topRug.getColor()) {
                case "c":
                    this.setFill(Color.CYAN);
                    break;
                case "y":
                    this.setFill(Color.YELLOW);
                    break;
                case "r":
                    this.setFill(Color.RED);
                    break;
                case "p":
                    this.setFill(Color.PURPLE);
                    break;
                default:
//                    URL gridUrl = getClass().getResource("./GridImage.jpg");
//                    Image gridImage = new Image(gridUrl.toString());
                    Image gridImage = new Image("comp1110/ass2/GridImage.jpg");
                    this.setFill(new ImagePattern(gridImage));
                    break;
            }
        } else {
//            URL gridUrl = getClass().getResource("./GridImage.jpg");
//            Image gridImage = new Image(gridUrl.toString());
            Image gridImage = new Image("comp1110/ass2/GridImage.jpg");
            this.setFill(new ImagePattern(gridImage));
        }
    }
    public void setTopRug(Rug topRug) {
        this.topRug = topRug;
        this.hasRug = true;
        updateColorBasedOnRug();
    }

    public Boolean getHasRug() {
        return hasRug;
    }

    public void setHasRug(Boolean hasRug) {
        this.hasRug = hasRug;
    }
    public IntPair getPosition() {
        return position;
    }

    public void setPosition(IntPair position) {
        this.position = position;
    }
}
