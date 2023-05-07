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
import java.util.List;

// FIXME Task 14
// FIXME Task 15
public class Game extends Application {

    private final Group root = new Group();
    private final Group menu = new Group();
    private final Group game = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final String URI_BASE = "Resources/";
    Model model;





    public void modelToGui() {

    }


    public static class selectGame extends Group {
        private static final Slider playerSlider = new Slider(2,4,1);

    }
    public static class menu extends Group {
        private final Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20);





    }



    public static class game extends Group {
        private final Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20);
        private Model model;

        private final Board board = model.getBoard();
        public void scoreboard() {
           List<Board.Player> players = board.getPlayerList();
           for (Board.Player player : players) {
               int playerID = player.getId();
               PlayerPointCounter pointCounter = new PlayerPointCounter(playerID, Board.tiles,board.numOfIslands);
            int linkCount = pointCounter.linkCounter();
            int islandsCount = pointCounter.islandsCounter();
            int majorityCount = pointCounter.majorityIslandsCounter(board.islandToPoints);
            int resourcesCount = board.resourcesPoints(playerID);
            int total = board.countPoints(player.getId());

           }
        }

        public void currentPlayerInventory() {
            Board.Player currentPlayer = board.getPlayer(model.getCurrentPlayer());
            int villager = currentPlayer.getVillages();
            int settlers = currentPlayer.getSettlers();
            Integer[] resources = currentPlayer.getResources();
            Rectangle inventory = new Rectangle(20,10);
            inventory.setX(WINDOW_WIDTH/2);
            inventory.setY(WINDOW_HEIGHT);


        }







    }



    public class Tile {

    }

    public class BoardTile {

    }




    @Override
    public void start(Stage stage) throws Exception {
        Scene scene2 = new Scene(this.menu, WINDOW_WIDTH, WINDOW_HEIGHT);

        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);


        Button start = new Button("Change");
        start.setLayoutX(20);
        start.setLayoutY(20);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(scene2);
                menu.getChildren().add(start);

                stage.show();
            }
        });


        Text text = new Text("BLUE LAGOON");
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20);
        text.setFont(font);
        text.setLayoutX(200);
        text.setLayoutY(200);

        root.getChildren().add(start);
        root.getChildren().add(text);

        stage.setScene(root.getScene());
        stage.show();
    }
}
