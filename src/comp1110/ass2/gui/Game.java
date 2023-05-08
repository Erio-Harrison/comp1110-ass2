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
import javafx.stage.Stage;




// FIXME Task 14
// FIXME Task 15
public class Game extends Application {

    private static final Group root = new Group();
    private final Group menu = new Group();
    private final Group game = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final String URI_BASE = "Resources/";

    private static final int MARGIN_X = 420;

    private static final int MARGIN_Y = 5;

    private Model model;

    public static final String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";


    // Use a state-string to initialise the game.
    private void setModel(String statestring) {
        this.model = new Model();
        model.toModel(statestring);
    }




    // Display the current element of the model as the current game state

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
                    double width = image.getWidth()-7.5;
                    double height = image.getHeight()-45;
                    double offset = width/2.0;

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
                    double width = image.getWidth()-7.5;
                    double height = image.getHeight()-45;
                    tileImage.setLayoutX((col * width) + (MARGIN_X));
                    tileImage.setLayoutY((row * height) + (MARGIN_Y));
                    game.getChildren().add(tileImage);
                }
            }


        }



    }
    public class gameState extends Group {








        public void scoreboard() {

        }

        public static void currentPlayerInventory() {
        }







    }






    public class Tile {

    }

    public class BoardTile {

    }




    @Override
    public void start(Stage stage) throws Exception {
        Scene scene2 = new Scene(this.menu, WINDOW_WIDTH, WINDOW_HEIGHT);
        Scene scene3 = new Scene(this.game, WINDOW_WIDTH,WINDOW_HEIGHT);
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        setModel(DEFAULT_GAME);


        makeBoard();


        stage.setScene(game.getScene());
        stage.show();
    }
}
