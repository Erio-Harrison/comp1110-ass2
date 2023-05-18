package comp1110.ass2.gui;

import comp1110.ass2.BlueLagoon;
import comp1110.ass2.Board;
import comp1110.ass2.Model;
import comp1110.ass2.PlayerPointCounter;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


// Authored By Jonathan Tay and Kenney Siu

public class Game extends Application {

/*    Different Groups represent our different stages that we will use,
      such as the actual game, the menu content and the game selection content.
     */
    private final Group game = new Group();
    private final Group menu = new Group();

    private final Group selectGame = new Group();

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    // Used to access our game assets
    private static final String URI_BASE = "Resources/";
    // Margins on of the actual game board
    private static final int MARGIN_X = 350;
    private static final int MARGIN_Y = 40;
    // Spacing between tiles
    private static final double TILE_SPACING_X = 60.0;
    // Even row tiles are offset by half the tile size
    private static final double OFFSET = TILE_SPACING_X/2.0;
    private static final double TILE_SPACING_Y = 42.0;
    // instance of our blueLagoon Game
    private Model model;
    // check if our game is versus AI
    private static boolean ai_mode = false;
    // Default board size used to compare against different board sizes
    private static final double DEFAULT_BOARD = 13;
    // How much our board should be reduced by or increased by
    private static double SIZING_RATIO;

    // This is only to display what the current board layout is being used
    private static String CURRENT_GAME;

    // Use a state-string to initialise the model (game) instance.
    private void setModel(String statestring) {
        this.model = new Model();
        this.model.toModel(statestring);
        List<Board.Tile> stoneCoords = this.model.board.getStoneRsrcTiles();
        this.model.board.assignRanResources(stoneCoords);
    }

    // make the elements of the board, such as the islands, stones, resources, etc.
    private void makeBoard() {
        int boardSize = this.model.board.boardSize;
        Board.Tile[][] tiles = this.model.board.tiles;
        SIZING_RATIO = DEFAULT_BOARD / boardSize;
        for (int row = 0; row < boardSize; row++) {
            int var = 0;
            if (row % 2 == 0) {
                var = 1;
            }
            for (int col = 0; col < boardSize - var; col++) {
                Board.Tile curr = tiles[row][col];
                String path = URI_BASE + curr.toURL();
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
        ArrayList<int[]> stoneCoords = model.board.gettiles(1, 0, 0);
        for (int[] coords : stoneCoords) {
            int row = coords[0];
            int col = coords[1];
            double boardX;
            double boardY;
            Board.Tile.Resource resource = this.model.board.tiles[row][col].resource;
            if (resource != null) {
                if (row % 2 == 0) {
                    boardX = (col * TILE_SPACING_X * SIZING_RATIO) + TILE_SPACING_X/2 * SIZING_RATIO + OFFSET * SIZING_RATIO + MARGIN_X;
                } else boardX = (col * TILE_SPACING_X * SIZING_RATIO) + TILE_SPACING_X/2 * SIZING_RATIO + MARGIN_X;
                boardY = row * TILE_SPACING_Y * SIZING_RATIO + 3*TILE_SPACING_Y/4 * SIZING_RATIO + MARGIN_Y;
                resourceToShape(boardX,boardY,resource, 20 * SIZING_RATIO);

            }
        }
    }

    // Method that takes our recourses into shapes to display on board
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

    // method to display players and their scores

    private void makeScoreboard() {
        // Each player
        Board board = this.model.board;
        int numberOfPlayers = model.numOfPlayers;

        // Row Labels
        Text playerT = new Text(10, 40, "Player: ");
        Text islandsT = new Text(10, 60, "7/8 Islands:");
        Text majoritiesT= new Text(10, 80, "Majority Islands:");
        Text linksT = new Text(10, 100, "Links:");
        Text resourcesT = new Text(10, 120, "Total Resources:");
        Text phaseP = new Text(10, 140, "Points this phase:");
        Text totalP = new Text(10, 160, "Total Points:");
        ArrayList<Text> textsL = new ArrayList<>(Arrays.asList(playerT,islandsT,majoritiesT,linksT,resourcesT,phaseP,totalP));
        game.getChildren().addAll(textsL);
        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerPointCounter pointCounter = new PlayerPointCounter(i, this.model.board.tiles, board.numOfIslands);

            // Scores
            Text scoreBoard = new Text(130/2., 20, "ScoreBoard");
            Text player = new Text(50 * i + 120, 40, String.valueOf(i));
            Text islands = new Text(50 * i + 120, 60, String.valueOf(pointCounter.islandsCounter()));
            Text majorityIslands = new Text(50 * i+ 120, 80, String.valueOf(pointCounter.majorityIslandsCounter(board.islandToPoints)));
            Text links = new Text(50 * i + 120, 100, String.valueOf(pointCounter.linkCounter()));
            Text resources = new Text(50 * i + 120, 120, String.valueOf(board.resourcesPoints(i)));
            Text phase = new Text(50 * i + 120, 140, String.valueOf(board.countPoints(i)));
            Text total = new Text(50 * i + 120, 160, String.valueOf(board.playerList.get(i).points));
            game.getChildren().addAll(new ArrayList<>(Arrays.asList(scoreBoard,player,islands,majorityIslands,links,resources,phase,total)));
        }
    }

    // Make our Settlers and villages for the current player
    private void makeGameTokens(int phase) {
        // tokens already on board
        Piece settlerToken = new Piece(0, this.model,ai_mode);
        Piece villagePiece = new Piece(1, this.model,ai_mode);
        // Placeable tokens
        if (phase == 0) {
            game.getChildren().addAll(settlerToken,villagePiece);
        } else game.getChildren().addAll(settlerToken);
    }

    // Shows how many settlers and villages are left for the current players turn, as well as displays the phase.
    private void makeCurrentInventory(int gameState) {
        String gameStateText = "Exploration";
        if (gameState == 1) gameStateText = "Settlement";
        Board board = this.model.board;
        Board.Player currentPlayer = board.getPlayer(model.currentPlayer);
        Text title = new Text(150/2., 400, "Inventory: Player " + currentPlayer.id);
        Text phase = new Text(150/4., 380, "PHASE: " + gameStateText);
        game.getChildren().addAll(title,phase);
        Text settlerCount = new Text(10, 420, "Settlers: " + (30 - currentPlayer.settlers) + " Left");
        Text villagerCount = new Text(10 + 100, 420, "Villagers: " + (5 - currentPlayer.villages) + " Left");


        if (gameState == 0) {
            game.getChildren().addAll(new Text[]{villagerCount,settlerCount});
        } else game.getChildren().addAll(new Text[]{settlerCount});
    }

    // Remake the current state as well as display when the current pahse is over.
    private void updateGUI(int num) {
        if (model.gamestate == 0) {
            if (num == 1) {
                Alert endedPhase = new Alert(Alert.AlertType.INFORMATION);
                endedPhase.setTitle("Exploration PHASE ENDED");
                endedPhase.setHeaderText("Highest Points: PLAYER " + model.board.getIds(model.board.declareWinner()));
                endedPhase.setContentText("Total Points: " + model.board.getAllPoints(model.board.declareWinner()));
                endedPhase.show();
                this.model.reset();
                this.model.changeState();
            }
        } else {
            if (num == 1) {
                Alert winner = new Alert(Alert.AlertType.INFORMATION);
                winner.setTitle("PHASE OVER");
                winner.setHeaderText("WINNER PLAYER:  " + model.board.getIds(model.board.declareWinner()));
                winner.setContentText("Total Points: " + model.board.getAllPoints(model.board.declareWinner()));
                winner.show();
            }
        }
        game.getChildren().clear();
        makeState(model.gamestate);
    }


    // restart the game and shows the current game again.
    private void newGame(String stateString) {
        CURRENT_GAME = stateString;
        game.getChildren().clear();
        this.setModel(CURRENT_GAME);
        makeState(model.gamestate);

    }

    // Makes the state of the Game displayable, current players, boards, resources, settlers and village pieces
    private void makeState(int phase) {
        makeBoard();
        makeScoreboard();
        makeResources();
        makeCurrentInventory(phase);
        makeGameTokens(phase);
        String gameMode = "2 Player";
        if (ai_mode) {
            gameMode = "Versus AI";
        }
        Text text = new Text(1050,20,"Game Mode: " + gameMode);

        Button button2 = new Button("Controls");
        button2.setLayoutX(WINDOW_WIDTH- 100);
        button2.setLayoutY(WINDOW_HEIGHT - 50);
        button2.setOnAction(event -> {
            controlInstructions();
        });
        game.getChildren().addAll(button2,text);
    }

    /*
    A class defining the settler and village pieces in the front end,
    these are draggable and droppable pieces that will update our backend when played.
     */
    class Piece extends Group {

        // Image of our piece
        ImageToken settler;
        // used to check if our game is versus AI
        boolean ai;
        // the current players piece
        int currentPlayer = model.currentPlayer;
        // check if it is a village or settler
        Integer village;

        // mouse coordinates
        double mouseX, mouseY;
        // default positions of the Piece
        double homeX;
        double homeY;


        // constructor that sets where the piece is in the players inventory,
        // implements drag and drop methods and piece playing,
        // the main functionality of our game.
        public Piece(Integer village, Model model, boolean ai) {
            // what kind of piece it is.
            this.village = village;
            // default locations in the players inventory, depending on whether
            // it's a villager or settler.
            this.homeX = 20;
            if (village == 1) {this.homeX += 100;}
            this.homeY = 470;
            this.ai = ai;

            // Displayable images of the pieces
            if (village == 1) {this.settler = new ImageToken("village" + currentPlayer + ".png");}
            else {this.settler = new ImageToken("settler" + currentPlayer +  ".png");}

            this.getChildren().add(this.settler);
            // Event handler if the user clicks on this piece
            this.setOnMousePressed(event -> {
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
            });


            // Event if the user drags this piece
            this.setOnMouseDragged(event -> {
                /*
                 Move the Piece by the difference in mouse position
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

            // If the user drops the piece
            this.setOnMouseReleased(
                    event -> {
                        int[] pos = getSnapPosition();
                        // check if it is a valid move, if it is play the piece as well as changes the turn of the player
                        if (model.board.isValidMove(pos[1], pos[0],model.currentPlayer,village, model.gamestate)) {
                            var num = model.applyMove(pos[1], pos[0], village);
                            // update the entire model to match the updated game
                            updateGUI(num);
                            if (ai && currentPlayer != model.currentPlayer) {
                                AIGame(model.currentPlayer);
                            }
                        }
                        // set location of the piece in game
                        this.setLocation(pos, village);
                    });
            // set default location of piece
            this.snapToHome();
        }
        // Translates the current piece cooridinates in the window in terms of the game board position
        // e.g. (0,0)
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
        // Set the location of the piece on the game window.
        public void setLocation(int[] position, int piece) {
            // Position is not on the board
            if (!model.board.isValidMove(position[0], position[1],model.currentPlayer, piece, model.gamestate)) {
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
        // set the piece on the default coordinates
        private void snapToHome() {
            this.setLayoutX(this.homeX);
            this.setLayoutY(this.homeY);
        }
    }
    // Class that makes our various images (pieces and tiles) displayable,
    // the size of our images correlates
    // how big the game board is.
    static class ImageToken extends ImageView {
        public ImageToken(String filename) {
            Image image = new Image(Game.class.getResource( URI_BASE  + filename).toString());
            this.setImage(image);
            this.setFitWidth(60 * SIZING_RATIO);
            this.setFitHeight(89 * SIZING_RATIO);
            this.setPreserveRatio(true);

        }
    }


    // Method that switches the board layouts
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

    // makes the game menu (only displays the title)
    private void makeMenu() {
        String path = URI_BASE + "menu_logo.png";
        Image image = new Image(getClass().getResource(path).toString());
        ImageView title = new ImageView(image);
       title.setFitHeight(600);
        title.setFitWidth(600);
        title.setPreserveRatio(true);
        title.setLayoutX(300);
        title.setLayoutY(0);
        menu.getChildren().add(title);
    }


    // displays controls for the game
    private void controlInstructions() {
        Alert winner = new Alert(Alert.AlertType.INFORMATION);
        winner.setTitle("controls");
        winner.setHeaderText("Controls");
        winner.setContentText(" \nIn game, make a move by dragging and dropping a piece onto the board " +
                " \nIn game, quit using Q " +
                " \nIn game create a new game using N " +
                " \nIn game Choose different boards in game using Number Keys, there are 5 game layouts to choose " +
                " \nIf you want the AI to choose a move do so by pressing A in the game on the current turn");

        winner.show();
    }

// Gets the move from the AI, plays it and updates the game's state
    public void AIGame(int player) {
        if (model.currentPlayer == player) {
            String move = model.decisionMaker();
            int row = Integer.parseInt(String.valueOf(move.charAt(2)));
            int col = Integer.parseInt(String.valueOf(move.charAt(4)));
            int piece = 0;

            if (move.charAt(0) == 'T') {
                piece = 1;
            }


            var num = model.applyMove(row,col, piece);
            updateGUI(num);
        }



    }

    //
    @Override
    public void start(Stage stage) throws Exception {
        // different scenes for our menu, game and selectGame Groups
        Scene scene = new Scene(this.game, WINDOW_WIDTH, WINDOW_HEIGHT);
        Scene scene2 = new Scene(this.menu,WINDOW_WIDTH,WINDOW_HEIGHT);
        Scene scene3 = new Scene(this.selectGame,WINDOW_WIDTH,WINDOW_HEIGHT);

        makeMenu();
        // button that when clicked shows our controls instructions in the menu
        Button button2 = new Button("Controls");
        button2.setLayoutX(WINDOW_WIDTH/2);
        button2.setLayoutY(WINDOW_HEIGHT/2 + 100);
        button2.setOnAction(event -> {
            controlInstructions();
        });

        // Button that if clicked changes the mode so user plays against AI
        AtomicReference<String> onOFF = new AtomicReference<>("OFF");
        Button aiButton = new Button("AI Mode: " + onOFF);
        aiButton.setLayoutX(WINDOW_WIDTH/2.);
        aiButton.setLayoutY(WINDOW_HEIGHT/2. + 150);

        aiButton.setOnAction(event -> {
            ai_mode = !ai_mode;
            if (ai_mode) {
                onOFF.set("ON");
            } else onOFF.set("OFF");
            aiButton.setText("AI Mode: " + onOFF);
        });

        // Button that makes it so player can choose game
        Button button = new Button("New Game?");
        button.setLayoutX(WINDOW_WIDTH/2);
        button.setLayoutY(WINDOW_HEIGHT/2 + 50);
        button.setOnAction(event -> {

            // Displays our board layouts that when clicked starts a game with that board
            Text selectGameText = new Text(WINDOW_WIDTH/2.5,WINDOW_HEIGHT/2, "CHOOSE BOARD LAYOUT");
            selectGame.getChildren().add(selectGameText);
            // default
            String[] boards = new String[]{"DEFAULT_GAME","FACE_GAME","SIDES_GAME","SPACE_INVADERS_GAME","WHEELS_GAME"};
            int spacing = 200;
            for (int i = 0; i < boards.length; i++) {
                String board = boards[i];
                String path = URI_BASE + board + ".png";


                // Adds all our board images to the selectGame group
                Image image = new Image(getClass().getResource(path).toString());
                ImageView gameBoard = new ImageView(image);
                gameBoard.setFitWidth(spacing);
                gameBoard.setFitHeight(spacing);
                gameBoard.setLayoutX((WINDOW_WIDTH/10) + (i * spacing));
                gameBoard.setLayoutY(WINDOW_HEIGHT/2);
                selectGame.getChildren().add(gameBoard);

                // clicking on the image will start that game
                gameBoard.setOnMouseClicked( (MouseEvent e) -> {
                    chooseGame(board);
                    stage.setScene(scene);
                });


            }





            // sets the secne to our selectGame group when the new game button is clicked
            stage.setScene(scene3);



        });


        menu.getChildren().addAll(button,button2,aiButton);




        // Start Game will always be set to default game
        newGame(BlueLagoon.DEFAULT_GAME);

        // Debug current State as a stateString
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.D) {
                System.out.println(model.toStateString());
            }

            // Create a new Game when you press N on Keyboard
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
            // let ai make a move (testing Purposes)
            if (e.getCode() == KeyCode.A) {
                AIGame(model.currentPlayer);
            }

            // quit to menu in game
            if (e.getCode() == KeyCode.Q) {
                stage.setScene(scene2);
            }





        });

        // when started, menu is displayed first
        stage.setScene(scene2);
        stage.show();
    }
}
