package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class Model {

    // the num of players in the game
    public int numOfPlayers;

    // exploration(0) or settling(1) phase
    public int gamestate;

    //current player
    public int currentPlayer;

    //Board
    public Board board;

    public Model() {
    }

    /**
     * Changes the gamestate from 0 to 1 or from 1 to 0.
     */
    public void changeState() {
        switch (gamestate) {
            case (0) -> {gamestate = 1;}
            case (1) -> {gamestate = 0;}
        }
    }

    /**
     *  Takes a statestring converts its data into attributes for the model
     *  @param stateString a string representing a game state
     */
    public void toModel(String stateString) {
        String[] stateArray = stateString.split("; |;");
        //==gameArrangement==
        String[] gameArrangeStatement = stateArray[0].split(" ");
        this.numOfPlayers = Integer.parseInt(gameArrangeStatement[2]);
        this.board = new Board( Integer.parseInt(gameArrangeStatement[1]));

        //==currentState==
        String[] currentStateStatement = stateArray[1].split(" ");
        this.currentPlayer = Integer.parseInt(currentStateStatement[1]);
        String phase = (currentStateStatement[2]);
        if (phase.equals("E")) {
            this.gamestate = 0;
        }
        else if (phase.equals("S")) {
            this.gamestate = 1;
        }

        //==islands==
        int i = 2;
        ArrayList islandState = (ArrayList)
                Arrays.stream(stateArray).collect(
                        Collectors.partitioningBy(n -> n.charAt(0) == 'i')).values().toArray()[1];
        // setting islands on board
        int islandNum = 0;
        for (Object j : islandState) {
            islandNum += 1;
            this.board.setBoardAttributes((String) j, 4, 0 );
            this.board.setBoardAttributes((String) j, 3, islandNum );
        }
        this.board.numOfIslands = islandNum;

        //==stones==
        i += islandState.size();
        this.board.setBoardAttributes(stateArray[i], 0, 0 );

        //==unclaimed resources statement==
        this.board.setBoardAttributes( stateArray[i + 1], 1, 0 );

        //==player statement==
        for (int k = i + 2; k < stateArray.length; k ++) {
            int id  = Character.getNumericValue(stateArray[k].charAt(2));
            this.board.setBoardAttributes(stateArray[k], 2, id);
        }
    }

    /**
     * Converts the model into a stateString
     * @return stateString of the model
     */
    public String toStateString() {
        String state;
        if (this.gamestate == 0) {state = "E";}
        else {state = "S";}

        List<List<String>> islands = new ArrayList<>();
        for (var i = 0; i < this.board.numOfIslands; i ++) {islands.add(new ArrayList<>());}

        List<String> stones = new ArrayList<>();

        List<List<String>> resources = new ArrayList<>();
        String[] resourceChar = {"C", "B", "W", "P", "S"};
        for (var i = 0; i < 5; i ++) {
            List<String> currentAL = new ArrayList<>();
            currentAL.add(resourceChar[i]);
            resources.add(currentAL);
        }

        //player statement
        List<List<String>> players = new ArrayList<>();
        List<List<String>> playerSettler = new ArrayList<>();
        List<List<String>> playerVillages = new ArrayList<>();
        for (int p = 0; p < this.numOfPlayers; p ++) {
            players.add(new ArrayList<>());
            playerSettler.add(new ArrayList<>());
            playerVillages.add(new ArrayList<>());
        }

        for (var player: this.board.playerList) {
            players.get(player.id).add("p");
            players.get(player.id).add(String.valueOf(player.id));
            players.get(player.id).add(String.valueOf(player.points));
            for (var r: player.resources) {players.get(player.id).add(String.valueOf(r));}
        }

        // runs through every tile on the board
        for (int row = 0; row < this.board.boardSize; row++) {
            var len = 0;
            if (row % 2 == 0) {len = -1;}
            for (int col = 0; col < this.board.boardSize + len; col++) {
                if (this.board.tiles[row][col].island != 0) {
                    islands.get(this.board.tiles[row][col].island - 1).add(row + "," + col);}

                if (this.board.tiles[row][col].isStoneCircle) {stones.add(row + "," + col);}

                if (this.board.tiles[row][col].resource != null) {
                    resources.get(Board.Tile.resourceToInt(this.board.tiles[row][col].resource)).add(row + "," + col);}
                if (this.board.tiles[row][col].occupier != -1) {
                    if (this.board.tiles[row][col].village == 1) {
                        List<String> currentList = playerVillages.get(this.board.tiles[row][col].occupier);
                        currentList.add(row + "," + col);
                    }
                    if (this.board.tiles[row][col].village == 0) {
                        List<String> currentList = playerSettler.get(this.board.tiles[row][col].occupier);
                        currentList.add(row + "," + col);
                    }
                }
            }
        }
        // constructs the statement
        String gameAStatement = "a " + this.board.boardSize + " " + this.numOfPlayers + ";";

        String csStatement = "c " + this.currentPlayer + " " + state + ";";

        StringBuilder stoneStatement = new StringBuilder("s");
        for (var k: stones) {stoneStatement.append(" ").append(k);}
        stoneStatement.append(";");

        StringBuilder islandStatement = new StringBuilder();
        int i = 0;
        for (var k: islands) {
            i += 1;
            StringBuilder currentIS = new StringBuilder("i " + this.board.islandToPoints.get(i - 1));
            for (var t: k) {currentIS.append(" ").append(t);}
            islandStatement.append(currentIS).append("; ");
        }

        StringBuilder resStatement = new StringBuilder("r");
        for (var k: resources) {
            for (String s: k) {resStatement.append(" ").append(s);}}
        resStatement.append(";");

        StringBuilder playerStatement = new StringBuilder();
        for (int k = 0; k < players.size(); k++) {
            List<String> player = players.get(k);
            if (k > 0) {playerStatement.append(" ");}
            for (var t: player) {playerStatement.append(t).append(" ");}
            playerStatement.append("S ");

            for (var t: playerSettler.get(k)) {playerStatement.append(t).append(" ");}
            playerStatement.append("T");

            for (var t: playerVillages.get(k)) {playerStatement.append(" ").append(t);}
            playerStatement.append(";");

        }
        return gameAStatement + " " + csStatement + " " + islandStatement + stoneStatement + " " + resStatement + " " + playerStatement;
    }

    /**
     *  Sets a piece at a given coordinate.
     *  @param row - row coordinate of piece
     *  @param col - col coordinate of piece
     *  @param piece - (0 indicates settler, 1 indicates village)
     */
    public void setSettler(int row, int col, int piece) {
        this.board.tiles[row][col].occupier = this.currentPlayer;
        this.board.tiles[row][col].village = piece;
        if (piece == 0) {this.board.getPlayer(this.currentPlayer).settlers += 1;}
        else if (piece == 1) {this.board.getPlayer(this.currentPlayer).villages += 1;}

        if (this.board.tiles[row][col].isStoneCircle) {
            if (this.board.tiles[row][col].resource != null) {
                this.board.getPlayer(this.currentPlayer).
                        resources[Board.Tile.resourceToInt(this.board.tiles[row][col].resource)] += 1;
            }
            this.board.tiles[row][col].resource = null;
        }
    }

    /**
     *  Outputs all valid moves a player can make in the current state
     *  @param player - integer representing chosen player
     *  @return hashset of all valid moves a player can make
     */
    public HashSet<String> allValidMoves(int player) {
        HashSet<String> ms=new HashSet<String>();
        int len;
        for (int row = 0 ;row < board.boardSize ;row ++) {
            len = 0;
            if (row % 2 == 0) {len = -1;}
            for (int col = 0; col < board.boardSize + len ; col ++) {
                if (gamestate == 0) {
                    if (board.isValidMove(row ,col ,player, 0, 0)) {
                        ms.add("S " + row + "," + col );
                    }
                    if (board.isValidMove(row ,col ,player, 1, 0)) {
                        ms.add("T " + row + "," + col );
                    }
                }
                if (gamestate == 1) {
                    if (board.isValidMove(row ,col ,player, 0, 1)) {
                        ms.add("S " + row + "," + col );
                    }
                }
            }
        }
        return ms;
    }

    /**
     * Outputs all valid moves a player can make in the current state
     * @param row - row coordinate of piece
     * @param col - col coordinate of piece
     * @param piece - (0 indicates settler, 1 indicates village)
     * @return hashset of all valid moves a player can make
     */
    public int applyMove(int row, int col, int piece) {
        setSettler(row, col, piece);
        if (checkEnd()) {
            var setto1 = false;
            if (gamestate == 0) {
                advancePlayer();
                setto1 = true;
            }
            reset();
            toModel(toStateString());
            if (setto1 && allValidMoves(currentPlayer).size() == 0) {advancePlayer();};
            return 1;
        }

        for (int k = 0; k < numOfPlayers; k++) {
            advancePlayer();
            if (allValidMoves(currentPlayer).size() != 0 && (board.getPlayer(currentPlayer).settlers != 30 ||
                            board.getPlayer(currentPlayer).villages != 5)) {break;}
        }
        return 0;
    }


    /**
     * Assigns a random resource to all stone resource tiles and
     * resets all resources that the player has obtained.
     */
    public void resetAllResources() {
        if(gamestate == 0){
            List<Board.Tile> stoneCoords = this.board.getStoneRsrcTiles();
            board.assignRanResources(stoneCoords);
            for (var k = 0; k < numOfPlayers; k++) {
                board.playerList.get(k).resetResources();
            }
        }
    }

    /**
     * The main reset function. Counts the points obtained by every player and if
     * it is in the exploration phase, progresses to the next state while removing
     * all settler pieces off the board and reseting all resources.
     */
    public void reset() {
        for (int k = 0; k < this.numOfPlayers; k ++) {
            var points = board.countPoints(k);
            board.playerList.get(k).points += points;
        }
        if (gamestate == 0) {
            board.removePieces();
            this.resetAllResources();
            this.changeState();
        }
    }

    /**
     * Adds one to the currentPlayer and wraps around
     * if currentPlayer exceeds numOfPlayers
     */
    public void advancePlayer() {
        this.currentPlayer += 1;
        if (this.currentPlayer >= this.numOfPlayers) {
            this.currentPlayer = 0;
        }
    }


    /**
     * Adds one to the currentPlayer and wraps around
     * if currentPlayer exceeds numOfPlayers
     */
    public boolean checkEnd() {
        boolean nomoremoves = true;
        for (int k = 0; k < numOfPlayers; k++) {
            if (allValidMoves(k).size() != 0) {nomoremoves = false;};
        }
        return nomoremoves || this.board.allResourcesCollected() || this.board.noValidMoves(this.gamestate);
    }

    /**
     * Applies countPoints to allValidMoves to compare the which move
     * generates the most number of points.
     * @param accumulator - empty list of minmaxNodes that will be populated
     *                    with minmaxNodes representing all valid moves
     * @param statestring - stateString representing the current gamestate
     */
    public void aiMoves (int previousPoints, List<minmaxNode> accumulator, String statestring) {
        int player = this.currentPlayer;
        for (String move :  this.allValidMoves(player)){
            var split = move.split(" ");
            int row = Integer.parseInt(split[1].split(",")[0]);
            int col = Integer.parseInt(split[1].split(",")[1]);
            int piece = 0;
            if (move.charAt(0) == 'T') {piece =1;}

            minmaxNode nextnode;
            nextnode = new minmaxNode(row, col,0, piece);

            Model nextModel = new Model();
            nextModel.toModel(statestring);
            nextModel.applyMove(row, col, piece);

            nextnode.points += nextModel.board.countPoints(player) - previousPoints;
            accumulator.add(nextnode);
        }
    }

    /**
     * @return moveString representing the optimal move decided on by the AI.
     */
    public String decisionMaker() {
        List<Model.minmaxNode> accumulator = new ArrayList<>();
        this.aiMoves(this.board.countPoints(this.currentPlayer), accumulator, this.toStateString());

        int centre = this.board.boardSize/2;
        Model.minmaxNode bestNode = new Model.minmaxNode();
        Model.minmaxNode closestNode = new Model.minmaxNode();
        double dist = 100;
        for (Model.minmaxNode node: accumulator) {
            double interNodedist =
                    Math.sqrt((centre - node.col) * (centre - node.col) + (centre - node.row) * (centre - node.row));
            if (interNodedist < dist) {
                closestNode = node;
                dist = interNodedist;}

            if (node.piece == 0 && node.points > bestNode.points) {
                bestNode = node;
            }

            // if island does not have a village already, places a village there
            if (node.piece == 1 && !board.doesIslandHaveVillage(board.findIsland(node.row, node.col))) {
                bestNode = node;
                break;
            }
        }

        // if all possible nodes yield no points, chooses a point closes to the centre
        if (bestNode.points == 0) {
            bestNode = closestNode;
        }

        if (bestNode.piece == 0) {
            return "S " + bestNode.row + "," + bestNode.col;
        }
        else {
            return "T " + bestNode.row + "," + bestNode.col;
        }
    }

    public static class minmaxNode {
        int row;
        int col;
        int piece;
        int points;

        public minmaxNode(int row, int col, int points, int piece) {
            this.row = row;
            this.col = col;
            this.points = points;
            this.piece = piece;
        }

        public minmaxNode() {
            this.row = 0;
            this.col = 0;
            this.points = 0;
            this.piece = 0;
        }
    }
}

