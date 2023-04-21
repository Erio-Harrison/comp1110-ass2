package comp1110.ass2;

import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Model {

    // the num of players in the game
    int numOfPlayers;

    // exploration(0) or settling(1) phase
    int gamestate;

    //current player
    int currentPlayer;

    // array with the points of each player
    int[] allPoints;

    // numOfTurns before gameEnds
    int numOfTurns;

    //Board
    Board board;

    // returns which player has the most points
    public static int declareWinner() {
        return 0;
    }

    private void modelSetUcr(String[] ucrState) {
        int ucrPosition = 2;
        System.out.println("setting coconut");
        System.out.println(Arrays.toString(ucrState));
        for (int k = ucrPosition; !ucrState[k].equals("B"); k++) {
            String[] coord =  ucrState[k].split(",");
            this.board.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = Board.Tile.Resource.COCO;
            ucrPosition += 1;
        }
        ucrPosition += 1;
        System.out.println("setting bamboo");
        for (int k = ucrPosition; !ucrState[k].equals("W"); k++) {
            String[] coord =  ucrState[k].split(",");
            this.board.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = Board.Tile.Resource.BBOO;
            ucrPosition += 1;
        }
        ucrPosition += 1;
        System.out.println("setting watr");
        for (int k = ucrPosition; !ucrState[k].equals("P"); k++) {
            String[] coord =  ucrState[k].split(",");
            this.board.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = Board.Tile.Resource.WATR;
            ucrPosition += 1;
        }
        ucrPosition += 1;
        System.out.println("setting stones");
        for (int k = ucrPosition; !ucrState[k].equals("S"); k++) {
            String[] coord =  ucrState[k].split(",");
            this.board.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = Board.Tile.Resource.STON;
            ucrPosition += 1;
        }
        ucrPosition += 1;
        System.out.println("setting statuettes");
        for (int k = ucrPosition; k < ucrState.length; k++) {
            String[] coord =  ucrState[k].split(",");
            this.board.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = Board.Tile.Resource.STAT;
        }
    }

    private void toModel(String stateString) {
        String[] stateArray = stateString.split("; |;");
        int length = stateArray.length;

        //gameArrangement
        String[] gameArrangeStatement = stateArray[0].split(" ");
        this.numOfPlayers = Integer.parseInt(gameArrangeStatement[2]);
        int size = Integer.parseInt(gameArrangeStatement[1]);
        this.board = new Board(size);

        //currentState
        String[] currentStateStatement = stateArray[1].split(" ");
        this.currentPlayer = Integer.parseInt(currentStateStatement[1]);

        String phase = (currentStateStatement[2]);
        if (phase.equals("E")) {
            this.gamestate = 0;
        }
        else if (phase.equals("S")) {
            this.gamestate = 1;
        }

        //islands
        int i = 2;
        ArrayList islandState = (ArrayList) Arrays.stream(stateArray).collect(Collectors.partitioningBy(n -> n.charAt(0) == 'i')).values().toArray()[1];
        i += islandState.size();

        System.out.println("testing stoneStatement");
        String[] stoneStatement = stateArray[i].split(" ");
        for (int k = 1; k < stoneStatement.length; k++) {
            String[] coord = stoneStatement[k].split(",");
            this.board.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].isStoneCircle = true;
        }

        System.out.println("testing resources");
        i  += 1;
        String[] ucrState = stateArray[i].split(" ");
        modelSetUcr(ucrState);
        i  += 1;

        System.out.println("testing players");
        //player
        List<String> playerStatement = new ArrayList<String>();
        while (i < length) {
            playerStatement.add(stateArray[i]);
            i++;
        }
        String currentPlayStatement = playerStatement.get(this.currentPlayer);
        String[] current = currentPlayStatement.split(" ");


        // setting islands on board
        int islandNum = 0;
        for (Object j : islandState) {
            islandNum += 1;
            String[] temp = ((String) j).split(" ");
            for (int k = 2; k < temp.length; k++) {
                String[] coord = temp[k].split(",");
                this.board.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].type = 1; // 1 represents land
                this.board.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].island = islandNum;
            }
        }

        // setting players on board
        for (String p : playerStatement) {
            String[] scores = p.split(" ");
            if (scores[scores.length -2].equals("S")) {
                continue;
            }

            int id  = Character.getNumericValue(p.charAt(2));
            int l = 9; //'Settlers part of the string'

            // retrieve all settler coordinates
            while (!scores[l].equals("T")) {
                String[] settlers = scores[l].split(",");
                int x = Integer.parseInt(settlers[0]);
                int y = Integer.parseInt(settlers[1]);
                this.board.tiles[x][y].occupier = id;
                l++;
            }
            l++;

            if (scores[scores.length -1].equals("T")) {
                continue;
            }
            // retrieve all villager coordinates
            while (l < scores.length) {
                String[] settlers = scores[l].split(",");
                int x = Integer.parseInt(settlers[0]);
                int y = Integer.parseInt(settlers[1]);
                this.board.tiles[x][y].occupier = id;
                this.board.tiles[x][y].village = 1;

                l++;
            }
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


    public static void main(String[] args) {


        // while true:
        //    while true:
        //          board.setSettler(current player)
        //          board.checkEnd()  --> (if true, breaks out of loop)
        //          increment player by 1
        //     board.countAll()       --> (counts all points accumulated by players adding it to allPoints)
        //     if numOfTurn s has reached:
        //            declareWinner()
        //            break loop
        //     else:
        //            board.reset()          --> (resets board and changes to next state)
        //
        String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";

        Model test = new Model();
        test.toModel(DEFAULT_GAME);


    }
}
