package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import comp1110.ass2.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
public class adjacentPiecesTest {


    @Test
    void edgesCases() {
        Board board = new Board(14);
        ArrayList<Board.Tile> topLeft = new ArrayList<>();
        topLeft.add(board.getTiles(0,1)   );
        topLeft.add(board.getTiles(1,0));

        ArrayList<Board.Tile> tiles = Board.adjacentTiles(0,0);
        Assertions.assertTrue(tiles.containsAll(topLeft));
    }
    @Test
    void outOfBounds() {
       Board testBoard = new Board(14);
       ArrayList<Board.Tile> tiles = Board.adjacentTiles(-1,-1);
       Assertions.assertTrue(tiles.isEmpty());


        Board small = new Board(9);
        ArrayList<Board.Tile> tiles2 = Board.adjacentTiles(10,3);
        Assertions.assertTrue(tiles2.isEmpty());

        Board large = new Board(26);
        ArrayList<Board.Tile> tiles3 = Board.adjacentTiles(1,-30);
        Assertions.assertTrue(tiles3.isEmpty());
    }




}
