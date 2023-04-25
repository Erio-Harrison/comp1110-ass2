package comp1110.ass2;

import comp1110.ass2.gittest.B;
import comp1110.ass2.testdata.GameDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import comp1110.ass2.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

class containPlayerTiles {
    //coord is out of bound
    @Test
    public void emptyAdjacentArray(){
        //smaller than size
        Board testBoard = new Board(13);
        ArrayList<Board.Tile> empty1 = Board.adjacentTiles(-1,-1);
        Boolean actual1 = Board.containsPlayerTiles(1,empty1);
        Boolean expect1 = Boolean.FALSE;
        Assertions.assertEquals(expect1,actual1);

        // short row
        ArrayList<Board.Tile> empty2 = Board.adjacentTiles(13,4);
        Boolean actual2 = Board.containsPlayerTiles(1,empty2);
        Boolean expect2 = Boolean.FALSE;
        Assertions.assertEquals(expect2,actual2);

        //larger than size
        ArrayList<Board.Tile> empty3 = Board.adjacentTiles(15,15);
        Boolean actual3 = Board.containsPlayerTiles(5,empty3);
        Boolean expect3 = Boolean.FALSE;
        Assertions.assertEquals(expect3,actual3);


    }
    @Test
    public void normalTrueCase(){
        //top left corner
        Board newBoard = new Board(13);
        Board.getTiles(1,0).setPlayer(1);
        Board.getTiles(3,4).setPlayer(1);
        ArrayList<Board.Tile> test1 = Board.adjacentTiles(0,0);
        Boolean actual1 = Board.containsPlayerTiles(1,test1);
        Boolean expect1 = Boolean.TRUE;
        Assertions.assertEquals(expect1,actual1);

        //middle
        Board.getTiles(4,9).setPlayer(2);
        Board.getTiles(3,4).setPlayer(2);
        ArrayList<Board.Tile> test2 = Board.adjacentTiles(3,3);
        Boolean actual2 = Board.containsPlayerTiles(2,test2);
        Boolean expect2 = Boolean.TRUE;
        Assertions.assertEquals(expect2,actual2);

        //top right corner
        Board.getTiles(1,2).setPlayer(5);
        Board.getTiles(11,1).setPlayer(5);
        ArrayList<Board.Tile> test3 = Board.adjacentTiles(12,1);
        Boolean actual3 = Board.containsPlayerTiles(5,test3);
        Boolean expect3 = Boolean.TRUE;
        Assertions.assertEquals(expect3,actual3);

        //down left corner
        Board.getTiles(1,10).setPlayer(3);
        Board.getTiles(2,11).setPlayer(3);
        ArrayList<Board.Tile> test4 = Board.adjacentTiles(1,11);
        Boolean actual4 = Board.containsPlayerTiles(3,test4);
        Boolean expect4 = Boolean.TRUE;
        Assertions.assertEquals(expect4,actual4);

        //down right corner
        Board.getTiles(11,12).setPlayer(2);
        Board.getTiles(2,11).setPlayer(2);
        ArrayList<Board.Tile> test5 = Board.adjacentTiles(12,12);
        Boolean actual5 = Board.containsPlayerTiles(2,test4);
        Boolean expect5 = Boolean.TRUE;
        Assertions.assertEquals(expect5,actual5);

    }
    @Test
    public void noTileOccupied(){
        //no tile is occupied
        Board newBoard = new Board(13);
        Board.getTiles(1,0).setPlayer(1);
        Board.getTiles(3,4).setPlayer(1);
        ArrayList<Board.Tile> test1 = Board.adjacentTiles(5,3);
        Boolean actual1 = Board.containsPlayerTiles(2,test1);
        Boolean expect1 = Boolean.FALSE;
        Assertions.assertEquals(expect1,actual1);
    }
    @Test
    public void falseCase(){
        //top left corner
        Board newBoard = new Board(13);
        Board.getTiles(6,5).setPlayer(1);
        Board.getTiles(6,9).setPlayer(5);
        ArrayList<Board.Tile> test1 = Board.adjacentTiles(0,0);
        Boolean actual1 = Board.containsPlayerTiles(1,test1);
        Boolean expect1 = Boolean.FALSE;
        Assertions.assertEquals(expect1,actual1);

        //middle
        Board.getTiles(5,9).setPlayer(2);
        Board.getTiles(1,0).setPlayer(1);
        ArrayList<Board.Tile> test2 = Board.adjacentTiles(3,3);
        Boolean actual2 = Board.containsPlayerTiles(2,test2);
        Boolean expect2 = Boolean.FALSE;
        Assertions.assertEquals(expect2,actual2);

        //top right corner
        Board.getTiles(10,2).setPlayer(1);
        Board.getTiles(11,7).setPlayer(5);
        ArrayList<Board.Tile> test3 = Board.adjacentTiles(12,1);
        Boolean actual3 = Board.containsPlayerTiles(1,test3);
        Boolean expect3 = Boolean.FALSE;
        Assertions.assertEquals(expect3,actual3);

        //down left corner
        Board.getTiles(5,4).setPlayer(3);
        Board.getTiles(2,11).setPlayer(7);
        Board.getTiles(10,11).setPlayer(2);
        Board.getTiles(2,11).setPlayer(2);
        ArrayList<Board.Tile> test4 = Board.adjacentTiles(0,12);
        Boolean actual4 = Board.containsPlayerTiles(3,test4);
        Boolean expect4 = Boolean.FALSE;
        Assertions.assertEquals(expect4,actual4);

        //down right corner
        Board.getTiles(1,1).setPlayer(2);
        Board.getTiles(0,10).setPlayer(2);
        Board.getTiles(1,2).setPlayer(5);
        Board.getTiles(11,1).setPlayer(5);
        ArrayList<Board.Tile> test5 = Board.adjacentTiles(12,12);
        Boolean actual5 = Board.containsPlayerTiles(2,test5);
        Boolean expect5 = Boolean.FALSE;
        Assertions.assertEquals(expect5,actual5);

    }



    }




