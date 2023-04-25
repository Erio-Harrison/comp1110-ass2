package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        Board testBoard = new Board(13);
        // Top Left
        ArrayList<Board.Tile> topLeft = new ArrayList<>();
        topLeft.add(Board.getTiles(1,0));
        topLeft.add(Board.getTiles(0,1));
        topLeft.add(Board.getTiles(1,1));
        ArrayList<Board.Tile> actualTLeft = Board.adjacentTiles(0,0);
        Assertions.assertIterableEquals(actualTLeft,topLeft);

        // Top Right
        ArrayList<Board.Tile> topRight = new ArrayList<>();
        topRight.add(Board.getTiles(10,0));
        topRight.add(Board.getTiles(11,1));
        topRight.add(Board.getTiles(12,1));
        ArrayList<Board.Tile> actualTRight = Board.adjacentTiles(11,0);
        Assertions.assertIterableEquals(actualTRight,topRight);

        // Bottom Left
        ArrayList<Board.Tile> bottomLeft = new ArrayList<>();
        bottomLeft.add(Board.getTiles(1,12));
        bottomLeft.add(Board.getTiles(1,11));
        bottomLeft.add(Board.getTiles(0,11));
        ArrayList<Board.Tile> actualBLeft = Board.adjacentTiles(0,12);
        Assertions.assertIterableEquals(actualBLeft,bottomLeft);

        // Bottom Right
        ArrayList<Board.Tile> bottomRight = new ArrayList<>();
        bottomRight.add(Board.getTiles(10,12));
        bottomRight.add(Board.getTiles(12,11));
        bottomRight.add(Board.getTiles(11,11));
        ArrayList<Board.Tile> actualBRight = Board.adjacentTiles(11,12);
        Assertions.assertIterableEquals(actualBRight,bottomRight);


    }

    @Test
    public void topAndBottom() {
    // Top Side
        Board board = new Board(13);
        ArrayList<Board.Tile> topSide = new ArrayList<>();
        topSide.add(Board.getTiles(3,0));
        topSide.add(Board.getTiles(5,0));
        topSide.add(Board.getTiles(4,1));
        topSide.add(Board.getTiles(5,1));
        ArrayList<Board.Tile> actualTop = Board.adjacentTiles(4,0);
        Assertions.assertIterableEquals(actualTop,topSide);
    // Bottom Side
        ArrayList<Board.Tile> botSide = new ArrayList<>();
        botSide.add(Board.getTiles(1,12));
        botSide.add(Board.getTiles(3,12));
        botSide.add(Board.getTiles(3,11));
        botSide.add(Board.getTiles(2,11));
        ArrayList<Board.Tile> actualBot = Board.adjacentTiles(2,12);
        Assertions.assertIterableEquals(actualBot,botSide);
    }

    @Test
    public void sides() {
        // Odd Left
        Board board = new Board(13);
        ArrayList<Board.Tile> oddLeft = new ArrayList<>();
        oddLeft.add(Board.getTiles(1,3));
        oddLeft.add(Board.getTiles(0,2));
        oddLeft.add(Board.getTiles(0,4));
        ArrayList<Board.Tile> actualOddLeft = Board.adjacentTiles(0,3);
        Assertions.assertIterableEquals(actualOddLeft,oddLeft);
        // Odd Right
        ArrayList<Board.Tile> oddRight = new ArrayList<>();
        oddRight.add(Board.getTiles(11,3));
        oddRight.add(Board.getTiles(11,2));
        oddRight.add(Board.getTiles(11,4));
        ArrayList<Board.Tile> actualOddRight = Board.adjacentTiles(12,3);
        Assertions.assertIterableEquals(actualOddRight,oddRight);
        // Even Left
        ArrayList<Board.Tile> evenLeft = new ArrayList<>();
        evenLeft.add(Board.getTiles(1,6));
        evenLeft.add(Board.getTiles(1,5));
        evenLeft.add(Board.getTiles(0,5));
        evenLeft.add(Board.getTiles(0,7));
        evenLeft.add(Board.getTiles(1,7));
        ArrayList<Board.Tile> actualEvenLeft = Board.adjacentTiles(0,6);
        Assertions.assertIterableEquals(actualEvenLeft,evenLeft);
        // Even Right
        ArrayList<Board.Tile> evenRight = new ArrayList<>();
        evenRight.add(Board.getTiles(10,8));
        evenRight.add(Board.getTiles(12,7));
        evenRight.add(Board.getTiles(11,7));
        evenRight.add(Board.getTiles(11,9));
        evenRight.add(Board.getTiles(12,9));
        ArrayList<Board.Tile> actualEvenRight = Board.adjacentTiles(11,8);
        Assertions.assertIterableEquals(actualEvenRight,evenRight);
    }

    @Test
    public void middle() {
        Board board = new Board(13);

        // Even Row
        ArrayList<Board.Tile> middle = new ArrayList<>();
        middle.add(Board.getTiles(4,8));
        middle.add(Board.getTiles(6,8));
        middle.add(Board.getTiles(6,7));
        middle.add(Board.getTiles(5,7));
        middle.add(Board.getTiles(5,9));
        middle.add(Board.getTiles(6,9));
        ArrayList<Board.Tile> actualMiddle = Board.adjacentTiles(5,8);
        Assertions.assertIterableEquals(actualMiddle,middle);

        // Odd Row
        ArrayList<Board.Tile> middle2 = new ArrayList<>();
        middle2.add(Board.getTiles(3,7));
        middle2.add(Board.getTiles(5,7));
        middle2.add(Board.getTiles(3,6));
        middle2.add(Board.getTiles(4,6));
        middle2.add(Board.getTiles(3,8));
        middle2.add(Board.getTiles(4,8));
        ArrayList<Board.Tile> actualMiddle2 = Board.adjacentTiles(4,7);
        Assertions.assertIterableEquals(actualMiddle2,middle2);
    }






}
