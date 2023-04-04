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
import javafx.stage.Stage;

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

    }
    private void displayStones(String stonesStatement) {

    }

    private void displayIsland(String islandStatement) {

    }

    private void displayResources(String resourcesStatement) {

    }

    private void displayPlayers(String playersStatement) {

    }




    private static String getStatement(String stateString, char a) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == a) {
                startIndex = i;

                for (int j = i; j < stateString.length(); j++) {
                    if (stateString.charAt(j) == ';') {
                        endIndex = j + 1;
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
