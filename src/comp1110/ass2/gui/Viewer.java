package comp1110.ass2.gui;

import comp1110.ass2.BlueLagoon;
import comp1110.ass2.Board;
import comp1110.ass2.Model;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Authored by Kenney Siu
 * A visual representation of a given stateString
 */

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField stateTextField;

    private Model model = new Model();

    Game game = new Game();


    private static final String URI_BASE = "Resources/";
    private static final int MARGIN_X = 200;
    private static final int MARGIN_Y = 40;
    private static final double TILE_SPACING_X = 60.0;
    private static final double OFFSET = TILE_SPACING_X/2.0;
    private static final double TILE_SPACING_Y = 42.0;



    // Constructor that is used to represent our game pieces
    static class Tile extends Rectangle {
        public Tile(double x, double y, double size, Color color) {
            super(x, y, size, size);
            this.setFill(color);
        }
    }
    // Constructor Hexagon that is used to represent our board tiles, islands and stones
    static class Hexagon extends Polygon {

        Hexagon(double x, double y, double size, Color color) {
            super();
            this.setFill(color);

            this.getPoints().addAll(
                    -size/2, size,
                    size/2,size,
                    size,0.,
                    size/2,-size,
                    -size/2,-size,
                    -size,0.
            );
            this.setRotate(30.);
            this.setLayoutX(x);
            this.setLayoutY(y);
        }
    }

    private void makeBoard() {
        int boardSize = this.model.getBoard().boardSize;
        Board.Tile[][] tiles = Board.tiles;

        for (int row = 0; row < boardSize; row++) {
            int var = 0;
            if (row % 2 == 0) {
                var = 1;
            }
            for (int col = 0; col < boardSize - var; col++) {
                Board.Tile curr = tiles[row][col];
                String path = URI_BASE + Board.toURL(curr);
                Image image = new Image(getClass().getResource(path).toString());
                ImageView tileImage = new ImageView(image);
                tileImage.setLayoutX((col * TILE_SPACING_X) + (MARGIN_X) + var * OFFSET);
                tileImage.setLayoutY((row * TILE_SPACING_Y) + (MARGIN_Y));
                root.getChildren().add(tileImage);
            }
        }
    }

    /**
     * Given a state string, draw a representation of the state
     * on the screen.
     * <p>
     * This may prove useful for debugging complex states.
     *
     * @param stateString a string representing a game state
     */
    void displayState(String stateString) {
        // FIXME Task 5
        if (BlueLagoon.isStateStringWellFormed(stateString) )
            this.model.toModel(stateString);
            root.getChildren().clear();
            root.getChildren().add(controls);
            legend();
            makeControls();
            makeBoard();
            displayArrangement();
            makeResources();
            currentState();
            displayPlayers();


    }

    /**
     * Display a legend in the top right corner showing what each square or hexagon represents
     */

    private void legend() {
        String[] legend = new String[]{"Board Tile", "Island", "Stones", "Coconuts", "Bamboo", "Water", "Precious Stone", "Statuettes", "Settlers", "Villages"};
        Color[] colours = new Color[]{Color.BLUE,Color.GREEN,Color.GREY, Color.WHEAT, Color.YELLOW,Color.LIGHTBLUE,Color.LIGHTGREEN, Color.BROWN, Color.BLACK, Color.FIREBRICK};
        int y = 20;
        for (int i = 0; i < legend.length; i++) {
            Text text = new Text(legend[i]);
            text.setLayoutX(1050);
            text.setLayoutY(y + 10);
            root.getChildren().add(text);

            if (i >= 8) {
                Tile shape = new Tile(1150, y, 10, colours[i]);
                root.getChildren().add(shape);
            } else {
                Hexagon shape = new Hexagon(1150, y, 10, colours[i]);
                root.getChildren().add(shape);
            }
            y += 20;
        }

        Text note = new Text("Number = bonus for island");
        note.setLayoutX(1000);
        note.setLayoutY(y + 10);
        root.getChildren().add(note);
    }

    /**
     * Given the Game Arrangement Statement of a state String, display game information and
     * display the board as water tiles
     */
    private void displayArrangement() {

        Text layout = new Text("Layout: " + model.board.boardSize + " high");
        layout.setLayoutX(0);
        layout.setLayoutY(20);

        Text players = new Text("Players: " + model.board.playerList.size());
        players.setLayoutX(0);
        players.setLayoutY(40);
        Text[] arragenmentText = new Text[] {layout,players};
        root.getChildren().addAll(arragenmentText);
    }
    /**
     * Given the stones Statement of a state String, display the stones as grey hexagons
     */
    private void makeResources() {
        ArrayList< Board.Position> stoneCoords = model.board.getStoneCoordinates();
        for (Board.Position coords : stoneCoords) {
            int row = coords.getX();
            int col = coords.getY();
            double boardX;
            double boardY;
            Board.Tile.Resource resource = Board.tiles[row][col].getResource();
            if (resource != null) {
                if (row % 2 == 0) {
                    boardX = (col * TILE_SPACING_X) + TILE_SPACING_X/2 + OFFSET + MARGIN_X;
                } else boardX = (col * TILE_SPACING_X) + TILE_SPACING_X/2 + MARGIN_X;
                boardY = row * TILE_SPACING_Y + 3*TILE_SPACING_Y/4 + MARGIN_Y;
                resourceToShape(boardX,boardY,resource);
            }
        }

    }

    public void resourceToShape(double x, double y, Board.Tile.Resource resource) {
        switch (resource) {
            case STON -> {
                Rectangle rectangle = new Rectangle(x,y,10,20);
                rectangle.setFill(Color.GREEN);
                root.getChildren().add(rectangle);
            }
            case COCO -> {
                Circle circle = new Circle(x,y,20,Color.WHITE);
                root.getChildren().add(circle);
            }
            case BBOO -> {
                Rectangle rectangle = new Rectangle(x,y,10,20);
                rectangle.setFill(Color.YELLOW);
                root.getChildren().add(rectangle);
            }
            case STAT -> {
                Rectangle rectangle = new Rectangle(x,y,10,20);
                rectangle.setFill(Color.FIREBRICK);
                root.getChildren().add(rectangle);
            }
            case WATR -> {
                Circle circle = new Circle(x,y,20,Color.FIREBRICK);
                root.getChildren().add(circle);
            }
        }
    }





    private void displayPlayers() {
        int initialY = 80;
        int playerSpacing = 80;
        List<Board.Player> players = model.board.playerList;
        for (Board.Player player : players) {

            Text playerText = new Text(0,initialY + playerSpacing * players.indexOf(player), "Player: " + player.getId() + " Statistics");
            initialY += 20;
            Text score = new Text(0,initialY + playerSpacing *  players.indexOf(player), "Score: " + player.getPoints());
            initialY += 20;
            Text resourcesInstructions  = new Text(0,initialY + playerSpacing *  players.indexOf(player), "COCO,BBOO,WATR,STON,STAT");
            initialY += 20;
            Text resources = new Text(0,initialY + playerSpacing *  players.indexOf(player), "Resources: " + Arrays.toString(player.getResources()));
            initialY += 20;
            Text settlers = new Text(0,initialY + playerSpacing *  players.indexOf(player), "Settlers Placed" );
            initialY += 20;
            Text set = new Text(0, initialY + playerSpacing * players.indexOf(player), model.board.getOccupiedTiles(player.getId(),0).toString());
            initialY += 20;
            Text villages = new Text(0,initialY + playerSpacing *  players.indexOf(player), "Villages Placed");
            initialY += 20;
            Text vil = new Text(0, initialY + playerSpacing * players.indexOf(player), model.board.getOccupiedTiles(player.getId(),1).toString());



            root.getChildren().addAll(playerText,score,resources, resourcesInstructions,settlers, set,villages, vil);
        }
    }



    private void currentState() {
        int currentPlayer = model.getCurrentPlayer();
        Text state = new Text("Player to Move: " + currentPlayer + " , Phase: " + model.toPhase());
        state.setLayoutX(0);
        state.setLayoutY(60);
        root.getChildren().add(state);
    }







    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label playerLabel = new Label("Game State:");
        stateTextField = new TextField();
        stateTextField.setPrefWidth(200);
        Button button = new Button("Refresh");
        button.setOnAction(e -> displayState(stateTextField.getText()));
        HBox hb = new HBox();
        hb.getChildren().addAll(playerLabel, stateTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Blue Lagoon Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);
        legend();
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
