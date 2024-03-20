package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.Grid;
import comp1110.ass2.Marrakech;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

/** Original Version of game gui
 * @Author Yiping Yan
 */
public class Game1 extends Application {
    ComboBox<Integer> numberPlayer = new ComboBox<>(FXCollections.observableArrayList(2,3,4));
    private final Group root = new Group();
    private Group controls=new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    public static String rugString="";
    public String gameString = "";
    public static int playerNumber;
    public static int[] remain_rugs=new int[4];
    public static int current_player=0;
    public static int x1,x2;
    public static int y1,y2;
    public static int check_move=0;
    Text dice;
    public VBox player_box=new VBox();
    int check=0;


    Grid[][] gridArr = new Grid[7][7];
    Text[] playerText;
    Text winText;
    Grid assGrid;




    void displayState(String state) {
        int playerIndex = state.indexOf("P");
        int strPlayerNum = 0;
        while(playerIndex != -1){
            String playerStr = state.substring(playerIndex, playerIndex + 8 );
            String color="";
            if(playerStr.charAt(1) == 'r'){
                color = "red";
            } else if (playerStr.charAt(1) == 'c') {
                color = "cyan";
            } else if (playerStr.charAt(1) == 'y') {
                color = "yellow";
            }else if (playerStr.charAt(1) == 'p') {
                color = "purple";
            }
            String playerInfo = "player" + strPlayerNum
                    +"\n"+ "color: " + color
                    +"\n" + "dirhams: " + playerStr.substring(2,5)
                    +"\n" + "rugs: " + playerStr.substring(5,7)
                    +"\n"+"state: "+ playerStr.charAt(7);
            playerText[strPlayerNum].setText(playerInfo);
            playerIndex = state.indexOf("P",playerIndex + 1);
            strPlayerNum++;
        }
        String assamStr = state.substring(state.indexOf('A'), state.indexOf('A') + 4 );

        int assamX = Integer.parseInt(String.valueOf(assamStr.charAt(1)));
        int assamY = Integer.parseInt(String.valueOf(assamStr.charAt(2)));

        Assam assam = new Assam(assamX, assamY, String.valueOf(assamStr.charAt(3)));
        String assamUrl = "Assam" + assam.directions + ".PNG";
        URL url = getClass().getResource(assamUrl);
        Image image = new Image(url.toString());
        root.getChildren().remove(assGrid);
        assGrid=new Grid(assamX,assamY,80);
        assGrid.setFill(new ImagePattern(image));
        assGrid.setStroke(null);
        root.getChildren().add(assGrid);

        String rugStr = state.substring(state.indexOf("B") + 1);
        for (int i = 0; i < rugStr.length(); i += 3){
            int y=(i/3)%7;
            int x=(i/3)/7;

            if (rugStr.charAt(i)=='y'){
                gridArr[x][y].setFill(Color.YELLOW);
            } else if (rugStr.charAt(i)=='r'){
                gridArr[x][y].setFill(Color.RED);
            } else if (rugStr.charAt(i)=='p'){
                gridArr[x][y].setFill(Color.PURPLE);
            } else if (rugStr.charAt(i)=='c'){
                gridArr[x][y].setFill(Color.CYAN );
            }
        }

        if (Marrakech.getWinner(gameString)!='n'){
            winText.setText("Winner is "+Marrakech.getWinner(gameString));
            winText.setLayoutX(700);
            winText.setLayoutY(20);
        }
    }

    private void makeControls() {

        //加载自定义字体
        Font customFont = Font.loadFont(getClass().getResource("/comp1110/ass2/gui/Skranji-Regular.ttf").toExternalForm(), 18);

        Button startButton = new Button("Start");
        startButton.setFont(customFont);

//        Button restartButton = new Button("Restart");
//        restartButton.setFont(customFont);
        Button rulesButton = new Button("Rules");
        rulesButton.setFont(customFont);
        rulesButton.setOnAction(event -> {
            displayRules();
        });
        controls.getChildren().add(rulesButton);
        rulesButton.setLayoutX(WINDOW_WIDTH - 300);  // 调整这里来改变X轴位置
        rulesButton.setLayoutY(WINDOW_HEIGHT - 60);  // 调整这里来改变Y轴位置


        Button diceButton = new Button("Dice Roller");
        diceButton.setFont(customFont);


        numberPlayer.setPromptText("Number of players");
        // 设置numberPlayer PromptText的字体和大小
        numberPlayer.setStyle("-fx-font-family: 'Skranji'; -fx-font-size: 18;");

        ComboBox<Integer> numberAI = new ComboBox<>(FXCollections.observableArrayList(0,1,2,3));
        numberAI.setPromptText("Number of AI");
        numberAI.setStyle("-fx-font-family: 'Skranji'; -fx-font-size: 18;");


        ComboBox<Integer> AI_Difficulty = new ComboBox<>(FXCollections.observableArrayList(1,2));
        AI_Difficulty.setPromptText("Difficulty of AI");
        AI_Difficulty.setStyle("-fx-font-family: 'Skranji'; -fx-font-size: 18;");

        HBox hb = new HBox();
        hb.getChildren().addAll(startButton, numberPlayer, numberAI,AI_Difficulty);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(WINDOW_HEIGHT - 60);
        controls.getChildren().add(hb);

        diceButton.setLayoutX(WINDOW_WIDTH - 150);
        diceButton.setLayoutY(WINDOW_HEIGHT - 60);
        diceButton.setOnAction(event -> {
            int diceValue=Marrakech.rollDie();
            int place_a=gameString.indexOf('A');
            String assam_s=gameString.substring(place_a,place_a+4);
            String new_assam_s=Marrakech.moveAssam(assam_s,diceValue);
            gameString=gameString.replace(assam_s,new_assam_s);
            dice.setText(String.valueOf(diceValue));
            dice.setStyle("-fx-font-family: 'Skranji'; -fx-font-size: 20;");
            dice.setFill(Color.BLACK);
            dice.setStroke(Color.CORNSILK);
            dice.setStrokeWidth(1.0);

            displayState(gameString);
        });
        controls.getChildren().add(diceButton);

        startButton.setOnAction(event -> {
            if (check==0){
                initialState();
                displayState(gameString);
            }else {
                initialState2();
                displayState(gameString);
            }
            event.consume();
        });
        root.setOnMouseClicked(event -> {
            if (check_move==2){
                System.out.println(gameString);
                System.out.println(rugString);


                gameString=Marrakech.makePlacement(gameString,rugString);
                displayState(gameString);
                System.out.println(gameString);
                System.out.println(rugString);
                check_move=0;
                rugString="";//
            }

        });
//        restartButton.setOnAction(event -> {
//            initialState2();
//            displayState(gameString);
//        });
    }
    private void displayRules() {
        Stage rulesStage = new Stage();
        rulesStage.setTitle("Game Rules");

        Text rulesText = new Text();
        rulesText.setText("Here are the rules of the game:\n\n" +
                "1. Rule one...\n" +
                "2. Rule two...\n" +
                "3. Rule three...\n");  // You can modify this text as per your game rules

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            rulesStage.close();
        });

        VBox vbox = new VBox(rulesText, closeButton);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20));

        Scene rulesScene = new Scene(vbox, 600, 300);  // Adjust the scene dimensions if needed
        rulesStage.setScene(rulesScene);
        rulesStage.show();
    }


    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        stage.setTitle("Marrakech");

        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        URL url = getClass().getResource("./Background2.jpeg");
        Image image = new Image(url.toString());
        scene.setFill(new ImagePattern(image));
        root.getChildren().add(controls);

        makeControls();

        stage.setScene(scene);
        stage.show();
    }
    public void initialState(){
        Font customFont = Font.loadFont(getClass().getResource("/comp1110/ass2/gui/Skranji-Regular.ttf").toExternalForm(), 18);

        dice=new Text();
        dice.setLayoutX(WINDOW_WIDTH - 120);
        dice.setLayoutY(WINDOW_HEIGHT - 80);
        controls.getChildren().add(dice);
        dice.setText("No Dice");
        dice.setStyle("-fx-font-family: 'Skranji'; -fx-font-size: 18;");
        dice.setFill(Color.BLACK);
        dice.setStroke(Color.CORNSILK);
        dice.setStrokeWidth(1.0);


        check+=1;
        String assamStr="A33S";
        VBox player_box=new VBox();
        gameString = "B";
        for (int i = 0; i < 49; i++) {
            gameString = gameString+"n00";
        }

        //Font customFont = Font.loadFont(getClass().getResource("/comp1110/ass2/gui/Skranji-Regular.ttf").toExternalForm(), 18);
        playerNumber=numberPlayer.getValue();
        playerText = new Text[playerNumber];
        for (int i = 0; i < playerNumber; i++){
            playerText[i] = new Text();
            playerText[i].setStyle("-fx-font-family: 'Skranji'; -fx-font-size: 18;");
            playerText[i].setFill(Color.CORNSILK);
            playerText[i].setStroke(Color.BLACK);
            playerText[i].setStrokeWidth(0.7);

            player_box.getChildren().add(playerText[i]);
        }
        player_box.setLayoutX(25);
        player_box.setLayoutY(50);
        player_box.setSpacing(10);
        root.getChildren().add(player_box);

        gameString=assamStr+gameString;
        if (playerNumber==2){
            gameString="Pc00015i"+"Py00015i"+gameString;

        }else if (playerNumber==3){
            gameString="Pc00015i"+"Py00015i"+"Pp00015i"+gameString;

        } else if (playerNumber==4) {
            gameString="Pc00015i"+"Py00015i"+"Pp00015i"+"Pr00015i"+gameString;

        }
        for(int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                Grid grid = new Grid(i,j,80);
                root.getChildren().add(grid);
                gridArr[i][j] = grid;
            }
        }

        String assamUrl = "Assam" + assamStr.charAt(3) + ".PNG";
        URL url = getClass().getResource(assamUrl);
        Image image = new Image(url.toString());
        assGrid= new Grid(3,3,80);
        assGrid.setFill(new ImagePattern(image));
        root.getChildren().add(assGrid);

    }
    public void initialState2(){
        dice.setText("No Dice");
        gameString="";
        String assamStr="A33S";
        gameString = "B";
        for (int i = 0; i < 49; i++) {
            gameString = gameString+"n00";
        }

//        playerText=new Text[playerNumber];
//        root.getChildren().remove(player_box);
        for (int i=0;i<playerNumber;i++){
            playerText[i].setText("");
        }
        playerNumber=numberPlayer.getValue();

//        for (int i=0;i<playerNumber;i++){
//            playerText[i]=new Text();
//            player_box.getChildren().add(playerText[i]);
//        }
//        player_box.setLayoutX(30);
//        player_box.setLayoutY(50);
//        player_box.setSpacing(8);
//        root.getChildren().add(player_box);
        gameString=assamStr+gameString;
        if (playerNumber==2){
            gameString="Pc00015i"+"Py00015i"+gameString;
            remain_rugs[0]=15;
            remain_rugs[1]=15;

        }else if (playerNumber==3){
            gameString="Pc00015i"+"Py00015i"+"Pp00015i"+gameString;
            remain_rugs[0]=15;
            remain_rugs[1]=15;
            remain_rugs[2]=15;

        } else if (playerNumber==4) {
            gameString="Pc00015i"+"Py00015i"+"Pp00015i"+"Pr00015i"+gameString;
            remain_rugs[0]=15;
            remain_rugs[1]=15;
            remain_rugs[2]=15;
            remain_rugs[3]=15;

        }
        for(int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++){
                Grid grid = new Grid(i,j,80);
                root.getChildren().add(grid);
                gridArr[i][j] = grid;
            }
        }

        root.getChildren().remove(assGrid);
        String assamUrl = "Assam" + assamStr.charAt(3) + ".PNG";
        URL url = getClass().getResource(assamUrl);
        Image image = new Image(url.toString());
        assGrid= new Grid(3,3,80);
        assGrid.setFill(new ImagePattern(image));
        root.getChildren().add(assGrid);



    }

}
