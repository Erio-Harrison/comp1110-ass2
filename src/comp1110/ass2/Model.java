package comp1110.ass2;

import javafx.scene.paint.Color;

import java.lang.reflect.Array;
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

    // array with the points of each player
    public int[] allPoints;

    // numOfTurns before gameEnds
    public int numOfTurns;

    //Board
    public Board board;

    public Model() {
    }
    public void changeState() {
        switch (gamestate) {
            case (0) -> {
                gamestate = 1;
            }
            case (1) -> {
                gamestate = 0;
            }
        }
    }


public String toPhase() {
        if (gamestate == 0) return "Exploration";
        return "Settlement";
}


    //authored by Tay Shao An
    //takes a stateString and adds its attributes to the model
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
        ArrayList islandState = (ArrayList) Arrays.stream(stateArray).collect(Collectors.partitioningBy(n -> n.charAt(0) == 'i')).values().toArray()[1];
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

    public boolean isMoveValid(int x,int y,int piece) {
        return (((this.gamestate == 0) && this.board.isValidExploration(x,y,this.currentPlayer,piece))
                || (this.gamestate == 1 && this.board.isValidSettle(x,y,this.currentPlayer,piece)));
    }
    // converts model to a statestring
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
                if (Board.tiles[row][col].island != 0) {
                    islands.get(Board.tiles[row][col].island - 1).add(row + "," + col);}

                if (Board.tiles[row][col].isStoneCircle) {stones.add(row + "," + col);}

                if (Board.tiles[row][col].resource != null) {
                    resources.get(Board.resourceToInt(Board.tiles[row][col].resource)).add(row + "," + col);}
                if (Board.tiles[row][col].occupier != -1) {
                    if (Board.tiles[row][col].village == 1) {
                        List<String> currentList = playerVillages.get(Board.tiles[row][col].occupier);
                        currentList.add(row + "," + col);
                    }
                    if (Board.tiles[row][col].village == 0) {
                        List<String> currentList = playerSettler.get(Board.tiles[row][col].occupier);
                        currentList.add(row + "," + col);
                    }
                }
            }
        }

        // constructs the statement
        String gameAStatement = "a " + this.board.boardSize + " " + this.numOfPlayers + ";";

        String csStatement = "c " + this.currentPlayer + " " + state + ";";

        String stoneStatement = "s";
        for (var k: stones) {stoneStatement += " " + k;}
        stoneStatement += ";";

        String islandStatement = "";
        int i = 0;
        for (var k: islands) {
            i += 1;
            String currentIS = "i " + this.board.islandToPoints.get(i - 1);
            for (var t: k) {currentIS += " " + t;}
            islandStatement += currentIS + "; ";
        }

        String resStatement = "r";
        for (var k: resources) {
            for (String s: k) {resStatement += " " + s;}
        }
        resStatement += ";";

        String playerStatement = "";
        for (int k = 0; k < players.size(); k++) {
            List<String> player = players.get(k);
            if (k > 0) {playerStatement += " ";}
            for (var t: player) {playerStatement += t + " ";}
            playerStatement += "S ";

            for (var t: playerSettler.get(k)) {playerStatement += t + " ";}
            playerStatement += "T";

            for (var t: playerVillages.get(k)) {playerStatement += " " + t;}
            playerStatement += ";";

        }
        return gameAStatement + " " + csStatement + " " + islandStatement + stoneStatement + " " + resStatement + " " + playerStatement;
    }



    // sets a piece at a particular tile
    public void setSettler(int x, int y, int piece) {
        Board.tiles[x][y].occupier = this.currentPlayer;
        Board.tiles[x][y].village = piece;
        if (piece == 0) {this.board.getPlayer(this.currentPlayer).settlers += 1;}
        else if (piece == 1) {this.board.getPlayer(this.currentPlayer).villages += 1;}

        if (Board.tiles[x][y].isStoneCircle) {
            this.board.getPlayer(this.currentPlayer).resources[Board.resourceToInt(Board.tiles[x][y].resource)] += 1;
            Board.tiles[x][y].resource = null;
        }
    }


    public void previousTurn() {

            this.currentPlayer--;
            if (this.currentPlayer <= 0) {
                this.currentPlayer = numOfPlayers;
            }
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

    public Board getBoard() {
        return board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getGamestate() {
        return gamestate;
    }



    // returns a hashset of movestrings of every valid move a player can make
    public HashSet<String> allValidMoves(int player) {
        HashSet<String> ms=new HashSet<String>();
        int len;
        for (int a=0;a < board.boardSize ;a++) {
            len = 0;
            if (a % 2 == 0) {len = -1;}
            for (int b = 0; b < board.boardSize + len ; b++) {
                if (gamestate == 0) {
                    if (board.isValidExploration(a,b,player, 0)) {
                        ms.add("S " + a + "," + b);
                    }
                    if (board.isValidExploration(a,b,player, 1)) {
                        ms.add("T " + a + "," + b);
                    }
                }
                if (gamestate == 1) {
                    if (board.isValidSettle(a,b,player, 0)) {
                        ms.add("S " + a + "," + b);
                    }
                }
            }
        }
        return ms;
    }


    // resets board and progresses the game to the next phase
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    public void resetAllResources() {
        if(gamestate == 0){
            List<Board.Tile> stoneCoords = this.board.getStoneRsrcTiles();
            board.assignRanResources(stoneCoords);
            for (var k = 0; k < numOfPlayers; k++) {
                board.playerList.get(k).resetResources();
            }
        }
    }

    public void reset() {
        countAllPoints();
        if (gamestate == 0) {
            board.removePieces();
            this.resetAllResources();
            this.changeState();
        }

    }

    public void advancePlayer() {
        this.currentPlayer += 1;
        if (this.currentPlayer >= this.numOfPlayers) {
            this.currentPlayer = 0;
        }
    }


    // checks alls end of phase conditions, returns true if it the end
    public boolean checkEnd(int gameState) {
        Boolean nomoremoves = true;
        for (int k = 0; k < numOfPlayers; k++) {
            if (allValidMoves(k).size() != 0) {nomoremoves = false;};
        }
        if (nomoremoves) {return true;};
        if (this.board.allResourcesCollected()) {return true;}
        if (this.board.noValidMoves(gameState)) {return true;}

        return false;
    }

    public int countPoints (int player){
        return board.countPoints(player);
    }

    // counts all the points for each player and adds them to the playerlist
    public void countAllPoints() {
        for (int k = 0; k < this.numOfPlayers; k ++) {
            var points = board.countPoints(k);
            board.playerList.get(k).points += points;
        }
    }

    public String phaseToString() {
        if (gamestate == 0) return "EXPLORATION";
        return "SETTLEMENT";
    }
    public static void main(String[] args) {
        String SNAKE= "a 13 2; c 0 S; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C 0,0 1,8 9,9 11,0 12,2 12,8 B 4,2 5,9 7,0 9,0 10,10 11,5 W 0,5 1,4 3,5 3,12 5,11 6,3 P 0,9 4,0 6,6 10,3 10,6 12,11 S 1,12 2,1 3,7 3,10 7,8 7,12 8,2 8,5; p 0 74 0 0 0 0 0 S T 0,6 0,7 1,6 4,5 8,1; p 1 66 0 0 0 0 0 S T 5,6 6,1 11,1 12,5;";
        Model test = new Model();
        test.toModel(SNAKE);
        System.out.println(test.allValidMoves(test.currentPlayer));

    }
}
