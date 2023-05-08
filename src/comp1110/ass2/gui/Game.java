package comp1110.ass2.gui;

import comp1110.ass2.Board;
import comp1110.ass2.Model;
import comp1110.ass2.PlayerPointCounter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// FIXME Task 14
// FIXME Task 15
public class Game extends Application {

    private static final Group root = new Group();
    private final Group menu = new Group();
    private final Group game = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final String URI_BASE = "Resources/";

    private static final int MARGIN_X = 250;

    private static final int MARGIN_Y = 40;


    private static final double TILE_SPACING_X = 65.0;

    private static final double OFFSET = 32.0;
    private static final double TILE_SPACING_Y = 47.0;


    private Model model;

    public static final String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";


    // Use a state-string to initialise the game.
    private void setModel(String statestring) {
        this.model = new Model();
        model.toModel(statestring);
    }





    // make the elements of the board, such as the islands, stones, resources, etc.
    private void makeBoard() {
        int boardSize = this.model.getBoard().boardSize;
        Board.Tile[][] tiles = this.model.getBoard().tiles;




        for (int row = 0; row < boardSize; row++) {
            if (row % 2 == 0) {
                for (int col = 0; col < boardSize-1; col++) {
                    Board.Tile curr = tiles[row][col];
                    String path = URI_BASE + Board.toURL(curr);
                    Image image = new Image(getClass().getResource(path).toString());
                    ImageView tileImage = new ImageView(image);
                    double width = image.getWidth();
                    double height = image.getHeight() - 42;
                    double offset = (int) (width/2.0);
                    System.out.println(height);
                    tileImage.setLayoutX((col * width) + (MARGIN_X) + offset);
                    tileImage.setLayoutY((row * height) + (MARGIN_Y));

                    game.getChildren().add(tileImage);
                }
            } else {
                for (int col = 0; col < boardSize; col++) {
                    Board.Tile curr = tiles[row][col];
                    String path = URI_BASE + Board.toURL(curr);
                    Image image = new Image(Game.class.getResource(path).toString());
                    ImageView tileImage = new ImageView(image);
                    int width = (int) image.getWidth();
                    double height = image.getHeight()-42;
                    tileImage.setLayoutX((col * width) + (MARGIN_X));
                    tileImage.setLayoutY((row * height) + (MARGIN_Y));

                    game.getChildren().add(tileImage);
                }
            }


        }
    }
    private void makeResources() {

    }

    private void makeScoreboard() {

        // Each player
        Board board = this.model.getBoard();
        int numberOfPlayers = model.numOfPlayers;

        // Row Labels
        Text playerT = new Text(10, 40, "Player: ");
        Text islandsT = new Text(10, 60, "7/8 Islands:");
        Text majoritiesT= new Text(10, 80, "Majority Islands:");
        Text linksT = new Text(10, 100, "Links:");
        Text resourcesT = new Text(10, 120, "Total Resources:");
        Text totalT = new Text(10, 140, "Total Points:");
        ArrayList<Text> textsL = new ArrayList<>(Arrays.asList(playerT,islandsT,majoritiesT,linksT,resourcesT,totalT));
        game.getChildren().addAll(textsL);
        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerPointCounter pointCounter = new PlayerPointCounter(i, board.tiles, board.numOfIslands);

            // Scores
            Text scoreBoard = new Text(130/2, 20, "ScoreBoard");
            Text player = new Text(50 * i + 120, 40, ""+ i);
            Text islands = new Text(50 * i + 120, 60, "" + pointCounter.islandsCounter());
            Text majorityIslands = new Text(50 * i+ 120, 80, "" + pointCounter.majorityIslandsCounter(board.islandToPoints));
            Text links = new Text(50 * i + 120, 100, "" + pointCounter.linkCounter());
            Text resources = new Text(50 * i + 120, 120, "" + board.resourcesPoints(i));
            Text total = new Text(50 * i + 120, 140, "" +board.countPoints(i));
            ArrayList<Text> texts = new ArrayList<>(Arrays.asList(scoreBoard,player,islands,majorityIslands,links,resources,total));
            game.getChildren().addAll(texts);
        }
    }

    private void makeGameTokens() {

        SettlerPiece settlerToken = new SettlerPiece();
        game.getChildren().add(settlerToken);
    }


    private void makeCurrentInventory() {
        Board board = this.model.getBoard();
        Board.Player currentPlayer = board.getPlayer(model.currentPlayer);
        Text title = new Text(150/2, 400, "Inventory");

        game.getChildren().add(title);

        Text villagerCount = new Text(10, 420, "Villagers: " + (30 - currentPlayer.getVillages()) + " Left");
        Text settlerCount = new Text(10 + 100, 420, "Villagers: " + (5 - currentPlayer.getSettlers()) + " Left");
        game.getChildren().addAll(new Text[]{villagerCount,settlerCount});
    }



    class SettlerPiece extends Group {

        Color[] tokenColours = new Color[]{Color.YELLOW, Color.PURPLE, Color.ORANGE, Color.BLUE};
        SettlerToken settler;

        double mouseX, mouseY;
        double homeX;
        double homeY;

        public SettlerPiece() {
            this.homeX = 50;
            this.homeY = 470;

            this.settler = new SettlerToken();
            this.getChildren().add(this.settler);
            this.setOnMousePressed(event -> {
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();

            });

            this.setOnMouseDragged(event -> {

                /*
                 Move the caterpillar by the difference in mouse position
                 since the last drag.
                 */
                double diffX = event.getSceneX() - mouseX;
                double diffY = event.getSceneY() - mouseY;
                this.setLayoutX(this.getLayoutX() + diffX);
                this.setLayoutY(this.getLayoutY() + diffY);

                /*
                 Update `mouseX` and `mouseY` and repeat the process.
                 */
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
            });

            this.setOnMouseReleased(event -> {

            });

            this.snapToHome();
        }
// get the resource coordinate in terms of (0,0)
        public int[] getSnapPosition() {
            int x;
            int y = (int) Math.round((this.getLayoutY() - MARGIN_Y) / TILE_SPACING_Y);
            if (y % 2 == 0) {
                x = (int) Math.round((this.getLayoutY() - MARGIN_X - OFFSET) / TILE_SPACING_X);
            } else {
                x = (int) Math.round((this.getLayoutY() - MARGIN_X) / TILE_SPACING_X);
            }

            return new int[]{x, y};
        }


        private void snapToHome() {
            this.setLayoutX(this.homeX);
            this.setLayoutY(this.homeY);
        }





    }

    class SettlerToken extends ImageView {

        String path = URI_BASE + "stone.png";

        public SettlerToken() {
            Image image = new Image(Game.class.getResource(path).toString());
            this.setImage(image);

        }

    }




    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.game, WINDOW_WIDTH, WINDOW_HEIGHT);
        setModel(DEFAULT_GAME);


        makeBoard();
        makeScoreboard();
        makeCurrentInventory();
        makeGameTokens();

        stage.setScene(game.getScene());
        stage.show();
    }
}
