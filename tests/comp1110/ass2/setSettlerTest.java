package comp1110.ass2;

import comp1110.ass2.BlueLagoon;
import comp1110.ass2.Board;
import comp1110.ass2.Model;
import comp1110.ass2.gittest.B;
import comp1110.ass2.testdata.GameDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
public class setSettlerTest {
    private final GameDataLoader gameDataLoader = new GameDataLoader();
    public void applyString(String stateString){
        Model model = new Model();
        model.toModel(stateString);
    }


    int x = 0;
    int y = 0;

    @Test
    public void defaultGame(){
        String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
        applyString(DEFAULT_GAME);
        Model model = new Model();
        Board board = new Board(13);
        int size = board.boardSize ;
        int curPlayer = model.currentPlayer;
        int phase = model.gamestate;
        for (x = 0;x <= size;x++){
            for (y = 0;y <= size;y++){
                int piece = board.tiles[x][y].village;
                String moveStr = String.valueOf(piece)+" "+String.valueOf(x)+","+String.valueOf(y);
                Boolean actual = Board.setSettler(x,y,curPlayer,piece,phase);
                Boolean expected = BlueLagoon.isMoveValid(DEFAULT_GAME,moveStr);
                System.out.println(phase);
                System.out.println(x+","+y);
                Assertions.assertEquals(expected,actual);
            }
        }






    }




}
