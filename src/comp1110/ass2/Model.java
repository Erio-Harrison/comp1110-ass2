package comp1110.ass2;

import java.util.Arrays;

public class Model {

    // the num of players in the game
    int numOfPlayers;

    // exploration(0) or settling(1) phase
    int gamestate;

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



    private static String getStatement(String stateString, char start, char end) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == start) {
                startIndex = i+2;

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


    public static void main(String[] args) {
        String string = getStatement("s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11;", 's', ';');
        String[] strings = string.split(" ");
        String[] test = strings[5].split(",");
        System.out.println(Integer.parseInt(test[1]));
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


    }
}
