package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.jar.Manifest;

import javafx.scene.text.Font;
import javafx.util.Duration;

import static comp1110.ass2.Marrakech.isPlacementValid;


/**
 * @Author Haipeng Yan, Yiping Yan, Yichi Zhang
 */
public class Game extends Application {

    private final BorderPane root = new BorderPane();

    private static final int WINDOW_WIDTH = 1200;//900;
    private static final int WINDOW_HEIGHT =700; //900;//660

    public String gameString = "";
    public static int playerNumber;


    private int currentPlayerIndex = -1;

    Font customFont = Font.loadFont(getClass().getResource("/comp1110/ass2/gui/Skranji-Regular.ttf").toExternalForm(), 18);
    Font customFont1 = Font.loadFont(getClass().getResource("/comp1110/ass2/gui/Skranji-Regular.ttf").toExternalForm(), 44);
    GridPane gameGridPane;
    Grid assGrid;
    Stage stage;
    Scene startScene;
    ArrayList<Player> players;
    static Assam assam;
    static Board board;

    private boolean hasRolledDice = false;
    private boolean hasAdjustedDirection = false;

    private Button rollDieButton;
    private Button rotateLeftButton;
    private Button rotateRightButton;
    private Grid selectedGrid = null;
    //Viewer
    URL audioUrl = null;
    Media backgroundMusic = null;
    MediaPlayer musicPlayer = null;
    VBox rightPanel = new VBox();
    Button confirmDirectionButton = new Button("Confirm Direction");

    private static final Image AI_ICON_1 = new Image("comp1110/ass2/gui/Robot.png");
    private static final Image AI_ICON_2 = new Image("comp1110/ass2/gui/Player.png");
    private static final Image DIRHAMS_IMAGE = new Image("comp1110/ass2/gui/Icon_Small_CoinDollar.png");
    private static final Image RUGS_IMAGE = new Image("comp1110/ass2/gui/Icon_BackPack.png");
    private static final Image SCORE_IMAGE = new Image("comp1110/ass2/gui/Icon_Large_Star.png");
    private static final Image STATE_ICON_1 = new Image("comp1110/ass2/gui/Icon_Large_HeartFull_SeethroughOutline.png");
    private static final Image STATE_ICON_2 = new Image("comp1110/ass2/gui/Icon_Large_HeartEmpty_SeethroughOutline.png");
    Label winnerLabel;

    {
        try {
            audioUrl = getClass().getResource("./Marrakech-Markets.wav");
            backgroundMusic = new Media(audioUrl.toURI().toString());
            musicPlayer = new MediaPlayer(backgroundMusic);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creat a start scene with background picture, video, and button. From the event, initialize the player, board and assam
     * @return a start scene
     * @throws IOException
     * @Author Haipeng Yan, Yiping Yan, Yichi Zhang
     */
    public Scene createStartScene() throws IOException {
        // Use StackPane as main layout
        StackPane mainLayout = new StackPane();

        // Load background video
        URL videoUrl = getClass().getResource("./BackgroundVideo.mp4");
        Media media = new Media(videoUrl.toString());
//        URL url = new URL("jar:file:/students/u7748799/IdeaProjects/comp1110-ass2/src/comp1110/ass2/gui/BackgroundVideo.mp4!/");
//        JarURLConnection jarConnection = (JarURLConnection)url.openConnection();
//        Manifest manifest = jarConnection.getManifest();
//        Media media = new Media("comp1110/ass2/gui/BackgroundVideo.mp4");
//        Media media = new Media("file://./src/comp1110/ass2/gui/BackgroundVideo.mp4");
//        File file = new File("src/comp1110/ass2/gui/BackgroundVideo.mp4");
//        Media media = new Media(file.toURI().toURL().toString());
//        Media media = new Media(videoUrl.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true); // Set video to play automatically
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set video loop playback
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(WINDOW_WIDTH);
        mediaView.setFitHeight(WINDOW_HEIGHT);
        mediaPlayer.setOnError(() -> {
            System.err.println("Media player error occurred: " + mediaPlayer.getError());
        });

        // add MediaView to StackPane
        mainLayout.getChildren().add(mediaView);

        // Load your background image (you need to provide the image file)
        Image backgroundImage = new Image("comp1110/ass2/gui/Background2.jpeg");

        // Create an ImageView to display the background image
        ImageView backgroundImageView = new ImageView(backgroundImage);
        // Create a StackPane layout to hold the background image
//        StackPane layout = new StackPane();
        backgroundImageView.setFitWidth(WINDOW_WIDTH);  // Set image width to match window width
        backgroundImageView.setFitHeight(WINDOW_HEIGHT);

        // Add backgroundImageView to mainLayout
//        mainLayout.getChildren().add(backgroundImageView);

        VBox layout = new VBox(20); // Use VBox layout
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));  // Add some padding to keep the element some distance from the edge
        layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))); // Set transparent background

        //show background
//        layout.getChildren().add(backgroundImageView);

        // set title
        Text title = new Text("Welcome to Marrakech!");
        title.setFont(customFont1);
        title.setFill(Color.WHITE);

        // set stroke
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(0.8);


        // Create player number ComboBox
        ComboBox<Integer> playerNumberChoice = new ComboBox<>(FXCollections.observableArrayList(2, 3, 4));
        playerNumberChoice.setPromptText("Select Player Number");

        // Create AI number ComboBox
        ComboBox<Integer> aiNumberChoice = new ComboBox<>();
        aiNumberChoice.setPromptText("Select AI Number");

        // Create AI difficulty ComboBox
        ComboBox<String> aiDifficultyChoice = new ComboBox<>();
        aiDifficultyChoice.getItems().addAll("Normal AI");
        aiDifficultyChoice.setPromptText("Select AI Difficulty");

        String comboBoxStyle = "-fx-background-color: #fffcf2; " +
                "-fx-font-size: 18px; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 15px; " +
                "-fx-border-radius: 15px; " +
                "-fx-padding: 10px; " +
                "-fx-focus-color: transparent; " +
                "-fx-faint-focus-color: transparent;";

        playerNumberChoice.setStyle(comboBoxStyle);
        aiNumberChoice.setStyle(comboBoxStyle);
        aiDifficultyChoice.setStyle(comboBoxStyle);

// Use Platform.runLater to ensure UI components are loaded
        Platform.runLater(() -> {
            String whiteTextColor = "-fx-text-fill: white;";
            playerNumberChoice.lookup(".list-cell").setStyle(whiteTextColor);
            aiNumberChoice.lookup(".list-cell").setStyle(whiteTextColor);
            aiDifficultyChoice.lookup(".list-cell").setStyle(whiteTextColor);


        });


        // When the value of the player number selection box changes, update the optional value of the AI number selection box
        playerNumberChoice.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                aiNumberChoice.getItems().clear();
                int maxAINumber = 4 - newVal;
                for (int i = 0; i <= maxAINumber; i++) {
                    aiNumberChoice.getItems().add(i);
                }
            }
        });

        // Create a start button
        Button startGameButton = new Button("Start Game");



// Set the button's background color to orange, text color to white, rounded corners, and some other styles
        String buttonStyle = "-fx-background-color: #fffcf2; " +
                "-fx-text-fill: black; " +
                "-fx-font-size: 20px; " +
                "-fx-font-weight: bold; " +


                "-fx-background-radius: 15px; " +
                "-fx-border-radius: 15px; " +
                "-fx-padding: 10px 20px;";
        startGameButton.setStyle(buttonStyle);

        startGameButton.setStyle(buttonStyle);

        startGameButton.setOnAction(event -> {
            assam = new Assam(3,3,"N");
            board = new Board();
            if (playerNumberChoice.getValue() != null && aiNumberChoice.getValue() != null) {
                playerNumber = playerNumberChoice.getValue();
                int aiNumber = aiNumberChoice.getValue();

                // Create player and AI lists
                players = new ArrayList<>();

                // Define color order
                String[] colors = {"c", "y", "r", "p"};
                ArrayList<String> availableColors = new ArrayList<>(Arrays.asList(colors));

                // Create object for player
                for (int i = 0; i < playerNumber; i++) {
                    Player player = new Player(availableColors.get(i), 30, 15, 'i',false);
                    players.add(player);
                }

                // Remove player's color from available colors
                for (int i = 0; i < playerNumber; i++) {
                    availableColors.remove(0);
                }

                // Create object for  AI
                for (int i = 0; i < aiNumber; i++) {
                    if (aiDifficultyChoice.getValue().equals("Normal AI")) {
                        Player ai = new ComputerPlayer(availableColors.get(i), 30, 15, 'i',true); // Assuming you have a class named ComputerPlayer for normal AI
                        players.add(ai);
                    } else if (aiDifficultyChoice.getValue().equals("Intelligent AI")) {
                        Player ai = new IntelligentComputerPlayer(availableColors.get(i), 30, 15, 'i',true);
                        players.add(ai);
                    }
                }

                // update GameString
                gameString = updateGameString();
                // Call createMainGameScene() to set the components of the main Scene
                createMainGameScene();

                // Create a new Scene using root and set it as the current scene
                Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
                stage.setScene(mainScene);
            } else {
                // Show warning if number of players or AI is not selected
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select both player and AI numbers.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        layout.getChildren().addAll(title, playerNumberChoice, aiNumberChoice, aiDifficultyChoice, startGameButton);

        //layout.getChildren().add(backgroundImageView);

        mainLayout.getChildren().add(layout);

        return new Scene(mainLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    /**
     * @Author Yiping Yan, Haipeng Yan
     */
    void displayState() {
        // Update Assam's position and direction
        Image image = new Image("comp1110/ass2/gui/Assam" + assam.directions + ".png");

        // Remove the previous assGrid from the gameGridPane
        if (assGrid != null) {
            gameGridPane.getChildren().remove(assGrid);
        }

        // Create a new assGrid for Assam's current position and add it to the gameGridPane
        assGrid = new Grid(assam.getAssamCurrentLocation().getX(), assam.getAssamCurrentLocation().getY(), 92);
        assGrid.setFill(new ImagePattern(image));
        assGrid.setStroke(null);
        gameGridPane.add(assGrid, assam.getAssamCurrentLocation().getX(), assam.getAssamCurrentLocation().getY());

        // Update each grid's color based on the rug
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                Grid currentGrid = board.grid[x][y];
                if (currentGrid.getHasRug()) {
                    Rug rug = currentGrid.getTopRug();
                    switch (rug.getColor()) {
                        case "c":
                            currentGrid.setFill(Color.rgb(56, 232, 251));
                            break;
                        case "y":
                            currentGrid.setFill(Color.rgb(255, 154, 35));
                            break;
                        case "r":
                            currentGrid.setFill(Color.rgb(250, 35, 62));
                            break;
                        case "p":
                            currentGrid.setFill(Color.rgb(85, 50, 133));
                            break;
                        default:
                            Image gridImage = new Image("comp1110/ass2/gui/GridImage.jpg");
                            currentGrid.setFill(new ImagePattern(gridImage));
                            break;
                    }
                } else {
                    Image gridImage = new Image("comp1110/ass2/gui/GridImage.jpg");
                    currentGrid.setFill(new ImagePattern(gridImage));
                }
            }
        }

    }

    /**
     * Create the Main Game Scene, containing many element like gameboard, playerInfoPanel, contral Panel
     * @Author Haipeng Yan
     */
    private void createMainGameScene() {
        // Create a game board
        VBox gameBoard = createGameBoard();
        root.setLeft(gameBoard); // Set the game board to the left

        // Create a player information panel
        VBox playerInfoPanel = createPlayerInfoPanel();

        // Create a control panel
        HBox controlPanel = createControlPanel();


        // Add new "Confirm Direction" button in createControlPanel() method
        confirmDirectionButton.setFont(customFont);
        confirmDirectionButton.setDisable(true); // Disable this button initially
        // Set the click event of the "Confirm Direction" button
        confirmDirectionButton.setOnAction(event -> {
            rotateLeftButton.setDisable(true);
            rotateRightButton.setDisable(true);
            rollDieButton.setDisable(false); // Enable "Roll Die and move" button
            confirmDirectionButton.setDisable(true);
            updateGameString();
        });

        // rollDieButton
        rollDieButton = new Button("Roll Die and move");
        rollDieButton.setFont(customFont);
        rollDieButton.setDisable(true);
        rollDieButton.setOnAction(event -> {
            if (!hasRolledDice) {
                int diceValue = Marrakech.rollDie();
                Assam testassam = new Assam(Marrakech.moveAssam(assam.getAssamString(), diceValue));
                assam.position = testassam.position;

                hasRolledDice = true;
                rollDieButton.setDisable(true);
                rotateLeftButton.setDisable(false);
                rotateRightButton.setDisable(false);
                hasAdjustedDirection = false;

                // Enable all the grids for clicking
                for (int x = 0; x < 7; x++) {
                    for (int y = 0; y < 7; y++) {
                        board.grid[x][y].setDisable(false);
                    }
                }

                updateGameString();
                // Pay dirhams
                int payAmount = Marrakech.getPaymentAmount(gameString);
//                for(Player player:players){
////                    System.out.println(player.color + "dirhams" + player.dirhams );
//                }
                players.get(currentPlayerIndex).dirhams -= payAmount;
                if (players.get(currentPlayerIndex).dirhams < 0) {
                    players.get(currentPlayerIndex).dirhams = 0;
                    players.get(currentPlayerIndex).state = 'o';
                }
//                System.out.println("payment should be xx:"+ payAmount);
//                for(Player player:players){
//                    System.out.println("Before"+player.color + "dirhams" + player.dirhams );
//                }
                Grid assamOn = board.grid[assam.getAssamCurrentLocation().getX()][assam.getAssamCurrentLocation().getY()];

                if (assamOn.hasRug) {
                    String color = assamOn.getTopRug().color;
                    switch (color) {
                        case "c":
                            players.get(0).dirhams += payAmount;
                            break;
                        case "y":
                            players.get(1).dirhams += payAmount;
                            break;
                        case "r":
                            players.get(2).dirhams += payAmount;
                            break;
                        case "p":
                            players.get(3).dirhams += payAmount;
                            break;
                    }
                }


                // Update players' dirhams and state
                for (Player player : players) {
                    if (player.dirhams <= 0) {
                        player.dirhams = 0;
                        player.state = 'o';
                    }
                    updatePlayerLabel(player);
                }

                if(!players.get(currentPlayerIndex).isPlayerInGame()){
                    for (int i =0; i<7;i++){
                        for (int j = 0; j<7; j++){
                            if(board.grid[i][j].getHasRug()){
                                if(board.grid[i][j].topRug.color.equals(players.get(currentPlayerIndex).color)){
                                    board.grid[i][j].topRug =null;
                                    board.grid[i][j].hasRug = false;

                                }
                            }

                        }
                    }
                }
                updateGameString();
                for (Player player : players) {
                    if (player.dirhams <= 0) {
                        player.dirhams = 0;
                        player.state = 'o';
                    }
                    updatePlayerLabel(player);
                }
                displayState();

                if(players.get(currentPlayerIndex).state == 'o') {
                    startNewTurn();
                    return;
                }
                // Update player info panel
                VBox newPlayerInfoPanel = createPlayerInfoPanel();
                rightPanel.getChildren().set(0, newPlayerInfoPanel);

                // Update game string
                updateGameString();

                // Update the display state once at the end
                displayState();

                // Show the alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select two grids to place a rug.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // Create the control panel below
        HBox controlPanelAtBottom = createControlPanelAtBottom();

        // Create a new VBox to overlay the control panel and player information panel
        rightPanel.getChildren().addAll(playerInfoPanel, controlPanel,confirmDirectionButton,rollDieButton,controlPanelAtBottom);
        rightPanel.setTranslateX(-100);
        root.setRight(rightPanel); // Set the new VBox to the right
        gameString = updateGameString();
        displayState();
        startNewTurn();  // Now, the game will start with player c
    }

    /**
     * Creates and returns a VBox containing the game board represented as a GridPane.
     * <p>
     * This method initializes a 7x7 game grid, where each cell is represented by a 'Grid' object.
     * If a grid cell is not already initialized in the board, it creates a new 'Grid' object.
     * Each grid cell is initially disabled for clicking. A mouse click event is set for each grid cell,
     * which is handled by the 'handleGridClick' method, but only if the dice have been rolled.
     * </p>
     *
     * @return A VBox containing the GridPane representing the game board.
     * @Author Haipeng Yan
     */
    private VBox createGameBoard() {
        VBox gameBoard = new VBox();
        gameGridPane = new GridPane();

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                Grid currentGrid = board.grid[x][y];
                if (currentGrid == null) {
                    currentGrid = new Grid(x, y, 80);
                    board.grid[x][y] = currentGrid;
                }

                // Initially, disable the grid clicks
                currentGrid.setDisable(true);

                // Set mouse click event for each Grid
                currentGrid.setOnMouseClicked(event -> {
                    if (hasRolledDice) { // Ensure that the grid can only be clicked if dice is rolled
                        handleGridClick((Grid) event.getSource());
                    }
                });

                gameGridPane.add(currentGrid, x, y);
            }
        }

        gameBoard.getChildren().add(gameGridPane);
        return gameBoard;
    }
    /**
     * Handles the logic when a grid on the game board is clicked.
     * <p>
     * This method manages the behavior of grid selection, rug generation, and placement.
     * On the first click, it selects and marks the grid. On the second click on a different grid,
     * it tries to generate and place a rug between the previously selected grid and the currently clicked grid.
     * If the rug placement is valid, it updates the game board and the player's remaining rugs.
     * If the rug placement is not valid, it displays an error message prompting the player to select two grids again.
     * If the player clicks on the same grid during the second click, it simply resets the selected grid.
     * </p>
     *
     * @param clickedGrid The Grid object that was clicked.
     * @Author Haipeng Yan
     */
    private void handleGridClick(Grid clickedGrid) {
        if (selectedGrid == null) {
            // First click: Select the Grid and mark it as selected
            selectedGrid = clickedGrid;
            clickedGrid.setStroke(Color.RED);
        } else {
            if (selectedGrid != clickedGrid) { // Make sure you are not clicking on the same Grid
                // Second click: Try to generate and place the Rug
                Rug generatedRug = generateRugString(selectedGrid, clickedGrid);
                if (isPlacementValid(gameString, generatedRug.rugString())) {
                    board.grid[selectedGrid.position.x][selectedGrid.position.y].setTopRug(generatedRug);
                    board.grid[clickedGrid.position.x][clickedGrid.position.y].setTopRug(generatedRug);
                    players.get(currentPlayerIndex).remainingRugs-=1;
                    updatePlayerLabel(players.get(currentPlayerIndex));
                    VBox newPlayerInfoPanel = createPlayerInfoPanel();
                    rightPanel.getChildren().set(0, newPlayerInfoPanel);
                    updateGameString();
                    displayState();
                    startNewTurn();
                } else {
                    // If the Rug placement fails, display a message and ask the player to choose again.
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid rug placement! Please select two grids again.", ButtonType.OK);
                    alert.showAndWait();
                }

                // Before resetting the selectedGrid, restore its original borders
                selectedGrid.setStroke(Color.BLACK); //
                clickedGrid.setStroke(Color.BLACK); //

                // Reset selected Grid
                selectedGrid = null;
                for (Player player : players) {
                    if (player.dirhams <= 0) {
                        player.dirhams = 0;
                        player.state = 'o';
                    }
                    updatePlayerLabel(player);
                }

                // Update player info panel
                VBox newPlayerInfoPanel = createPlayerInfoPanel();
                rightPanel.getChildren().set(0, newPlayerInfoPanel);
            } else {
                // If the player clicks on the same tile, just reset the selected tile and restore the original borders
                selectedGrid.setStroke(Color.BLACK);
                selectedGrid = null;
            }
        }
    }

    /**
     * Generates a Rug object based on the selected grids and the current player's color.
     * <p>
     * This method determines the color of the rug based on the current player's index.
     * It calculates the rug ID using the current player's remaining rug count added to a base value of 16.
     * The rug is then initialized using the provided grids' positions and the determined color.
     * </p>
     *
     * @param grid1 The first selected Grid object.
     * @param grid2 The second selected Grid object.
     * @return A Rug object representing the generated rug based on the selected grids and current player's color.
     * @throws IllegalArgumentException if an invalid player number is encountered.
     * @Author Haipeng Yan
     */
    private Rug generateRugString(Grid grid1, Grid grid2) {
        // Generate carpet color based on current player's color
        String color;
        switch (currentPlayerIndex) {
            case 0:
                color = "c";
                break;
            case 1:
                color = "y";
                break;
            case 2:
                color = "r";
                break;
            case 3:
                color = "p";
                break;
            default:
                throw new IllegalArgumentException("Invalid player number");
        }

        // Calculate the rug ID using the current player's remaining rug count + 16
        int id = players.get(currentPlayerIndex).remainingRugs + 16;
        IntPair[] rugPositions = new IntPair[2];
        rugPositions[0] = grid1.position;
        rugPositions[1] = grid2.position;

        // generate carpet
        Rug rugSelected = new Rug(color,id,rugPositions);
        return rugSelected;
    }

    /**
     * Creates and returns a VBox containing information panels for all players.
     * <p>
     * This method initializes a vertical box (VBox) as the main panel to hold individual player information.
     * It then iterates through each player, updates their respective labels using the `updatePlayerLabel` method,
     * and adds the updated labels to the main panel.
     * </p>
     *
     * @return A VBox containing individual information panels for all players.
     * @Author Haipeng Yan
     */
    private VBox createPlayerInfoPanel() {
        VBox playerInfoPanel = new VBox(10);
        playerInfoPanel.setPadding(new Insets(10));

        for (Player player : players) {
            VBox playerBox = updatePlayerLabel(player);
            playerInfoPanel.getChildren().add(playerBox);
        }

        return playerInfoPanel;
    }


    /**
     * Creates and updates a player's information panel.
     * <p>
     * This method initializes a vertical box (VBox) to hold and display the player's information.
     * The player's color is set as the background of the panel. The method then creates and
     * positions various labels and icons within the panel to visually represent the player's
     * name, AI status, dirhams, remaining rugs, score, and game state.
     * </p>
     *
     * @param player The Player object whose information needs to be displayed.
     * @return A VBox containing the updated information panel for the specified player.
     * @Author Haipeng Yan
     */
    private VBox updatePlayerLabel(Player player) {
        VBox playerBox = new VBox(10);
        playerBox.setPadding(new Insets(10));

        // Set player color as background
        Color playerColor = mapCharToColor(player.color);
        playerBox.setBackground(new Background(new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY)));

        // Create playerBox label and center it
        Label playerName = new Label("Player: " + player.color);
        playerName.setTextFill(Color.WHITE);
        playerName.setFont(Font.font("System", FontWeight.BOLD, 24));
        playerName.setAlignment(Pos.CENTER);

        // Set AI icon
        ImageView aiIcon = player.isAI() ? new ImageView(AI_ICON_1) : new ImageView(AI_ICON_2);
        aiIcon.setFitWidth(30);
        aiIcon.setFitHeight(30);
        HBox aiBox = new HBox(5, aiIcon);
        aiBox.setAlignment(Pos.CENTER_LEFT);
        HBox playerNameBox = new HBox(5, aiBox, playerName);

        // Set dirhams icons and labels
        ImageView dirhamsIcon = new ImageView(DIRHAMS_IMAGE);
        dirhamsIcon.setFitWidth(30);
        dirhamsIcon.setFitHeight(30);
        Label dirhamsLabel = new Label(String.valueOf(player.dirhams));
        dirhamsLabel.setTextFill(Color.WHITE);
        dirhamsLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        HBox dirhamsBox = new HBox(5, dirhamsIcon, dirhamsLabel);
        dirhamsBox.setAlignment(Pos.CENTER_LEFT);

        // Set remainingRugs icon and label
        ImageView rugsIcon = new ImageView(RUGS_IMAGE);
        rugsIcon.setFitWidth(30);
        rugsIcon.setFitHeight(30);
        Label rugsLabel = new Label(String.valueOf(player.remainingRugs));
        rugsLabel.setTextFill(Color.WHITE);
        rugsLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        HBox rugsBox = new HBox(5, rugsIcon, rugsLabel);
        rugsBox.setAlignment(Pos.CENTER_LEFT);

        // Set score icons and labels
        ImageView scoreIcon = new ImageView(SCORE_IMAGE);
        scoreIcon.setFitWidth(30);
        scoreIcon.setFitHeight(30);
        int score = Player.getPlayerScore(gameString, player);
        Label scoreLabel = new Label(String.valueOf(score));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        HBox scoreBox = new HBox(5, scoreIcon, scoreLabel);
        scoreBox.setAlignment(Pos.CENTER_LEFT);

        // Set player status icon
        ImageView stateIcon = player.state == 'i' ? new ImageView(STATE_ICON_1) : new ImageView(STATE_ICON_2);
        stateIcon.setFitWidth(30);
        stateIcon.setFitHeight(30);
        HBox stateBox = new HBox(5, stateIcon);
        stateBox.setAlignment(Pos.CENTER_LEFT);

        HBox iconbox = new HBox(10, dirhamsBox, rugsBox, scoreBox, stateBox);

        playerBox.getChildren().addAll(playerNameBox, iconbox);

        return playerBox;
    }

    /**
     * Initiates the logic for starting a new turn in the game.
     * <p>
     * This method handles the progression of game turns, ensuring the game switches to the next player
     * and updating the game state accordingly. It performs several checks:
     * 1. Determines if the game has ended.
     * 2. Updates the status of a player with zero dirhams to 'out'.
     * 3. Skips players who are out of the game or have no remaining rugs.
     * 4. Executes the turn for computer players, if applicable.
     * 5. Displays a prompt for human players indicating the start of their turn and awaiting their actions.
     * </p>
     * @Author Haipeng Yan
     */

    private void startNewTurn() {
        switchToNextPlayer();
        updateGameString();
        checkGameOver();  // First check if the game is over
        // Get current player
        Player currentPlayer = players.get(currentPlayerIndex);


        // If the player's dirhams is 0, set their status to 'o'
        if (currentPlayer.dirhams == 0) {
            currentPlayer.state = 'o';
            updateGameString();
        }



        // Update player info panel
        VBox newPlayerInfoPanel = createPlayerInfoPanel();
        rightPanel.getChildren().set(0, newPlayerInfoPanel);
        updateGameString();
        // Check if player is out or has no carpet
        while (currentPlayer.remainingRugs == 0 || !currentPlayer.isPlayerInGame()) {
            // Switch to next player
            switchToNextPlayer();
            currentPlayer = players.get(currentPlayerIndex);

            // Check dirhams for new players and update status
            if (currentPlayer.dirhams == 0) {
                currentPlayer.state = 'o';
                updateGameString();
            }
        }

        // Check if the current player is a computer player
        if (currentPlayer instanceof ComputerPlayer) {
            executeComputerTurn();
            return;  // If it is a computer player, directly execute the computer player's turn
        }

        // Show prompt message
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Now is " + currentPlayer.color + " 's turn! Please change assam's direction!",
                ButtonType.OK);
        alert.showAndWait();

        // reset button state
        rollDieButton.setDisable(true);
        rotateLeftButton.setDisable(false);
        rotateRightButton.setDisable(false);
        hasAdjustedDirection = false; //Reset direction selection for new turn
        hasRolledDice = false;  //Reset the dice rolling state for the new round
        updateGameString();
        displayState();

    }

    /**
     * Helper method that change the current player index to next player. If the index exceeds the size of players, roll back to 0
     * @Author Haipeng Yan
     */
    public void switchToNextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex > players.size() - 1) {
            currentPlayerIndex = 0; // If the end of the player list has been reached, reset to 0
        }
    }

    /**
     * Executes the logic for a computer player's turn in the game.
     * <p>
     * This method automates the actions for a computer player, following these steps:
     * 1. Checks the computer player's dirhams and remaining rugs.
     * 2. Randomly chooses a direction for the Assam piece and updates its position.
     * 3. Rolls the dice and moves the Assam piece accordingly.
     * 4. Places a rug on the board, if the placement is valid.
     * 5. Calculates and pays the dirhams based on the Assam's current position.
     * 6. Updates all player labels and the game state.
     * 7. Checks if the next player is a computer. If so, it triggers a pause before executing the next computer's turn.
     *    If the next player is a human player, the turn ends and control is passed to the human player.
     * </p>
     * @Author Haipeng Yan
     */
    private void executeComputerTurn() {
        if(players.get(currentPlayerIndex).dirhams==0){
            players.get(currentPlayerIndex).state='o';
            updateGameString();
        }
        if(players.get(currentPlayerIndex).remainingRugs==0||!players.get(currentPlayerIndex).isPlayerInGame()){
            return;
        }
        // 1. Choose a direction for Assam
        int direction = ((ComputerPlayer) players.get(currentPlayerIndex)).chooseRandomDirectionForAssam();
        Assam testassam = new Assam(Marrakech.rotateAssam(GameString.getAssamString(gameString), direction));
        System.out.println(testassam.directions);
        assam.directions=testassam.directions;
        updateGameString();
        displayState();
        // 2. Roll the dice and move Assam
        int diceValue = Marrakech.rollDie();
        Assam testassam1 = new Assam(Marrakech.moveAssam(assam.getAssamString(), diceValue));
        assam.position = testassam1.position;
        updateGameString();
        displayState();
        // 3. place rug
        Rug rugToPlace = ((ComputerPlayer) players.get(currentPlayerIndex)).placeRugForComputer(gameString);
        if(Marrakech.isPlacementValid(gameString,rugToPlace.rugString()))
        board.grid[rugToPlace.getPlacementPosition()[0].getX()][rugToPlace.getPlacementPosition()[0].getY()].setTopRug(rugToPlace);
        board.grid[rugToPlace.getPlacementPosition()[1].getX()][rugToPlace.getPlacementPosition()[1].getY()].setTopRug(rugToPlace);
        updateGameString();
        players.get(currentPlayerIndex).remainingRugs-=1;
        for (int i = 0; i < players.size(); i++) {
            updatePlayerLabel(players.get(i));
            VBox newPlayerInfoPanel = createPlayerInfoPanel(); //Create a new player information panel
            rightPanel.getChildren().set(0, newPlayerInfoPanel);
        }
        // 4. pay dirhams
        int payAmount = Marrakech.getPaymentAmount(gameString);
        players.get(currentPlayerIndex).dirhams -= payAmount;
        if (players.get(currentPlayerIndex).dirhams < 0) {
            players.get(currentPlayerIndex).dirhams = 0;
            players.get(currentPlayerIndex).state = 'o';
        }
        Grid assamOn = board.grid[assam.getAssamCurrentLocation().getX()][assam.getAssamCurrentLocation().getY()];
        if(assamOn.hasRug) {
            String color = assamOn.getTopRug().color;
            switch (color) {
                case "c":
                    players.get(0).dirhams += payAmount;
                    if(players.get(0).dirhams < 0) { players.get(0).dirhams = 0; players.get(0).state = 'o'; }
                    break;
                case "y":
                    players.get(1).dirhams += payAmount;
                    if(players.get(1).dirhams < 0) { players.get(1).dirhams = 0; players.get(1).state = 'o'; }
                    break;
                case "r":
                    players.get(2).dirhams += payAmount;
                    if(players.get(2).dirhams < 0) { players.get(2).dirhams = 0; players.get(2).state = 'o'; }
                    break;
                case "p":
                    players.get(3).dirhams += payAmount;
                    if(players.get(3).dirhams < 0) { players.get(3).dirhams = 0; players.get(3).state = 'o'; }
                    break;
            }
        }
        for (int i = 0; i < players.size(); i++) {
            updatePlayerLabel(players.get(i));
            VBox newPlayerInfoPanel = createPlayerInfoPanel();  //Create a new player information panel
            rightPanel.getChildren().set(0, newPlayerInfoPanel);
        }
        updateGameString();
        // 5. Switch to next player's turn
        displayState();
        updateGameString();
        if (players.get((currentPlayerIndex + 1) % players.size()) instanceof ComputerPlayer) {
            // If the next player is also a computer player, start PauseTransition
            PauseTransition pause = new PauseTransition(Duration.seconds(1));  // 1 second delay
            pause.setOnFinished(e -> {
                Platform.runLater(() -> startNewTurn());  // Use Platform.runLater() to execute startNewTurn() asynchronously
            });
            pause.play();
            return;
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(1));  // 1 second delay
        startNewTurn();
    }

    /**
     * Creates and returns a horizontal box (HBox) containing control buttons for the game.
     * <p>
     * This method constructs a control panel that provides the player with buttons to
     * rotate the Assam piece either to the left or to the right. The actions associated
     * with each button handle the rotation logic, update the game state, and render the
     * updated state on the screen. After a rotation is made, the "Confirm Direction" button
     * is enabled, allowing the player to finalize the chosen direction.
     * </p>
     *
     * @return HBox containing the "Rotate Left" and "Rotate Right" buttons.
     * @Author Haipeng Yan
     */
    private HBox createControlPanel() {
        HBox controlPanel = new HBox();

        rotateLeftButton = new Button("Rotate Left");
        rotateLeftButton.setFont(customFont);

        rotateRightButton = new Button("Rotate Right");
        rotateRightButton.setFont(customFont);

        rotateLeftButton.setOnAction(event -> {
            if (!hasAdjustedDirection) {
                Assam testassam = new Assam(Marrakech.rotateAssam(GameString.getAssamString(gameString), 270));
                assam.directions=testassam.directions;
                updateGameString();
                displayState();
                hasAdjustedDirection = true;
                confirmDirectionButton.setDisable(false); // Enable "Confirm Direction" button
            }
            updateGameString();
        });

        rotateRightButton.setOnAction(event -> {
            if (!hasAdjustedDirection) {
                Assam testassam = new Assam(Marrakech.rotateAssam(GameString.getAssamString(gameString), 90));
                assam.directions=testassam.directions;
                updateGameString();
                displayState();
                hasAdjustedDirection = true;
                confirmDirectionButton.setDisable(false); // Enable the "Confirm Direction" button
            }
            updateGameString();
        });

        controlPanel.getChildren().addAll(rotateLeftButton, rotateRightButton);

        return controlPanel;
    }

    /**
     * Constructs and returns a horizontal box (HBox) containing various control elements for the game interface.
     * <p>
     * This method constructs a control panel positioned at the bottom of the game interface. The control panel includes:
     * <ul>
     *     <li>A "Rules" button represented by an icon that, when clicked, displays the game rules.</li>
     *     <li>A volume control icon that toggles the visibility of a volume slider. The slider allows the user to adjust the game's volume level. An icon change indicates whether the sound is muted or active based on the slider's position.</li>
     * </ul>
     * The control elements are spaced evenly and padded for a better visual appearance.
     * </p>
     *
     * @return HBox containing the "Rules" icon, volume control icon, and volume slider.
     * @Author Haipeng Yan
     */
    private HBox createControlPanelAtBottom() {
        HBox controlPanel = new HBox(10);
        controlPanel.setPadding(new Insets(10));

        // Create a "Rules" button and set its click event
        Image rulesImage = new Image("comp1110/ass2/gui/Icon_Small_Blank_Help.png");
        ImageView rulesImageView = new ImageView(rulesImage);
        rulesImageView.setFitWidth(30);
        rulesImageView.setFitHeight(30);
        rulesImageView.setOnMouseClicked(event -> {
            displayRules();
        });
        rulesImageView.setTranslateX(70);
        rulesImageView.setTranslateY(20);

        //Add volume slider
        Slider volumeSlider = new Slider(0, 1, 0.5); //The default volume is 50%
        volumeSlider.valueProperty().bindBidirectional(musicPlayer.volumeProperty());
        // Create volume and mute icons
        Image volumeImage = new Image("comp1110/ass2/gui/Icon_Large_Audio_Grey.png");
        Image muteImage = new Image("comp1110/ass2/gui/Icon_Large_AudioOff_Grey.png");
        ImageView volumeIcon = new ImageView(volumeImage);
        //Set image width and height
        volumeIcon.setFitWidth(30);
        volumeIcon.setFitHeight(30);
        volumeIcon.setTranslateX(85);
        volumeIcon.setTranslateY(20);
        volumeSlider.setVisible(false);
//Add click event
        volumeIcon.setOnMouseClicked(event -> {
            volumeSlider.setVisible(!volumeSlider.isVisible()); // Toggle slider visibility
        });

// Listen for slider value changes
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() == 0) {
                volumeIcon.setImage(muteImage); //
            } else {
                volumeIcon.setImage(volumeImage); //
            }
        });

        //Add the new "Confirm Direction" button, volume slider and other controls to the controlPanel
        controlPanel.getChildren().addAll(rulesImageView, volumeIcon,volumeSlider);

        return controlPanel;
    }

    /**
     * Constructs and returns a scene representing the end of the game, announcing the winner.
     * <p>
     * This method creates a visual scene to display the winner of the game, using a specified player color.
     * The scene comprises:
     * <ul>
     *     <li>A background image to enhance the visual appeal.</li>
     *     <li>A label indicating which player (by color) is the winner.</li>
     *     <li>A button that allows the user to return to the main menu.</li>
     * </ul>
     * The elements in the scene are organized in a vertical box (VBox) layout, centered and spaced for a better visual presentation.
     * </p>
     * Note: There is an issue with the media player for the win sound which needs to be addressed separately.
     *
     * @param winnerColor The color of the player who won the game.
     * @return Scene containing the winner announcement and a button to return to the main menu.
     * @Author Yiping Yan， Haipeng Yan
     */
    private Scene createWinnerScene(char winnerColor) {
        VBox layout = new VBox(7);
        layout.setAlignment(Pos.CENTER);

        Image image = new Image(getClass().getResourceAsStream("/comp1110/ass2/gui/winner.jpeg"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 70, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        layout.setBackground(background);

        if(winnerColor=='t'){
            winnerLabel = new Label("The game ends in a tie !");
        }else {
            winnerLabel = new Label(winnerColor + " player is the winner!");
        }
        winnerLabel.setFont(new Font("Arial", 24));

//        Media winMusic = new Media("comp1110/ass2/gui/win_sound.wav");
//        MediaPlayer winMusicPlayer = new MediaPlayer(winMusic);
//        winMusicPlayer.setCycleCount(1);
//        winMusicPlayer.play();
//        Exception in thread "JavaFX Application Thread" java.lang.IllegalArgumentException: uri.getScheme() == null! uri == 'comp1110/ass2/gui/win_sound.wav'


        VBox.setMargin(winnerLabel, new Insets(-330, 0, 0, 0));

        Button backButton = new Button("Go to Main Menu");
        backButton.setOnAction(e -> {
            // back to startScene
            Scene startScene = null;
            try {
                startScene = createStartScene();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Stage currentStage = (Stage) layout.getScene().getWindow();
            currentStage.setScene(startScene);
        });
        layout.getChildren().addAll(winnerLabel, backButton);
        return new Scene(layout, 500, 430);
    }

    /**
     * Check the game is over or not
     * @Author Haipeng Yan
     */
    private void checkGameOver() {
        int activePlayersCount = 0;
        Player lastActivePlayer = null;

        for (Player player : players) {
            if (player.state != 'o') {
                activePlayersCount++;
                lastActivePlayer = player;
            }
        }

        if (activePlayersCount == 1 && lastActivePlayer != null) {
            // Switch to the winner Scene for the last active player

            Stage currentStage = (Stage) root.getScene().getWindow();
            currentStage.setScene(createWinnerScene(lastActivePlayer.color.charAt(0)));
            return;
        }

        if (Marrakech.isGameOver(gameString)) {
            char winnerColor = Marrakech.getWinner(gameString);
            // Switch to the winner Scene
            Stage currentStage = (Stage) root.getScene().getWindow();
            currentStage.setScene(createWinnerScene(winnerColor));
        }
    }

    /**
     * Mapping the player color to rgp color
     * @param colorChar
     * @return Corresponding custom color
     * @Author Haipeng Yan
     */
    private Color mapCharToColor(String colorChar) {
        switch (colorChar) {
            case "c":
                return Color.rgb(56, 232, 251);
            case "y":
                return Color.rgb(255, 154, 35);
            case "r":
                return Color.rgb(250, 35, 62);
            case "p":
                return Color.rgb(85, 50, 133);
            default:
                return Color.BLACK;
        }
    }


    /**
     * Creat element of rules scene
     * @Author Haipeng Yan, Yichi Zhang
     */
    private void displayRules() {
        Stage rulesStage = new Stage();
        rulesStage.setTitle("Game Rules");

        Text rulesText = new Text();
        rulesText.setText("Objective:\n" +
                "- The goal of the game is to earn the most coins by laying rugs on the board.\n" +
                "Game Rounds:\n" +
                "- The game is played in rounds.\n" +
                "- Each round consists of a player's turn to move the Assam pawn and place rugs.\n" +
                "Taking a Turn:\n" +
                "- On a player's turn, they must perform two steps: moving the Assam pawn and placing rugs.\n" +
                "- The player may choose to move the Assam pawn in any direction (horizontally or vertically) on the board. The pawn must be moved at least one space.\n" +
                "- The player must place a rug on the space where the Assam pawn lands.\n" +
                "Paying for Rugs:\n" +
                "- When a player places a rug, they must pay the owner of the space they land on for each side of the rug that touches the space.\n" +
                "- The cost is one coin per side of the rug touching a space. The player pays this amount to the owner.\n" +
                "Earnings:\n" +
                "- If a player places a rug on an empty space, they pay nothing.\n" +
                "- If a player places a rug on their own rug (same color), they also pay nothing.\n" +
                "- If a player places a rug on an opponent's rug, they must pay the owner of that rug.\n" +
                "Winning Rugs:\n" +
                "- If the Assam pawn lands on a rug, the player who owns that rug (the one who placed it) earns a coin from the bank.\n" +
                "Special Rules:\n" +
                "- Players may not place rugs on top of the Assam pawn.\n" +
                "- The Assam pawn may not be moved onto a space with another pawn.\n" +
                "- End of the Game:\n" +
                "Scoring:\n" +
                "- The game continues until all rugs have been placed on the board.\n" +
                "- At the end of the game, players count their remaining coins.\n" +
                "Determining the Winner:\n" +
                "- The player with the most coins at the end of the game wins.");  // You can modify this text as per your game rules

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            rulesStage.close();
        });

        VBox vbox = new VBox(rulesText, closeButton);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20));

        Scene rulesScene = new Scene(vbox, 1000, 520);  // Adjust the scene dimensions if needed
        rulesStage.setScene(rulesScene);
        rulesStage.show();
    }

    /**
     * Start Scene
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     * @Author Haipeng Yan, Yiping Yan, Yichi Zhang
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // FIXME Task 7 and 15
        this.stage = primaryStage;
        stage.setTitle("Marrakech");

        URL audioUrl = getClass().getResource("./Marrakech-Markets.wav");
        if (audioUrl != null) {
            Media backgroundMusic = new Media(audioUrl.toURI().toString());
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.play();



        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Audio Error");
            alert.setContentText("Cannot find audio file!");
            alert.showAndWait();
        }


        startScene = createStartScene();
        stage.setScene(startScene);
        stage.setResizable(false);
        stage.show();


//        Scene winnerScene = createWinnerScene('r');
//        stage.setScene(winnerScene);
//        stage.show();
    }


    public String updateGameString(){
        String newGameString ="";
        for (Player player:players){
            newGameString += player.PlayerString();
        }
        newGameString += assam.getAssamString();
        newGameString+= board.toBoardString();
        gameString =newGameString;
        return gameString;
    }

    public static Assam getAssam() {
        return assam;
    }
    public static Board getBoard() {
        return board;
    }
}
