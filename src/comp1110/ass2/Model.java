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

    //takles a stateString and adds its attributes to the model
    private void toModel(String stateString) {
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
        String FACE_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; p 1 0 0 1 0 0 0 S 0,5 3,7 7,11 T 6,11; p 0 0 0 0 0 0 0 S 1,6 7,2 7,7 9,9 T 9,8;";
        String SIDES_GAME =  "a 7 2; c 0 E; i 4 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 4,0 4,1 4,2 4,3 5,0 5,1 5,2 5,3 6,0 6,1 6,2 6,3; i 20 0,5 1,5 1,6 2,5 3,5 3,6 4,5 5,5 5,6 6,5; s 0,0 0,1 0,2 0,3 1,1 1,2 1,3 1,5 1,6 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 3,5 3,6 4,0 4,1 4,2 4,3 5,1 5,2 5,3 5,5 5,6 6,0 6,1 6,2 6,3; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";

        FACE_GAME= BlueLagoon.distributeResources(FACE_GAME);

        Model test = new Model();
        test.toModel(FACE_GAME);


    }
}
