package comp1110.ass2.gui;

import comp1110.ass2.Model;
import javafx.application.Application;
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

import java.awt.*;

// FIXME Task 14
// FIXME Task 15
public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final String URI_BASE = "Resources/";
    Model model;

    private final Group menu = new Group();
    private final Group game = new Group();



    public void modelToGui() {

    }



    public static class menu extends Group {
        private final Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20);





    }
    public class Tile {

    }

    public class BoardTile {

    }




    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();

    }
}
