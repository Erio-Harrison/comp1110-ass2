package comp1110.ass2.gui;

import comp1110.ass2.BlueLagoon;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

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
        int x = 0;
        int y = 20;
        root.getChildren().clear();
        root.getChildren().add(controls);
        legend();
        makeControls();



        if (BlueLagoon.isStateStringWellFormed(stateString)) {
            String[] substring = stateString.split("; ");
            String[] test2 = substring[0].split(" ");
            double boardHeight = Integer.parseInt(test2[1]);
            double ratio = 13/ boardHeight;
            double size = 40;
            displayArrangement(substring[0], y,size * ratio);
            ArrayList<Text> bonusText = new ArrayList<>();
            currentState(substring[1] + ";", x, y + 40);
            for (int i = 2; i < substring.length-1; i++) {
                String curr = substring[i] + ";";
                if (curr.charAt(0) == 'i') {
                    bonusText.addAll(displayIsland(curr, size * ratio));
                } else if (curr.charAt(0) == 's') {
                    displayStones(curr, size * ratio);
                } else if (curr.charAt(0) == 'r') {
                    displayUnclaimedResources(curr, size * ratio);
                } else if (curr.charAt(0) == 'p') {
                    displayPlayers(curr, y + 80, size * ratio);
                }
            }
            displayPlayers(substring[substring.length-1], y + 300, size * ratio);
            root.getChildren().addAll(bonusText);
        }

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
     * @param gameArrangementStatement a string representing a game state
     * @param y the y coordinate to set for the text
     * @param size the size our tiles should be
     */
    private void displayArrangement(String gameArrangementStatement, int y, double size) {
        String[] arrangements = gameArrangementStatement.split(" ");
        Text layout = new Text("Layout: " + arrangements[1] + " high");
        layout.setLayoutX(0);
        layout.setLayoutY(y);

        Text players = new Text("Players: " + arrangements[2].charAt(0));
        players.setLayoutX(0);
        players.setLayoutY(y + 20);
        Text[] arragenmentText = new Text[] {layout,players};
        root.getChildren().addAll(arragenmentText);

        int boardHeight = Integer.parseInt(arrangements[1]);
        ArrayList<Hexagon> tiles = new ArrayList<>();
        for (int row = 0; row < boardHeight; row++) {
            if (row % 2 == 0) {
                for (int col = 0; col < boardHeight-1; col++) {
                    double offset = size / 2;
                    var BoardTile = new Hexagon((col * size) + (VIEWER_WIDTH / 5.) + offset, (row * size) + (VIEWER_HEIGHT / 10.), size*0.55, Color.BLUE);
                    tiles.add(BoardTile);
                }
            } else {
                for (int col = 0; col < boardHeight; col++) {
                    var BoardTile = new Hexagon((col * size) + (VIEWER_WIDTH / 5.), (row * size) + (VIEWER_HEIGHT / 10.), size*0.55, Color.BLUE);
                    tiles.add(BoardTile);
                }
            }


        }
        root.getChildren().addAll(tiles);

    }
    /**
     * Given the stones Statement of a state String, display the stones as grey hexagons
     * @param stonesStatement a string representing the stones Statement
     * @param size the size our tiles should be
     */
    private void displayStones(String stonesStatement, double size) {
        String coordinate = stoneCoordinates(stonesStatement);
        String[] statement = coordinate.split(" ");
        for (String s : statement) {
            String[] coordinates = s.split(",");
            int x1 = Integer.parseInt(coordinates[1]);
            int y1 = Integer.parseInt(coordinates[0]);
            double offset = size / 2;
            Hexagon islandCord;
            if (y1 % 2 == 0) {
                islandCord = new Hexagon((x1 * size) + (VIEWER_WIDTH / 5.) + offset, (y1 * size) + (VIEWER_HEIGHT / 10.), size * 0.4, Color.GRAY);
            } else {
                islandCord = new Hexagon((x1 * size) + (VIEWER_WIDTH / 5.), (y1 * size) + (VIEWER_HEIGHT / 10.), size * 0.4, Color.GRAY);
            }
            root.getChildren().add(islandCord);


        }
    }
    /**
     * Given the Unclaimed Resources Statement of a state String, display the unclaimed resources as squares
     * @param resourcesStatement a string representing the resources Statement
     * @param size the size our tiles should be
     */
    private void displayUnclaimedResources(String resourcesStatement, double size) {
        String resources = "CBWPS";
        Color[] colours = new Color[]{Color.WHEAT, Color.YELLOW,Color.LIGHTBLUE,Color.LIGHTGREEN, Color.BROWN};
        for (int i = 0; i < resources.length(); i++) {
            String coordinates = getUnclaimedResources(resourcesStatement, resources.charAt(i));
            String[] coordinatesList = coordinates.split(" ");
            if (coordinatesList.length > 1) {
                for (int j = 1; j < coordinatesList.length; j++) {
                    String[] coordinate = coordinatesList[j].split(",");
                    int y = Integer.parseInt(coordinate[0]);
                    int x = Integer.parseInt(coordinate[1]);
                    double offset = (double) 40 /2;
                    Hexagon islandCord;
                    if (y % 2 == 0) {
                        islandCord = new Hexagon((x * size) + (VIEWER_WIDTH / 5.) + offset, (y * size) + (VIEWER_HEIGHT / 10.), size * 0.3, colours[i]);
                    } else {
                        islandCord = new Hexagon((x * size) + (VIEWER_WIDTH / 5.), (y * size) + (VIEWER_HEIGHT / 10.) , size * 0.3, colours[i]);
                    }
                    root.getChildren().add(islandCord);
                }
            }
        }

    }

    /**
     * Given the Island Statement of a state String, display the islands as green hexagons
     * @param islandStatement a string representing a game state
     * @param size the size our tiles should be
     */
    private ArrayList<Text> displayIsland(String islandStatement, double size) {
        String[] statement = islandStatement.split(" ");
        String bonus = statement[1];


        String islands = getCoordinates(islandStatement);
        String[] islandsArray = islands.split(" ");
        ArrayList<Hexagon> tiles = new ArrayList<>();
        ArrayList<Text> bonusTexts = new ArrayList<>();
        for (int i = 1; i < islandsArray.length; i++) {
            String[] coordinates = islandsArray[i].split(",");
            int x1 = Integer.parseInt(coordinates[1]);
            int y1 = Integer.parseInt(coordinates[0]);
            double offset = size/2;
            double v = (y1 * size) + (VIEWER_HEIGHT / 10.);
            if (y1 % 2 == 0) {
                Hexagon islandCord = new Hexagon((x1 * size) + (VIEWER_WIDTH / 5.) + offset, (y1 * size) + (VIEWER_HEIGHT / 10.), size * 0.5, Color.GREEN);
                tiles.add(islandCord);
                Text bonusText = new Text(bonus);
                bonusText.setLayoutX((x1 * size) + (VIEWER_WIDTH / 5.) + offset);
                bonusText.setLayoutY(v);
                bonusTexts.add(bonusText);
            } else {
                Hexagon islandCord = new Hexagon((x1 * size) + (VIEWER_WIDTH / 5.), (y1 * size) + (VIEWER_HEIGHT / 10.), size * 0.5, Color.GREEN);
                tiles.add(islandCord);
                Text bonusText = new Text(bonus);
                bonusText.setLayoutX((x1 * size) + (VIEWER_WIDTH / 5.));
                bonusText.setLayoutY(v);
                bonusTexts.add(bonusText);
            }
        }
        root.getChildren().addAll(tiles);
        return bonusTexts;
    }

    /**
     * Given a state String, return a substring that has the stones statement
     * @param stateString stateString
     * @return stoneStatement
     */
    private static String stoneCoordinates(String stateString) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == 's') {
                startIndex = i + 2;

                for (int j = i; j < stateString.length(); j++) {
                    if (stateString.charAt(j) == ';') {
                        endIndex = j;
                        break;
                    }
                }
                break;
            }
        }
        return stateString.substring(startIndex, endIndex);
    }

    /**
     * Given an islandStatement, return a substring that has the stones statement
     * @param stateString islandStatement
     * @return coordinates of the island statement
     */
    private static String getCoordinates(String stateString) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (Character.isDigit(stateString.charAt(i))) {
                startIndex = i;

                for (int j = i; j < stateString.length(); j++) {
                    if (stateString.charAt(j) == ';') {
                        endIndex = j;
                        break;
                    }
                }
                break;
            }
        }
        return stateString.substring(startIndex, endIndex);
    }

    /**
     * Given an unclaimed Resource statement and a character representing the resource,
     * return the coordinates of that resoruce
     * @param statement unclaimed resource statement
     * @param resource character representing resource
     * @return coordinates of the unclaimed resources
     */
    private static String getUnclaimedResources(String statement, char resource) {
        String string = "";
        if (resource == 'C') {
            string += getStatement(statement, 'C', 'B');
        } else if (resource == 'B') {
            string += getStatement(statement, 'B', 'W');
        } else if (resource == 'W') {
            string += getStatement(statement, 'W', 'P');
        } else if (resource == 'P') {
            string += getStatement(statement,'P','S');
        } else string += getStatement(statement,'S',';');
        return string;
    }
    /**
     * Given a player statement and a double and an int, display the player information
     * as well as the settlers and villages on the board
     * @param playersStatement unclaimed resource statement
     * @param y y coordinate to set text
     * @param size size of the player tiles on the board
     */
    private void displayPlayers(String playersStatement, int y, double size) {
    String[] statement = playersStatement.split(" ");
    Text player = new Text("Player " + statement[1] + " Statistics:");
    player.setLayoutX(0);
    player.setLayoutY(y);
    root.getChildren().add(player);
    String[] resources = new String[]{"Coconuts ", "Bamboo ", "Water ", "Precious Stone ", "Statuettes "};
    y += 20;
    for (int i = 0; i < resources.length; i++) {
        Text resource = new Text(resources[i] + statement[i+2]);
        resource.setLayoutX(0);
        resource.setLayoutY(y);
        root.getChildren().add(resource);
        y += 20;
    }

    Text settler = new Text("Placed Settler At:");
    String settlers = getStatement(playersStatement, 'S', 'T');
    String[] settlersList = settlers.split(" ");
        ArrayList<String> settlersList2 = new ArrayList<>(Arrays.asList(settlersList).subList(1, settlersList.length));

    if (!settlersList2.isEmpty()) {
        ArrayList<Tile> rectangles = new ArrayList<>();
        double offset = size / 2;
        for (String s : settlersList2) {
            int setY = Integer.parseInt(("" + s.charAt(0)));
            int setX = Integer.parseInt(("" + s.charAt(2)));
            Tile rectangle;
            if (setX % 2 == 0) {
                rectangle = new Tile((setX * size) + (VIEWER_WIDTH / 5.) + offset, (setY * size) + (VIEWER_HEIGHT / 10.), size * 0.3, Color.BLACK);
            } else {
                rectangle = new Tile((setX * size) + (VIEWER_WIDTH / 5.), (setY * size) + (VIEWER_HEIGHT / 10.), size * 0.3, Color.BLACK);
            }
            rectangles.add(rectangle);
        }
        root.getChildren().addAll(rectangles);
    }
    Text setCoordinates = new Text(settlersList2.toString());
    setCoordinates.setLayoutX(0);
    setCoordinates.setLayoutY(y + 20);
    settler.setLayoutX(0);
    settler.setLayoutY(y);
    root.getChildren().addAll(new Text[]{setCoordinates, settler});
    y += 40;

    Text village = new Text("Place Villages At:");
    String villages = getStatement(playersStatement, 'T', ';');
        String[] villagesList = villages.split(" ");
        ArrayList<String> villagesList2 = new ArrayList<>(Arrays.asList(villagesList).subList(1, villagesList.length));

        if (!villagesList2.isEmpty()) {
            ArrayList<Tile> rectangles = new ArrayList<>();
            double offset = size / 2;
            for (String s : villagesList2) {
                int setY = Integer.parseInt(("" + s.charAt(0)));
                int setX = Integer.parseInt(("" + s.charAt(2)));
                Tile rectangle;
                if (setX % 2 == 0) {
                    rectangle = new Tile((setX * size) + (VIEWER_WIDTH / 5.) + offset, (setY * size) + (VIEWER_HEIGHT / 10.), size * 0.3, Color.FIREBRICK);
                } else {
                    rectangle = new Tile((setX * size) + (VIEWER_WIDTH / 5.), (setY * size) + (VIEWER_HEIGHT / 10.), size * 0.3, Color.FIREBRICK);
                }
                rectangles.add(rectangle);
            }
            root.getChildren().addAll(rectangles);
        }

        Text vilCord = new Text(villagesList2.toString());
        vilCord.setLayoutX(0);
        vilCord.setLayoutY(y + 20);
        village.setLayoutX(0);
        village.setLayoutY(y);
        root.getChildren().addAll(new Text[]{vilCord, village});

    }


    /**
     * Given a current state statement, display information of the current phase, and current player move
     * @param currentStateStatement stateString
     * @param x x coordinate to set text
     * @param y y coordinate to set text
     */
    private void currentState(String currentStateStatement, int x, int y) {
        String[] statement = currentStateStatement.split(" ");
        String phase;
        if (statement[2].equals("E;")) {
            phase = "Exploration";
        } else phase = "Settlement";
        Text state = new Text("Player to Move: " + statement[1] + " , Phase: " + phase);
        state.setLayoutX(x);
        state.setLayoutY(y);
        root.getChildren().add(state);
    }



    /**
     * get the substring of a statestring between the start and end characters
     *
     * @param stateString stateString
     * @param start starting character of string
     * @param end ending character of string
     * @return substring between start and end characters
     */
    private static String getStatement(String stateString, char start, char end) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == start) {
                startIndex = i;

                for (int j = i; j < stateString.length(); j++) {
                    if (stateString.charAt(j) == end) {
                        endIndex = j;
                        break;
                    }
                }
                break;
            }
        }
        return stateString.substring(startIndex, endIndex);
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
