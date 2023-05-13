package comp1110.ass2.gui;

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
    
    private final Group game = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final String URI_BASE = "Resources/";
    private static final int MARGIN_X = 250;
    private static final int MARGIN_Y = 40;
    private static final double TILE_SPACING_X = 60.0;
    private static final double OFFSET = TILE_SPACING_X/2.0;
    private static final double TILE_SPACING_Y = 42.0;
    private Model model;
    public static final String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";

    // Use a state-string to initialise the game.
    private void setModel(String statestring) {
        this.model = new Model();
        this.model.toModel(statestring);
        List<Board.Tile> stoneCoords = this.model.board.getStoneRsrcTiles();
        this.model.board.assignRanResources(stoneCoords);
    }

    // make the elements of the board, such as the islands, stones, resources, etc.
    private void makeBoard() {
        int boardSize = this.model.getBoard().boardSize;
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
                tileImage.setLayoutX((col * TILE_SPACING_X) + (MARGIN_X) + var * OFFSET);
                tileImage.setLayoutY((row * TILE_SPACING_Y) + (MARGIN_Y));
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
                    boardX = (col * TILE_SPACING_X) + TILE_SPACING_X/2 + OFFSET + MARGIN_X;
                }
                else {
                    boardX = (col * TILE_SPACING_X) + TILE_SPACING_X/2 + MARGIN_X;
                }
                boardY = row * TILE_SPACING_Y + 3*TILE_SPACING_Y/4 + MARGIN_Y;
                resourceToShape(boardX,boardY,resource);
            }
        }
    }

    public void resourceToShape(double x, double y, Board.Tile.Resource resource) {
        switch (resource) {
            case STON -> {
                Rectangle rectangle = new Rectangle(x,y,10,20);
                rectangle.setFill(Color.GREEN);
                game.getChildren().add(rectangle);
            }
            case COCO -> {
                Circle circle = new Circle(x,y,20,Color.WHITE);
                game.getChildren().add(circle);
            }
            case BBOO -> {
                Rectangle rectangle = new Rectangle(x,y,10,20);
                rectangle.setFill(Color.YELLOW);
                game.getChildren().add(rectangle);
            }
            case STAT -> {
                Rectangle rectangle = new Rectangle(x,y,10,20);
                rectangle.setFill(Color.FIREBRICK);
                game.getChildren().add(rectangle);
            }
            case WATR -> {
                Circle circle = new Circle(x,y,20,Color.FIREBRICK);
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
            Text player = new Text(50 * i + 120, 40, String.valueOf(i));
            Text islands = new Text(50 * i + 120, 60, String.valueOf(pointCounter.islandsCounter()));
            Text majorityIslands = new Text(50 * i+ 120, 80, String.valueOf(pointCounter.majorityIslandsCounter(board.islandToPoints)));
            Text links = new Text(50 * i + 120, 100, String.valueOf(pointCounter.linkCounter()));
            Text resources = new Text(50 * i + 120, 120, String.valueOf(board.resourcesPoints(i)));
            Text total = new Text(50 * i + 120, 140, String.valueOf(board.countPoints(i)));

            game.getChildren().addAll(new ArrayList<>(Arrays.asList(scoreBoard,player,islands,majorityIslands,links,resources,total)));
        }
    }

    private void makeGameTokens(int phase) {
        // tokens already on board
        Piece settlerToken = new Piece(0, this.model);
        Piece villagePiece = new Piece(1, this.model);
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

        Text villagerCount = new Text(10, 420, "Settlers: " + (30 - currentPlayer.getSettlers()) + " Left");
        Text settlerCount = new Text(10 + 100, 420, "Villagers: " + (5 - currentPlayer.getVillages()) + " Left");

        if (gameState == 0) {
            game.getChildren().addAll(new Text[]{villagerCount,settlerCount});
        } else game.getChildren().addAll(new Text[]{villagerCount});

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

    private void newGame() {

        game.getChildren().clear();
        this.setModel(DEFAULT_GAME);
        makeState(model.gamestate);

    }

    private void makeState(int phase) {
        makeScoreboard();
        makeBoard();
        makeResources();
        makeCurrentInventory(phase);
        makeGameTokens(phase);
    }

    class Piece extends Group {

        ImageToken settler;

        Integer village;

        double mouseX, mouseY;
        double homeX;
        double homeY;

        public Piece(Integer village, Model model) {
            this.village = village;
            this.homeX = 20;
            if (village == 1) {this.homeX += 100;};
            this.homeY = 470;

            if (village == 1) {this.settler = new ImageToken("village.png");}
            else {this.settler = new ImageToken("settler.png");}

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
            int y = (int) Math.round((this.getLayoutY() - MARGIN_Y) / TILE_SPACING_Y);
            if (y % 2 == 0) {
                x = (int) Math.round((this.getLayoutX() - MARGIN_X - OFFSET) / TILE_SPACING_X);
            } else {
                x = (int) Math.round((this.getLayoutX() - MARGIN_X) / TILE_SPACING_X);
            }

            return new int[]{x, y};
        }

        public void setLocation(int[] position, int piece) {
            // Position is not on the board
            if (model.getBoard().outOfBounds(position) || !model.isMoveValid(position[0],position[1],piece)) {
                this.snapToHome();
            } else {
                this.setLayoutY(MARGIN_Y + position[1] * TILE_SPACING_Y);
                if (position[1] % 2 == 0) {
                    this.setLayoutX(OFFSET + MARGIN_X + (position[0] * TILE_SPACING_X));
                } else {
                    this.setLayoutX(MARGIN_X + (position[0] * TILE_SPACING_X));
                }
            }
        }

        private void snapToHome() {
            this.setLayoutX(this.homeX);
            this.setLayoutY(this.homeY);
        }
    }
    static class ImageToken extends ImageView {
        public ImageToken(String filename) {
            Image image = new Image(Game.class.getResource( URI_BASE  + filename).toString());
            this.setImage(image);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.game, WINDOW_WIDTH, WINDOW_HEIGHT);
        newGame();
        // Debug current State as a stateString
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.D) {
                System.out.println(model.toStateString());
            }

            // Create a new Game when you press N on Keyboard
            if (e.getCode() == KeyCode.N) {
                newGame();
            }
        });


        stage.setScene(scene);
        stage.show();
    }
}
