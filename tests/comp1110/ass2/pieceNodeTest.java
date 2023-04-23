package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
public class pieceNodeTest {
    public static int longestLink(int player, Board.Tile[][] tiles) {
        List<Board.PieceNode> allPieces = new ArrayList<>();
        int x = 0;
        int len = -1;
        int shortlong = 0; //0 = short, 1 = long
        for (Board.Tile[] k: tiles) {
            len = 0;
            shortlong = 0;
            if (x % 2 == 0) {
                System.out.print(" ");
                len = -1;
                shortlong = 1;
            }
            // for each tile
            for (int y = 0; y < k.length + len; y ++) {
                Board.Tile tile = k[y];
                System.out.print(tile.occupier + 1);
                System.out.print(" ");
                // if tile is occupied by player
                if (tile.occupier == player) {
                    // adds all pieces on the board to a list
                    allPieces = Board.addPieces(tile, x, y, shortlong, allPieces);
                }
            }
            System.out.println("");
            x += 1;
        }
        List<Integer> branchLen = new ArrayList<>();
        for (Board.PieceNode node: allPieces) {
            System.out.println(node.nodeRunner(new HashSet<>(), new ArrayList<>()));
            branchLen.add(node.nodeRunner(new HashSet<>(), new ArrayList<>()).size());
        }
        System.out.println("Most number of islands:" + Arrays.toString(branchLen.toArray()));
        Collections.sort(branchLen);
        return branchLen.get(branchLen.size() -1);
    }

    private static void linktest(String state, int answer){
        Model test = new Model();
        test.toModel(state);
        boolean result = longestLink(0,test.board.tiles) == answer;
        Assertions.assertTrue(result);
    }

    @Test
    public void snaketest() {
        String SNAKE= "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; p 0 0 0 0 0 0 0 S 1,2 2,2 2,3 2,4 3,4 4,4 4,5 3,6 3,7 4,7 5,7 6,6 7,6 7,2 8,2 9,2 T 9,8;";
        linktest(SNAKE, 5);

    }

    @Test
    public void sltest() {
        String STRAIGHT_LINE = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; p 1 0 0 1 0 0 0 S 0,5 3,7 7,11 T 6,11; p 0 0 0 0 0 0 0 S 1,2 2,2 3,2 4,2 5,2 6,2 7,2 8,2 9,2 T 9,8;";
        linktest(STRAIGHT_LINE, 3);

    }

    @Test
    public void pipstest() {
        String PIPS = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; p 0 0 0 0 0 0 0 S 1,2 3,2 5,2 7,2 9,2 T 9,8;";
        linktest(PIPS, 1);

    }


}
