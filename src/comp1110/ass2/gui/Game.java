package comp1110.ass2.gui;

import comp1110.ass2.Board;
import comp1110.ass2.Model;
import comp1110.ass2.PlayerPointCounter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import javax.swing.*;
import java.awt.*;
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
    Model model;





    public class selectGame extends Group {
        private static final Slider playerSlider = new Slider(2,4,1);

    }
    public class menu extends Group {
        private final Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20);





    }



    public class GameState extends Group {
        private final Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20);
        private static Model model = new Model();

        public static final String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";



        // Use a statestring to initialise the game.
        public static void setModel(String statestring) {
        model.toModel(statestring);
        }

        // Display the current element of the model as the current game state
        public void modelToGui() {

        }

        public void scoreboard() {

        }

        public static void currentPlayerInventory() {
            int textOffset = 100;
            Board board = model.getBoard();
            Board.Player currentPlayer = board.getPlayer(model.getCurrentPlayer());
            int villager = currentPlayer.getVillages();
            int settlers = currentPlayer.getSettlers();
            Integer[] resources = currentPlayer.getResources();
            Rectangle inventory = new Rectangle(WINDOW_WIDTH/2,WINDOW_HEIGHT/10, Color.FIREBRICK);
            inventory.setX(WINDOW_WIDTH/4);
            inventory.setY(WINDOW_HEIGHT - WINDOW_HEIGHT/10);
            Text text = new Text("Villages: " + Integer.toString(villager));
            Text text2 = new Text("Settlers:" + Integer.toString(settlers));
            Text text3 = new Text(Arrays.toString(resources));
            Text text4 = new Text("Resources COCONUT, BAMBOO, WATER, STONES, STATUETTES:" );
            text.setX(WINDOW_WIDTH/4);
            text.setY(WINDOW_HEIGHT - WINDOW_HEIGHT/13);
            text2.setX(WINDOW_WIDTH/4 + textOffset);
            text2.setY(WINDOW_HEIGHT - WINDOW_HEIGHT/13);
            text3.setX(WINDOW_WIDTH/4 + 3*textOffset);
            text3.setY(WINDOW_HEIGHT - WINDOW_HEIGHT/15);
            text4.setX(WINDOW_WIDTH/4 + 2*textOffset);
            text4.setY(WINDOW_HEIGHT - WINDOW_HEIGHT/12);
            root.getChildren().add(inventory);
            root.getChildren().addAll(new Text[]{text,text2,text3,text4});

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
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);




        Text text = new Text("BLUE LAGOON");
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20);
        text.setFont(font);
        text.setLayoutX(200);
        text.setLayoutY(200);
        root.getChildren().add(text);
        GameState.setModel(GameState.DEFAULT_GAME);
        GameState.currentPlayerInventory();


        stage.setScene(root.getScene());
        stage.show();
    }
}
