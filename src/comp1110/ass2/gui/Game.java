package comp1110.ass2.gui;

import com.sun.javafx.stage.WindowHelper;
import comp1110.ass2.BlueLagoon;
import comp1110.ass2.Board;
import comp1110.ass2.Model;
import comp1110.ass2.PlayerPointCounter;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// FIXME Task 14
// FIXME Task 15
public class Game extends Application {

    private final Group menu = new Group();

    private final Group selectGame = new Group();
    private final Group game = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final String URI_BASE = "Resources/";
    private static final int MARGIN_X = 350;
    private static final int MARGIN_Y = 40;
    private static final double TILE_SPACING_X = 60.0;
    private static final double OFFSET = TILE_SPACING_X/2.0;
    private static final double TILE_SPACING_Y = 42.0;
    private Model model;

    private static final double DEFAULT_BOARD = 13;

    private static double SIZING_RATIO;

    private static String CURRENT_GAME;

    // Use a state-string to initialise the game.
    private void setModel(String statestring) {
        this.model = new Model();
        this.model.toModel(statestring);
        List<Board.Tile> stoneCoords = this.model.board.getStoneRsrcTiles();
        this.model.board.assignRanResources(stoneCoords);
    }

    // make the elements of the board, such as the islands, stones, resources, etc.
    private void makeBoard() {
/*        String path2 = URI_BASE + "menu.png";
        Image background = new Image(getClass().getResource(path2).toString());
        ImageView imageView = new ImageView(background);
        game.getChildren().add(imageView);
*/


        int boardSize = this.model.getBoard().boardSize;
        SIZING_RATIO = DEFAULT_BOARD / boardSize;
        Board.Tile[][] tiles = Board.tiles;

        for (int row = 0; row < boardSize; row++) {
            int var = 0;
            if (row % 2 == 0) {
                var = 1;
            }
            for (int col = 0; col < boardSize - var; col++) {
                Board.Tile curr = tiles[row][col];
                String path = URI_BASE + Board.toURL(curr);
                Image image = new Image(getClass().getResource(path).toString());
                ImageView tileImage = new ImageView(image);
                tileImage.setFitWidth(65 * SIZING_RATIO);
                tileImage.setFitHeight(89 * SIZING_RATIO);
                tileImage.setPreserveRatio(true);
                tileImage.setLayoutX((col * TILE_SPACING_X * SIZING_RATIO) + (MARGIN_X) + var * OFFSET * SIZING_RATIO);
                tileImage.setLayoutY((row * TILE_SPACING_Y * SIZING_RATIO) + (MARGIN_Y));
                game.getChildren().add(tileImage);
            }
        }
    }

    // sets resources on the board
    private void makeResources() {
        ArrayList< Board.Position> stoneCoords = model.board.getStoneCoordinates();
        for (Board.Position coords : stoneCoords) {
            int row = coords.getX();
            int col = coords.getY();
            double boardX;
            double boardY;
            Board.Tile.Resource resource = Board.tiles[row][col].getResource();
            if (resource != null) {
                if (row % 2 == 0) {
                    boardX = (col * TILE_SPACING_X * SIZING_RATIO) + TILE_SPACING_X/2 * SIZING_RATIO + OFFSET * SIZING_RATIO + MARGIN_X;
                } else boardX = (col * TILE_SPACING_X * SIZING_RATIO) + TILE_SPACING_X/2 * SIZING_RATIO + MARGIN_X;
                boardY = row * TILE_SPACING_Y * SIZING_RATIO + 3*TILE_SPACING_Y/4 * SIZING_RATIO + MARGIN_Y;
                resourceToShape(boardX,boardY,resource, 20 * SIZING_RATIO);
            }
        }

    }
    public void resourceToShape(double x, double y, Board.Tile.Resource resource, double size) {
        switch (resource) {
            case STON -> {
                Rectangle rectangle = new Rectangle(x,y,size/2,size);
                rectangle.setFill(Color.GREEN);
                game.getChildren().add(rectangle);
            }
            case COCO -> {
                Circle circle = new Circle(x,y,size,Color.WHITE);
                game.getChildren().add(circle);
            }
            case BBOO -> {
                Rectangle rectangle = new Rectangle(x,y,size/2,size);
                rectangle.setFill(Color.YELLOW);
                game.getChildren().add(rectangle);
            }
            case STAT -> {
                Rectangle rectangle = new Rectangle(x,y,size/2,size);
                rectangle.setFill(Color.FIREBRICK);
                game.getChildren().add(rectangle);
            }
            case WATR -> {
                Circle circle = new Circle(x,y,size,Color.FIREBRICK);
                game.getChildren().add(circle);
            }
        }
    }
    private void makeScoreboard() {
        // Each player
        Board board = this.model.getBoard();
        int numberOfPlayers = model.numOfPlayers;

        // Row Labels
        Text playerT = new Text(10, 40, "Player: ");
        Text islandsT = new Text(10, 60, "7/8 Islands:");
        Text majoritiesT= new Text(10, 80, "Majority Islands:");
        Text linksT = new Text(10, 100, "Links:");
        Text resourcesT = new Text(10, 120, "Total Resources:");
        Text totalT = new Text(10, 140, "Total Points:");
        ArrayList<Text> textsL = new ArrayList<>(Arrays.asList(playerT,islandsT,majoritiesT,linksT,resourcesT,totalT));
        game.getChildren().addAll(textsL);
        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerPointCounter pointCounter = new PlayerPointCounter(i, Board.tiles, board.numOfIslands);

            // Scores
            Text scoreBoard = new Text(130/2., 20, "ScoreBoard");
            Text player = new Text(50 * i + 120, 40, ""+ i);
            Text islands = new Text(50 * i + 120, 60, "" + pointCounter.islandsCounter());
            Text majorityIslands = new Text(50 * i+ 120, 80, "" + pointCounter.majorityIslandsCounter(board.islandToPoints));
            Text links = new Text(50 * i + 120, 100, "" + pointCounter.linkCounter());
            Text resources = new Text(50 * i + 120, 120, "" + board.resourcesPoints(i));
            Text total = new Text(50 * i + 120, 140, "" +board.countPoints(i));

            game.getChildren().addAll(new ArrayList<>(Arrays.asList(scoreBoard,player,islands,majorityIslands,links,resources,total)));
        }
    }

    private void makeGameTokens(int phase) {
        // tokens already on board
        SettlerPiece settlerToken = new SettlerPiece(0, this.model);
        SettlerPiece villagePiece = new SettlerPiece(1, this.model);
        // Placeable tokens
        if (phase == 0) {
            game.getChildren().addAll(settlerToken,villagePiece);
        } else game.getChildren().addAll(settlerToken);



    }

    private void makeCurrentInventory(int gameState) {
        Board board = this.model.getBoard();
        Board.Player currentPlayer = board.getPlayer(model.currentPlayer);
        Text title = new Text(150/2., 400, "Inventory: Player " + currentPlayer.getId());
        Text phase = new Text(150/4., 380, "PHASE: " + gameState);
        game.getChildren().addAll(title,phase);

        Text settlerCount = new Text(10, 420, "Settlers: " + (30 - currentPlayer.getSettlers()) + " Left");
        Text villagerCount = new Text(10 + 100, 420, "Villagers: " + (5 - currentPlayer.getVillages()) + " Left");

        if (gameState == 0) {
            game.getChildren().addAll(new Text[]{villagerCount,settlerCount});
        } else game.getChildren().addAll(new Text[]{settlerCount});

    }

    private void updateGUI() {
        if (model.gamestate == 0) {
            if (model.checkEnd(0)) {
                Alert endedPhase = new Alert(Alert.AlertType.INFORMATION);
                endedPhase.setTitle("Exploration PHASE ENDED");
                endedPhase.setHeaderText("Player with most points: PLAYER " + model.board.declareWinner().getId());
                endedPhase.setContentText("Total Points: " + model.board.declareWinner().getPoints());
                endedPhase.show();
                this.model.reset();
            } else {
                this.model.advancePlayer();

            }
            game.getChildren().clear();
            makeState(model.gamestate);
        } else {
            if (model.checkEnd(1)) {
                this.model.reset();
                Alert winner = new Alert(Alert.AlertType.INFORMATION);
                winner.setTitle("SETTLEMENT PHASE ENDED");
                winner.setHeaderText("WINNER: PLAYER:  " + model.board.declareWinner().getId());
                winner.setContentText("Total Points: " + model.board.declareWinner().getPoints());
                winner.show();


            } else {
                this.model.advancePlayer();
                game.getChildren().clear();
                makeState(model.gamestate);
            }

        }
    }

    private void newGame(String stateString) {
        CURRENT_GAME = stateString;
        game.getChildren().clear();
        this.setModel(CURRENT_GAME);
        makeState(model.gamestate);

    }


    private void makeState(int phase) {

        makeBoard();
        makeScoreboard();
        makeResources();
        makeCurrentInventory(phase);
        makeGameTokens(phase);
    }

    class SettlerPiece extends Group {

        SettlerToken settler;

        Integer village;

        double mouseX, mouseY;
        double homeX;
        double homeY;

        public SettlerPiece(Integer village, Model model) {
            this.village = village;
            this.homeX = 20;
            if (village == 1) {this.homeX += 100;}
            this.homeY = 470;

            if (village == 1) {this.settler = new SettlerToken("village.png");}
            else {this.settler = new SettlerToken("settler.png");}

            this.getChildren().add(this.settler);
            this.setOnMousePressed(event -> {
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
            });

            this.setOnMouseDragged(event -> {
                /*
                 Move the caterpillar by the difference in mouse position
                 since the last drag.
                 */
                double diffX = event.getSceneX() - mouseX;
                double diffY = event.getSceneY() - mouseY;
                this.setLayoutX(this.getLayoutX() + diffX);
                this.setLayoutY(this.getLayoutY() + diffY);
                /*
                 Update `mouseX` and `mouseY` and repeat the process.
                 */
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
            });

            this.setOnMouseReleased(
                    event -> {
                        int[] pos = getSnapPosition();
                        // check if it is a valid move
                        System.out.println(model.isMoveValid(pos[1], pos[0], village));
                        if (model.isMoveValid(pos[1], pos[0], village)) {
                            System.out.println("update gui");
                            model.setSettler(pos[1], pos[0], village);
                            updateGUI();
                        }
                        this.setLocation(pos, village);
                    });
            this.snapToHome();
        }
// get the resource coordinate in terms of (0,0)
        public int[] getSnapPosition() {
            int x;
            int y = (int) Math.round((this.getLayoutY() - MARGIN_Y) / (TILE_SPACING_Y * SIZING_RATIO));
            if (y % 2 == 0) {
                x = (int) Math.round((this.getLayoutX() - MARGIN_X - OFFSET * SIZING_RATIO) / (TILE_SPACING_X* SIZING_RATIO));
            } else {
                x = (int) Math.round((this.getLayoutX() - MARGIN_X) / (TILE_SPACING_X*SIZING_RATIO));
            }

            return new int[]{x, y};
        }

        public void setLocation(int[] position, int piece) {
            // Position is not on the board
            if (model.getBoard().outOfBounds(position) || !model.isMoveValid(position[0],position[1],piece)) {
                this.snapToHome();
            } else {
                this.setLayoutY(MARGIN_Y + position[1] * TILE_SPACING_Y * SIZING_RATIO);
                if (position[1] % 2 == 0) {
                    this.setLayoutX(OFFSET * SIZING_RATIO + MARGIN_X + (position[0] * TILE_SPACING_X * SIZING_RATIO));
                } else {
                    this.setLayoutX(MARGIN_X + (position[0] * TILE_SPACING_X * SIZING_RATIO));
                }
            }
        }

        private void snapToHome() {
            this.setLayoutX(this.homeX);
            this.setLayoutY(this.homeY);
        }
    }
    static class SettlerToken extends ImageView {
        public SettlerToken(String filename) {
            Image image = new Image(Game.class.getResource( URI_BASE  + filename).toString());
            this.setImage(image);
            this.setFitWidth(65 * SIZING_RATIO);
            this.setFitHeight(89 * SIZING_RATIO);
            this.setPreserveRatio(true);
        }
    }


private void chooseGame(String game) {
        switch (game) {
            case "DEFAULT_GAME" -> {
                newGame(BlueLagoon.DEFAULT_GAME);
            }

            case "FACE_GAME" -> {
                newGame(BlueLagoon.FACE_GAME);
            }

            case "SIDES_GAME" -> {
                newGame(BlueLagoon.SIDES_GAME);
            }

            case "SPACE_INVADERS_GAME" -> {
                newGame(BlueLagoon.SPACE_INVADERS_GAME);
            }

            case "WHEELS_GAME" -> {
                newGame(BlueLagoon.WHEELS_GAME);
            }

        }

}





    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.game, WINDOW_WIDTH, WINDOW_HEIGHT);
        Scene scene2 = new Scene(this.menu,WINDOW_WIDTH,WINDOW_HEIGHT);
        Scene scene3 = new Scene(this.selectGame,WINDOW_WIDTH,WINDOW_HEIGHT);

        Button button = new Button("New Game?");
        button.setLayoutX(WINDOW_WIDTH/2);
        button.setLayoutY(WINDOW_HEIGHT/2);
        button.setOnAction(event -> {
            // default
            String[] boards = new String[]{"DEFAULT_GAME","FACE_GAME","SIDES_GAME","SPACE_INVADERS_GAME","WHEELS_GAME"};
            int spacing = 200;
            for (int i = 0; i < boards.length; i++) {
                String board = boards[i];
                String path = URI_BASE + board + ".png";


                Image image = new Image(getClass().getResource(path).toString());
                ImageView gameBoard = new ImageView(image);
                gameBoard.setFitWidth(spacing);
                gameBoard.setFitHeight(spacing);
                gameBoard.setLayoutX((WINDOW_WIDTH/10) + (i * spacing));
                gameBoard.setLayoutY(WINDOW_HEIGHT/2);
                selectGame.getChildren().add(gameBoard);

                gameBoard.setOnMouseClicked( (MouseEvent e) -> {
                    chooseGame(board);
                    stage.setScene(scene);
                });


            }
            // face

            // sides

            // space_invaders






            stage.setScene(scene3);

        });

        menu.getChildren().add(button);





        // START GAME IS ALWAYS DEFAULT_GAME
        newGame(BlueLagoon.DEFAULT_GAME);
        // Debug current State as a stateString
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.D) {
                System.out.println(model.toStateString());
            }

            // Create a new Game when you press N on Keyboard of current board
            if (e.getCode() == KeyCode.N) {
                newGame(CURRENT_GAME);
            }

            // switch boards
            if (e.getCode() == KeyCode.DIGIT1) {
                newGame(BlueLagoon.DEFAULT_GAME);
            }
            if (e.getCode() == KeyCode.DIGIT2) {
                newGame(BlueLagoon.WHEELS_GAME);
            }

            if (e.getCode() == KeyCode.DIGIT3) {
                newGame(BlueLagoon.SIDES_GAME);
            }

            if (e.getCode() == KeyCode.DIGIT4) {
                newGame(BlueLagoon.SPACE_INVADERS_GAME);
            }
            if (e.getCode() == KeyCode.DIGIT5) {
                newGame(BlueLagoon.FACE_GAME);
            }
// quit back to menu
            if (e.getCode() == KeyCode.ESCAPE) {
                stage.setScene(scene2);
            }





        });


        stage.setScene(scene2);
        stage.show();
    }
}
