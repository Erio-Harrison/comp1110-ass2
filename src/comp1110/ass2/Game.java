package comp1110.ass2;

public class Game {

    // the num of players in the game
    int numOfPlayers;

    // exploration(0) or settling(1) phase
    int gamestate;

    // array with the points of each player
    int[] allPoints;

    // numOfTurns before gameEnds
    int numOfTurns;

    // returns which player has the most points
    public static int declareWinner() {
        return 0;
    }

    public static void main(String[] args) {
        // while true:
        //    while true:
        //          board.setSettler(current player)
        //          board.checkEnd()  --> (if true, breaks out of loop)
        //          increment player by 1
        //     board.countAll()       --> (counts all points accumulated by players adding it to allPoints)
        //     if numOfTurns has reached:
        //            declareWinner()
        //            break loop
        //     else:
        //            board.reset()          --> (resets board and changes to next state)
        //
    }
}
