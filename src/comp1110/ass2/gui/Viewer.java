package comp1110.ass2.gui;

import comp1110.ass2.BlueLagoon;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.Arrays;


public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField stateTextField;

    class Tile extends Rectangle {
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
        if (BlueLagoon.isStateStringWellFormed(stateString)) {

            String[] substring = stateString.split("; ");
            displayArrangement(substring[0],0,y);
            currentState(substring[1] + ";", x, y + 40);
            displayPlayers(substring[substring.length-1], 0,y + 300);
            for (int i = 2; i < substring.length-1; i++) {
                String curr = substring[i] + ";";
                if (curr.charAt(0) == 'i') {
                    displayIsland(curr,0,y + 60);
                } else if (curr.charAt(0) == 's') {
                    displayStones(curr);
                } else if (curr.charAt(0) == 'r') {
                    displayUnclaimedResources(curr);
                } else if (curr.charAt(0) == 'p') {
                    displayPlayers(curr,0,y + 80);
                }
            }
        }
    }


    private void displayArrangement(String gameArrangementStatement, int x, int y) {
        String[] arrangements = gameArrangementStatement.split(" ");
        Text layout = new Text("Layout: " + arrangements[1] + " high");
        layout.setLayoutX(x);
        layout.setLayoutY(y);

        Text players = new Text("Players: " + arrangements[2].charAt(0));
        players.setLayoutX(x);
        players.setLayoutY(y + 20);
        Text[] arragenmentText = new Text[] {layout,players};
        root.getChildren().addAll(arragenmentText);

        int boardheight = Integer.parseInt(arrangements[1]);
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int row = 0; row < boardheight; row++) {
            if (row % 2 == 0) {
                for (int col = 0; col < boardheight-1; col++) {
                    int offset = 15;
                    var BoardTile = new Tile((col * 30) + (VIEWER_WIDTH / 3) + offset,(row * 30) + 35,30, Color.BLUE);
                    tiles.add(BoardTile);
                }
            } else {
                for (int col = 0; col < boardheight; col++) {
                    var BoardTile = new Tile((col * 30) + (VIEWER_WIDTH / 3),(row * 30) + 35,30, Color.BLUE);
                    tiles.add(BoardTile);
                }
            }


        }
        root.getChildren().addAll(tiles);
    }
    private void displayStones(String stonesStatement) {
        String coords = stoneCoords(stonesStatement,'s',';');
        String[] statement = coords.split(" ");
        for (int i = 0; i < statement.length; i++) {
            String[] coordinates = statement[i].split(",");
            int x1 = Integer.parseInt(coordinates[1]);
            int y1 = Integer.parseInt(coordinates[0]);
            int offset = 15;
            if (y1 % 2 == 0) {
                Tile islandCord = new Tile((x1 * 30) + (VIEWER_WIDTH / 3) + offset + 2,(y1 * 30) + 35 + 3,10, Color.GRAY);
                root.getChildren().add(islandCord);
            } else {
                Tile islandCord = new Tile((x1 * 30) + (VIEWER_WIDTH / 3) + 2,(y1 * 30) + 35 + 3,10, Color.GRAY);
                root.getChildren().add(islandCord);
            }


        }
    }

    private void displayUnclaimedResources(String resourcesStatement) {
        String resources = "CBWPS";
        Color[] colours = new Color[]{Color.WHITE, Color.YELLOW,Color.LIGHTBLUE,Color.LIGHTGREEN, Color.BROWN};
        for (int i = 0; i < resources.length(); i++) {
            String coords = getUnclaimedResources(resourcesStatement, resources.charAt(i));
            String[] coordsList = coords.split(" ");
            if (coordsList.length > 1) {
                for (int j = 1; j < coordsList.length; j++) {
                    String[] coordinate = coordsList[j].split(",");
                    int y = Integer.parseInt(coordinate[0]);
                    int x = Integer.parseInt(coordinate[1]);
                    Tile islandCord = new Tile((x * 30) + (VIEWER_WIDTH / 3),(y * 30) + 35,43, colours[i]);
                    root.getChildren().add(islandCord);

                }
            }
        }

    }

    private void displayIsland(String islandStatement,int x, int y) {
        String[] statement = islandStatement.split(" ");
        Text bonus = new Text(statement[1]);
        //bonus.setLayoutX(x);
        //bonus.setLayoutY(y);
        //root.getChildren().add(bonus);

        String islands = getCoords(islandStatement,';');
        String[] islandsArray = islands.split(" ");
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 1; i < islandsArray.length; i++) {
            String[] coordinates = islandsArray[i].split(",");
            int x1 = Integer.parseInt(coordinates[1]);
            int y1 = Integer.parseInt(coordinates[0]);
            int offset = 28/2;
            if (y1 % 2 == 0) {
                Tile islandCord = new Tile((x1 * 30) + (VIEWER_WIDTH / 3) + offset + 2,(y1 * 30) + 35 + 2,25, Color.GREEN);
                tiles.add(islandCord);
            } else {
                Tile islandCord = new Tile((x1 * 30) + (VIEWER_WIDTH / 3) + 2,(y1 * 30) + 35 + 2,25, Color.GREEN);
                tiles.add(islandCord);
            }
        }
        root.getChildren().addAll(tiles);
    }
    private static String stoneCoords(String stateString, char start, char end) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == start) {
                startIndex = i + 2;

                for (int j = i; j < stateString.length(); j++) {
                    if (stateString.charAt(j) == end) {
                        endIndex = j;
                        break;
                    }
                }
                break;
            }
        }
        String result = stateString.substring(startIndex, endIndex);
        return result;
    }


    private static String getCoords(String stateString, char end) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (Character.isDigit(stateString.charAt(i))) {
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
        String result = stateString.substring(startIndex, endIndex);
        return result;
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

    private void displayPlayers(String playersStatement, int x, int y) {
    String[] statement = playersStatement.split(" ");
    Text player = new Text("Player " + statement[1] + " Statistics:");
    player.setLayoutX(x);
    player.setLayoutY(y);
    root.getChildren().add(player);
    String[] resources = new String[]{"Coconuts ", "Bamboo ", "Water ", "Precious Stone ", "Statuettes "};
    y += 20;
    for (int i = 0; i < resources.length; i++) {
        Text resource = new Text(resources[i] + statement[i+2]);
        resource.setLayoutX(x);
        resource.setLayoutY(y);
        root.getChildren().add(resource);
        y += 20;
    }

    Text settler = new Text("Placed Settler At:");
    String settlers = getStatement(playersStatement, 'S', 'T');
    String[] settlersList = settlers.split(" ");
    for (int i = 0; i < settlersList.length;i++) {
        settlersList[i] = "(" + settlersList[i] + ")";
    }
    Text setCoordinates = new Text(Arrays.toString(settlersList));
    setCoordinates.setLayoutX(x);
    setCoordinates.setLayoutY(y + 20);
    settler.setLayoutX(x);
    settler.setLayoutY(y);
    root.getChildren().addAll(new Text[]{setCoordinates, settler});
    y += 40;

    Text village = new Text("Place Villages At:");
    String villages = getStatement(playersStatement, 'T', ';');
        String[] villagesList = villages.split(" ");
        for (int i = 0; i < villagesList.length;i++) {
            villagesList[i] = "(" + villagesList[i] + ")";
        }
        Text vilCord = new Text(Arrays.toString(villagesList));
        vilCord.setLayoutX(x);
        vilCord.setLayoutY(y + 20);
        village.setLayoutX(x);
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
        String result = stateString.substring(startIndex, endIndex);
        return result;
    }






    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label playerLabel = new Label("Game State:");
        stateTextField = new TextField();
        stateTextField.setPrefWidth(200);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {displayState(stateTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(playerLabel, stateTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Blue Lagoon Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
