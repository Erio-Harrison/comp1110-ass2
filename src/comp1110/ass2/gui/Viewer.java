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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;



public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField stateTextField;

    static class Tile extends Rectangle {
        public Tile(double x, double y, double size, Color color) {
            super(x, y, size, size);
            this.setFill(color);
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
            displayArrangement(substring[0], y,40 * ratio);
            currentState(substring[1] + ";", x, y + 40);
            displayPlayers(substring[substring.length-1], y + 300);
            for (int i = 2; i < substring.length-1; i++) {
                String curr = substring[i] + ";";
                if (curr.charAt(0) == 'i') {
                    displayIsland(curr, 40 * ratio);
                } else if (curr.charAt(0) == 's') {
                    displayStones(curr, 40 * ratio);
                } else if (curr.charAt(0) == 'r') {
                    displayUnclaimedResources(curr);
                } else if (curr.charAt(0) == 'p') {
                    displayPlayers(curr, y + 80);
                }
            }
        }
    }

    private void legend() {
        String[] legend = new String[]{"Board Tile", "Island", "Stones", "Coconuts", "Bamboo", "Water", "Precious Stone", "Statuettes"};
        Color[] colours = new Color[]{Color.BLUE,Color.GREEN,Color.GREY, Color.WHEAT, Color.YELLOW,Color.LIGHTBLUE,Color.LIGHTGREEN, Color.BROWN};
        int y = 20;
        for (int i = 0; i < legend.length; i++) {
            Text text = new Text(legend[i]);
            text.setLayoutX(1050);
            text.setLayoutY(y + 10);
            root.getChildren().add(text);

            Tile rectangle = new Tile(1150, y, 10, colours[i]);
            root.getChildren().add(rectangle);
            y += 20;
        }
        Text note = new Text("Number = bonus for island");
        note.setLayoutX(1000);
        note.setLayoutY(y + 10);
        root.getChildren().add(note);
    }

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
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int row = 0; row < boardHeight; row++) {
            double y1 = (row * size) + size + (size / 4);
            if (row % 2 == 0) {
                for (int col = 0; col < boardHeight-1; col++) {
                    double offset = size / 2;
                    var BoardTile = new Tile((col * size) + (VIEWER_WIDTH / 4) + offset, y1, size-1, Color.BLUE);
                    tiles.add(BoardTile);
                }
            } else {
                for (int col = 0; col < boardHeight; col++) {
                    var BoardTile = new Tile((col * size) + (VIEWER_WIDTH / 4), y1, size-1, Color.BLUE);
                    tiles.add(BoardTile);
                }
            }


        }
        root.getChildren().addAll(tiles);

    }
    private void displayStones(String stonesStatement, double size) {
        String coordinate = stoneCoordinates(stonesStatement);
        String[] statement = coordinate.split(" ");
        for (String s : statement) {
            String[] coordinates = s.split(",");
            int x1 = Integer.parseInt(coordinates[1]);
            int y1 = Integer.parseInt(coordinates[0]);
            double offset = size / 2;
            Tile islandCord;
            if (y1 % 2 == 0) {
                islandCord = new Tile((x1 * size) + (VIEWER_WIDTH / 4) + offset + (size / 12), (y1 * size) + (size * 1.25), (size / 3) - 1, Color.GRAY);
            } else {
                islandCord = new Tile((x1 * size) + (VIEWER_WIDTH / 4) + (size / 12), (y1 * size) + (size * 1.25), (size / 3) - 1, Color.GRAY);
            }
            root.getChildren().add(islandCord);


        }
    }

    private void displayUnclaimedResources(String resourcesStatement) {
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
                    Tile islandCord;
                    if (y % 2 == 0) {
                        islandCord = new Tile((x * (double) 40) + (VIEWER_WIDTH / 4) + offset + ((double) 40 / 12), (y * (double) 40) + ((double) 40 * 1.25), (double) 40 / 6, colours[i]);
                    } else {
                        islandCord = new Tile((x * (double) 40) + (VIEWER_WIDTH / 4) + ((double) 40 / 12), (y * (double) 40) + ((double) 40 * 1.25), (double) 40 / 6, colours[i]);
                    }
                    root.getChildren().add(islandCord);


                }
            }
        }

    }

    private void displayIsland(String islandStatement, double size) {
        String[] statement = islandStatement.split(" ");
        String bonus = statement[1];


        String islands = getCoordinates(islandStatement);
        String[] islandsArray = islands.split(" ");
        ArrayList<Tile> tiles = new ArrayList<>();
        ArrayList<Text> bonusTexts = new ArrayList<>();
        for (int i = 1; i < islandsArray.length; i++) {
            String[] coordinates = islandsArray[i].split(",");
            int x1 = Integer.parseInt(coordinates[1]);
            int y1 = Integer.parseInt(coordinates[0]);
            double offset = size/2;
            double v = (y1 * size) + (size * 1.28) + (size / 2.8);
            if (y1 % 2 == 0) {
                Tile islandCord = new Tile((x1 * size) + (VIEWER_WIDTH / 4) + offset + (size / 12), (y1 * size) + (size * 1.25), (0.8 * size)-1, Color.GREEN);
                tiles.add(islandCord);
                Text bonusText = new Text(bonus);
                bonusText.setLayoutX((x1 * size) + (VIEWER_WIDTH / 4) + offset + size/2.5);
                bonusText.setLayoutY(v);
                bonusTexts.add(bonusText);
            } else {
                Tile islandCord = new Tile((x1 * size) + (VIEWER_WIDTH / 4) + size / 12, (y1 * size) + (size * 1.25), (0.8 * size)-1, Color.GREEN);
                tiles.add(islandCord);
                Text bonusText = new Text(bonus);
                bonusText.setLayoutX((x1 * size) + (VIEWER_WIDTH / 4) + size/2.5);
                bonusText.setLayoutY(v);
                bonusTexts.add(bonusText);
            }
        }
        root.getChildren().addAll(tiles);
        root.getChildren().addAll(bonusTexts);
    }
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

    private void displayPlayers(String playersStatement, int y) {
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
    ArrayList<String> settlersList2 = new ArrayList<>();
    for (int i = 1; i < settlersList.length;i++) {
        String coordinate = "(" + settlersList[i] + ")";
        settlersList2.add(coordinate);
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
        ArrayList<String> villagesList2 = new ArrayList<>();
        for (int i = 1; i < villagesList.length;i++) {
            String coordinates = "(" + villagesList[i] + ")";
            villagesList2.add(coordinates);
        }
        Text vilCord = new Text(villagesList2.toString());
        vilCord.setLayoutX(0);
        vilCord.setLayoutY(y + 20);
        village.setLayoutX(0);
        village.setLayoutY(y);
        root.getChildren().addAll(new Text[]{vilCord, village});

    }



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
