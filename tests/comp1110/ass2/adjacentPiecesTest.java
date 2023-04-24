package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import comp1110.ass2.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
class adjacentPiecesTest {
    @Test
    public void outOfBounds() {
       Board testBoard = new Board(14);
       ArrayList<Board.Tile> tiles = Board.adjacentTiles(-1,-1);
       Assertions.assertTrue(tiles.isEmpty());

       ArrayList<Board.Tile> tiles2 = Board.adjacentTiles(0,-1);
       Assertions.assertTrue(tiles2.isEmpty());

       ArrayList<Board.Tile> tiles3 = Board.adjacentTiles(15,8);
       Assertions.assertTrue(tiles3.isEmpty());

        ArrayList<Board.Tile> tiles4 = Board.adjacentTiles(13,2);
        Assertions.assertTrue(tiles4.isEmpty());

        ArrayList<Board.Tile> tiles5 = Board.adjacentTiles(14,5);
        Assertions.assertTrue(tiles5.isEmpty());
    }

    @Test
    public void corners() {
        Board testBoard = new Board(14);
        // Top Left
        ArrayList<Board.Tile> topLeft = new ArrayList<>();
        topLeft.add(Board.getTiles(1,0));
        topLeft.add(Board.getTiles(0,1));
        topLeft.add(Board.getTiles(1,1));
        ArrayList<Board.Tile> actual = Board.adjacentTiles(0,0);
        Assertions.assertIterableEquals(actual,topLeft);

        // Top Right

        // Bottom Left

        // Bottom Right


    }

    @Test
    public void sides() {

    }

    @Test
    public void middle() {

    }






}
