package comp1110.ass2.testdata;

import comp1110.ass2.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class checkOccupierTest {
    public static final String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String NORMAL_GAME = "a 13 2; c 1 E; i 5 0,1 0,2 0,3 0,4 1,1 1,5 2,0 2,5 3,0 3,6 4,0 4,5 5,1 5,5 6,1 6,2 6,3 6,4; i 5 0,8 0,9 0,10 1,8 1,11 2,7 2,11 3,8 3,11 4,8 4,9 4,10; i 7 8,8 8,9 8,10 9,8 9,11 10,7 10,11 11,8 11,11 12,8 12,9 12,10; i 7 10,0 10,1 10,4 10,5 11,0 11,2 11,3 11,4 11,6 12,0 12,1 12,4 12,5; i 9 2,2 2,3 3,2 3,4 4,2 4,3; i 9 2,9; i 9 6,6 6,7 6,8 6,9 6,10 6,11 7,6 8,0 8,1 8,2 8,3 8,4 8,5; i 9 10,9; s 0,1 0,4 0,10 2,2 2,3 2,9 2,11 3,0 3,2 3,4 3,6 4,2 4,3 4,10 6,1 6,4 6,6 6,11 8,0 8,5 8,8 8,10 10,0 10,5 10,7 10,9 10,11 11,3 12,1 12,4 12,8 12,10; r C 0,4 2,9 3,6 6,6 10,9 12,10 B 4,2 6,11 8,8 10,7 10,11 12,1 W 0,10 2,11 3,4 4,10 12,4 12,8 P 2,2 2,3 4,3 8,5 8,10 11,3 S 0,1 3,0 3,2 6,1 6,4 8,0 10,0 10,5; p 0 0 0 0 0 0 0 S 1,6 9,9 T 9,8; p 1 0 0 0 0 0 0 S 3,7 7,11 T;";
    public static final String SETTLE_GAME= "a 13 2; c 0 S; i 5 0,1 0,2 0,3 0,4 1,1 1,5 2,0 2,5 3,0 3,6 4,0 4,5 5,1 5,5 6,1 6,2 6,3 6,4; i 5 0,8 0,9 0,10 1,8 1,11 2,7 2,11 3,8 3,11 4,8 4,9 4,10; i 7 8,8 8,9 8,10 9,8 9,11 10,7 10,11 11,8 11,11 12,8 12,9 12,10; i 7 10,0 10,1 10,4 10,5 11,0 11,2 11,3 11,4 11,6 12,0 12,1 12,4 12,5; i 9 2,2 2,3 3,2 3,4 4,2 4,3; i 9 2,9; i 9 6,6 6,7 6,8 6,9 6,10 6,11 7,6 8,0 8,1 8,2 8,3 8,4 8,5; i 9 10,9; s 0,1 0,4 0,10 2,2 2,3 2,9 2,11 3,0 3,2 3,4 3,6 4,2 4,3 4,10 6,1 6,4 6,6 6,11 8,0 8,5 8,8 8,10 10,0 10,5 10,7 10,9 10,11 11,3 12,1 12,4 12,8 12,10; r C 0,1 0,10 4,2 8,10 11,3 B 0,4 2,2 6,6 8,5 10,9 12,10 W 3,0 3,4 3,6 4,3 6,11 P 2,3 3,2 8,0 10,7 12,1 12,4 S 2,9 2,11 6,1 10,0 10,5 10,11 12,8; p 0 34 0 0 0 0 1 S 1,5 5,5 6,4 8,7 9,7 9,9 T 2,5 4,5 7,6 9,8; p 1 42 1 0 1 0 0 S 3,10 4,10 7,9 7,10 8,8 8,9 T 3,11 6,10;";
    public static final String MOVES_END_GAME = "a 13 2; c 1 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C 1,8 3,12 7,12 12,8 12,11 B 0,9 3,7 3,10 9,0 W 1,12 4,0 4,2 10,6 12,2 P 0,0 5,9 9,9 S 0,5 6,3 7,0 7,8 8,5 10,10; p 0 0 1 2 1 0 1 S 0,3 0,4 0,8 1,4 2,5 2,9 3,4 3,5 3,9 3,11 4,10 5,7 5,10 5,11 6,9 7,1 7,3 7,4 7,11 8,2 8,3 8,6 8,7 9,3 9,4 9,6 9,8 10,2 10,3 11,2 T 0,6 0,7 1,6 4,5 8,1; p 1 0 0 0 0 3 1 S 0,10 0,11 1,5 1,9 1,10 2,1 2,3 2,6 3,2 3,6 4,1 4,6 4,9 5,1 5,2 5,5 6,6 6,11 8,10 9,1 9,10 9,11 10,0 10,5 11,4 11,5 11,6 11,7 12,3 12,4 T 5,6 6,1 11,0 11,1 12,5;";
    //int[] pos = posCreate(x, y);
    public void applyString(String gameState,int x, int y,int player,boolean expect){
        Model test = new Model();
        test.toModel(gameState);
        int[] pos = test.board.posCreate(x,y);
        boolean actual = test.board.checkOccupier(pos,x,y,player);
        Assertions.assertEquals(expect, actual);
    }
    @Test
    public void testDefault(){
        //player0
        //all false case
        applyString(DEFAULT_GAME,1,2,0,false);
        applyString(DEFAULT_GAME,7,11,0,false);
        applyString(DEFAULT_GAME,4,7,0,false);
        applyString(DEFAULT_GAME,9,9,0,false);
        //player1
        //all false case
        applyString(DEFAULT_GAME,5,6,1,false);
        applyString(DEFAULT_GAME,11,10,1,false);
        applyString(DEFAULT_GAME,1,11,1,false);
        applyString(DEFAULT_GAME,8,2,1,false);
    }
    @Test
    public void testNormalCase(){
        //player0
        //true case
        applyString(NORMAL_GAME,1,7,0,true);
        applyString(NORMAL_GAME,9,9,0,true);
        //false case
        //occupied by player1
        applyString(NORMAL_GAME,7,11,0,false);
        //random unclaimed tiles
        applyString(NORMAL_GAME,4,7,0,false);

        //player1
        //true case
        applyString(NORMAL_GAME,7,10,1,true);
        //false case
        applyString(NORMAL_GAME,11,10,1,false);
        applyString(NORMAL_GAME,1,11,1,false);
        applyString(NORMAL_GAME,8,2,1,false);
    }
    @Test
    public void testSettleCase(){
        //player0
        //true case
        applyString(SETTLE_GAME,9,7,0,true);
        applyString(SETTLE_GAME,9,8,0,true);
        applyString(SETTLE_GAME,8,8,0,true);
        //false case
        //occupied by player1
        applyString(SETTLE_GAME,3,9,0,false);
        //random unclaimed tiles
        applyString(SETTLE_GAME,4,7,0,false);

        //player1
        //true case
        applyString(SETTLE_GAME,4,10,1,true);
        //false case
        applyString(SETTLE_GAME,3,8,1,false);
        applyString(SETTLE_GAME,11,10,1,false);
        applyString(SETTLE_GAME,1,11,1,false);
    }
    @Test
    public void testMovesEndCase(){
        //player0
        //true case
        applyString(MOVES_END_GAME,1,3,0,true);
        applyString(MOVES_END_GAME,3,8,0,true);

        //false case
        //occupied by player1
        applyString(SETTLE_GAME,4,9,0,false);
        //random unclaimed tiles
        applyString(SETTLE_GAME,3,7,0,false);

        //player1
        //true case
        applyString(SETTLE_GAME,6,11,1,true);
        applyString(SETTLE_GAME,6,10,1,true);
        //false case
        applyString(SETTLE_GAME,3,8,1,false);
        applyString(SETTLE_GAME,11,10,1,false);
        applyString(SETTLE_GAME,1,11,1,false);

    }
    }

