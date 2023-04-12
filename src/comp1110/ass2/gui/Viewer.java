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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField stateTextField;





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

    private void displayArrangement(String gameArrangementStatement) {
        String[] arrangements = gameArrangementStatement.split(" ");
        Text layout = new Text("Layout: " + arrangements[1] + " high");
        layout.setLayoutX(0);
        layout.setLayoutY(40);

        Text players = new Text("Players: " + arrangements[2].charAt(0));
        players.setLayoutX(0);
        players.setLayoutY(20);
        Text[] arragenmentText = new Text[] {layout,players};
        root.getChildren().addAll(arragenmentText);
    }
    private void displayStones(String stonesStatement) {

    }

    private void displayIslands(String islandStatement) {

    }

    private void displayResources(String resourcesStatement) {

    }

    private void displayPlayers(String playersStatement) {
    String[] statement = playersStatement.split(" ");
    Text player = new Text("Player " + statement[1] + " Statistics:");
    player.setLayoutX(0);
    player.setLayoutY(80);
    root.getChildren().add(player);
    String[] resources = new String[]{"Coconuts ", "Bamboo ", "Water ", "Precious Stone ", "Statuettes "};
    int y = 100;
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
    for (int i = 0; i < settlersList.length;i++) {
        settlersList[i] = "(" + settlersList[i] + ")";
    }
    Text setCoordinates = new Text(Arrays.toString(settlersList));
    setCoordinates.setLayoutX(0);
    setCoordinates.setLayoutY(y + 20);
    settler.setLayoutX(0);
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
        vilCord.setLayoutX(0);
        vilCord.setLayoutY(y + 20);
        village.setLayoutX(0);
        village.setLayoutY(y);
        root.getChildren().addAll(new Text[]{vilCord, village});

    }



    private void currentState(String currentStateStatement) {
        String[] statement = currentStateStatement.split(" ");
        String phase;
        if (statement[2].equals("E;")) {
            phase = "Exploration";
        } else phase = "Settlement";
        Text state = new Text("Player to Move: " + statement[1] + " , Phase: " + phase);
        state.setLayoutX(0);
        state.setLayoutY(60);
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

        displayArrangement("a 13 2;");
        currentState("c 0 E;");
        displayPlayers("p 1 42 1 2 3 4 5 S 5,6 8,7 T 1,2;");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
