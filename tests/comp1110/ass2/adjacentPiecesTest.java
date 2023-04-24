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
    public void corners() {
        Board board = new Board(14);
        // Top left
        ArrayList<Board.Tile> topLeft = new ArrayList<>();
        topLeft.add(board.getTiles(1,0));
        topLeft.add(board.getTiles(0,1));

        ArrayList<Board.Tile> tiles = Board.adjacentTiles(0,0);
        Assertions.assertTrue(tiles.containsAll(topLeft));

        // Top Right
        ArrayList<Board.Tile> topRight = new ArrayList<>();
        topRight.add(board.getTiles(12,0));
        topRight.add(board.getTiles(13,1));

        ArrayList<Board.Tile> tiles2 = Board.adjacentTiles(13,0);
        Assertions.assertTrue(tiles2.containsAll(topRight));

        // Bottom Left
        ArrayList<Board.Tile> botLeft = new ArrayList<>();
        botLeft.add(board.getTiles(1,13));
        botLeft.add(board.getTiles(0,12));

        ArrayList<Board.Tile> tiles3 = Board.adjacentTiles(0,13);
        Assertions.assertTrue(tiles3.containsAll(botLeft));

        // Bottom Right
        ArrayList<Board.Tile> botRight = new ArrayList<>();
        botRight.add(board.getTiles(12,13));
        botRight.add(board.getTiles(13,12));

        ArrayList<Board.Tile> tiles4 = Board.adjacentTiles(13,13);
        Assertions.assertTrue(tiles4.containsAll(botRight));
    }



    @Test
    public void outOfBounds() {
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


    @Test void threeNeighbours() {
        Board board = new Board(14);
        // Positions top row
        ArrayList<Board.Tile> topRow = new ArrayList<>();
        topRow.add(board.getTiles(3,0));
        topRow.add(board.getTiles(5,0));
        topRow.add(board.getTiles(4,1));


        ArrayList<Board.Tile> tiles = Board.adjacentTiles(4,0);
        Assertions.assertTrue(tiles.containsAll(topRow));
        // Position bottom row
        ArrayList<Board.Tile> botRow = new ArrayList<>();
        botRow.add(board.getTiles(4,13));
        botRow.add(board.getTiles(6,13));
        botRow.add(board.getTiles(5,12));


        ArrayList<Board.Tile> tiles2 = Board.adjacentTiles(5,13);
        Assertions.assertTrue(tiles2.containsAll(botRow));
        // Position first column
        ArrayList<Board.Tile> leftCol = new ArrayList<>();
        botRow.add(board.getTiles(0,0));
        botRow.add(board.getTiles(0,2));
        botRow.add(board.getTiles(1,1));


        ArrayList<Board.Tile> tiles3 = Board.adjacentTiles(0,1);
        Assertions.assertTrue(tiles3.containsAll(leftCol));
        // position right column
        ArrayList<Board.Tile> rightCol = new ArrayList<>();
        botRow.add(board.getTiles(13,5));
        botRow.add(board.getTiles(13,7));
        botRow.add(board.getTiles(12,6));


        ArrayList<Board.Tile> tiles4 = Board.adjacentTiles(13,6);
        Assertions.assertTrue(tiles4.containsAll(rightCol));
    }
    @Test
    public void fourNeighbours() {
        Board board = new Board(14);

        ArrayList<Board.Tile> expected = new ArrayList<>();
        expected.add(board.getTiles(6,5));
        expected.add(board.getTiles(4,5));
        expected.add(board.getTiles(5,4));
        expected.add(board.getTiles(5,6));

        ArrayList<Board.Tile> tiles = Board.adjacentTiles(5,5);
        Assertions.assertTrue(tiles.containsAll(expected));

        ArrayList<Board.Tile> expected2 = new ArrayList<>();
        expected.add(board.getTiles(1,5));
        expected.add(board.getTiles(3,5));
        expected.add(board.getTiles(2,4));
        expected.add(board.getTiles(2,6));

        ArrayList<Board.Tile> tiles2 = Board.adjacentTiles(2,5);
        Assertions.assertTrue(tiles2.containsAll(expected2));

        ArrayList<Board.Tile> expected3 = new ArrayList<>();
        expected.add(board.getTiles(5,0));
        expected.add(board.getTiles(5,2));
        expected.add(board.getTiles(4,1));
        expected.add(board.getTiles(6,1));

        ArrayList<Board.Tile> tiles3 = Board.adjacentTiles(5,1);
        Assertions.assertTrue(tiles3.containsAll(expected3));
}


}
