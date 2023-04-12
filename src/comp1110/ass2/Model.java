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


    public static void main(String[] args) {
        String string1 = "r C 1,1 B 1,2 W P 1,4 S;";
        String[] strings = string1.split(" ");
        String string = getStatement("r C 1,1 2,2 B 2,2 W 3,3 P S;", 'W', 'P');
        String[] strings2 = string.split(" ");

        System.out.println(Arrays.toString(strings2[1].split(",")));
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
