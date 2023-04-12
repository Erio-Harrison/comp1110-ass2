package comp1110.ass2.gui;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField stateTextField;

    class Tile extends Rectangle {
        public Tile(double v, double v1, double v2, Color color) {
            super(v, v1, v2, v2);
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

        // gameState = gameArrangementStatement, " ",
        // currentStateStatement, {" ", islandStatement},
        // " ", stonesStatement, " ",
        // unclaimedResourcesAndStatuettesStatement,
        // {" ", playerStatement}

    }


    private void displayArrangement(String gameArrangementStatement, int x, int y) {
        String[] arrangements = gameArrangementStatement.split(" ");
        Text layout = new Text("Layout: " + arrangements[1] + " high");
        layout.setLayoutX(x);
        layout.setLayoutY(y+20);

        Text players = new Text("Players: " + arrangements[2].charAt(0));
        players.setLayoutX(x);
        players.setLayoutY(y);
        Text[] arragenmentText = new Text[] {layout,players};
        root.getChildren().addAll(arragenmentText);

        int boardheight = Integer.parseInt(arrangements[1]);
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < boardheight; i++) {
            for (int j = 0; j < boardheight ; j++) {
                var BoardTile = new Tile((i * 45) + (VIEWER_WIDTH / 4),(j * 45) + 40,43, Color.BLUE);
                tiles.add(BoardTile);
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
            Tile islandCord = new Tile((x1 * 45) + (VIEWER_WIDTH / 4),(y1 * 45) + 40,43, Color.GRAY);
            root.getChildren().add(islandCord);

        }
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

    private void displayIsland(String islandStatement,int x, int y) {
        String[] statement = islandStatement.split(" ");
        Text bonus = new Text(statement[1]);
        bonus.setLayoutX(x);
        bonus.setLayoutY(y);
        root.getChildren().add(bonus);

        String islands = getCoords(islandStatement,'i',';');
        String[] islandsArray = islands.split(" ");
        for (int i = 0; i < islandsArray.length; i++) {
            String[] coordinates = islandsArray[i].split(",");
            int x1 = Integer.parseInt(coordinates[1]);
            int y1 = Integer.parseInt(coordinates[0]);
            Tile islandCord = new Tile((x1 * 45) + (VIEWER_WIDTH / 4),(y1 * 45) + 40,43, Color.GREEN);
            root.getChildren().add(islandCord);

        }

    }
    private static String getCoords(String stateString, char start, char end) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == start) {
                startIndex = i + 4;

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


    private void displayUnclaimedResources(String resourcesStatement) {
        String resources = "CBWPS";
        Color[] colours = new Color[]{Color.WHITE, Color.YELLOW,Color.LIGHTBLUE,Color.LIGHTGREEN};
        for (int i = 0; i < resources.length(); i++) {
            String coords = getUnclaimedResources(resourcesStatement, resources.charAt(i));
            String[] coordsList = coords.split(" ");
            if (coordsList.length > 1) {
                for (int j = 1; j < coordsList.length; j++) {
                    String[] coordinate = coordsList[j].split(",");
                    int y = Integer.parseInt(coordinate[0]);
                    int x = Integer.parseInt(coordinate[1]);
                    Tile islandCord = new Tile((x * 45) + (VIEWER_WIDTH / 4),(y * 45) + 40,43, colours[i]);
                    root.getChildren().add(islandCord);

                }
            }
        }

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

        displayArrangement("a 13 2;",0, 20);
        currentState("c 0 E;",0, 60);
        displayPlayers("p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2;",0,80);
        displayIsland("i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1;", 20,0);
        displayIsland("i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8;", 20, 0);
        displayStones("s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11;");
        displayUnclaimedResources("r C 1,1 B 1,2 W P 1,4 S;");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
