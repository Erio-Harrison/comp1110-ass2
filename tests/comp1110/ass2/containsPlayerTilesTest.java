package comp1110.ass2;

import comp1110.ass2.gittest.B;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class containsPlayerTilesTest {

    @Test

    public void hasPlayer() {
        Board board = new Board(14);
        int player = 1;
        ArrayList<Board.Tile> adjacent = Board.adjacentTiles(0,0);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j< 5; j++) {
                Board.getTiles(i,j).setPlayer(player);
            }
        }

        Assertions.assertTrue(Board.containsPlayerTiles(player,adjacent));

    }
}
