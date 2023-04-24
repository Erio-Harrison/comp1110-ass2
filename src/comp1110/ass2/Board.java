package comp1110.ass2;

import javafx.scene.Node;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    int boardSize;

    int numOfIslands;
    Tile[][] tiles;

    // Generates a board and initialises all the tiles
    public Board(int boardsize) {
        this.boardSize = boardsize;
        this.numOfIslands = 0;
        this.tiles =  new Tile[boardSize][boardSize];
        for (int k = 0; k < boardsize; k ++) {
            for (int i = 0; i < boardsize; i ++) {
                tiles[k][i] = new Tile();
            }
        }

    }


    // resets board and progresses the game to the next phase
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    public void reset(int gamestate) {
        ArrayList<Tile> stoneCoords = new ArrayList();
        if (gamestate == 1){
            // resources are removed
            for (int k = 0; k < boardSize; k ++) {
                for (int i = 0; i < boardSize; i ++) {
                    tiles[k][i].resource = null;
                    if (tiles[k][i].isStoneCircle){
                        // remove the village in stone circle
                        tiles[k][i].village = 0;
                        //re-distribute resource
                        stoneCoords.add(tiles[k][i]);
                    }
                }
            }
            Tile[] rscrsSub = new Tile[6];
            int i = 0;
            for (i = 0;i < 6; i++){
                int random = (int)(Math.random() * stoneCoords.size());
                    rscrsSub[i] = stoneCoords.get(random);
                    rscrsSub[i].resource = Tile.Resource.BBOO;
                    stoneCoords.remove(random);
            }
            for (i = 0;i < 6; i++){
                int random = (int)(Math.random() * stoneCoords.size());
                rscrsSub[i] = stoneCoords.get(random);
                rscrsSub[i].resource = Tile.Resource.STON;
                stoneCoords.remove(random);
            }
            for (i = 0;i < 6; i++){
                int random = (int)(Math.random() * stoneCoords.size());
                rscrsSub[i] = stoneCoords.get(random);
                rscrsSub[i].resource = Tile.Resource.WATR;
                stoneCoords.remove(random);
            }
            for (i = 0;i < 6; i++){
                int random = (int)(Math.random() * stoneCoords.size());
                rscrsSub[i] = stoneCoords.get(random);
                rscrsSub[i].resource = Tile.Resource.COCO;
                stoneCoords.remove(random);
            }
            for (i = 0;i < stoneCoords.size(); i++){
                int random = (int)(Math.random() * stoneCoords.size());
                rscrsSub[i] = stoneCoords.get(random);
                rscrsSub[i].resource = Tile.Resource.STAT;
                stoneCoords.remove(random);
            }

        }

    }

    // =====================================================================

    // calls isValid to check if a tile is a valid position. If true, replaces tile's fields with
    // relevant values
    // int x -> x coordinate of tile
    // int y -> y coordinate of tile
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    // int player -> int representing which player is currently in play
    public boolean setSettler(int x, int y, int gamestate, int player) {
        if (isValid(x,y,gamestate)){
            tiles[x][y].setPlayer(player);
        }
        return true;
    };

    // checks if a tile is a valid tile for settler to be placed.
    // int x -> x coordinate of tile
    // int y -> y coordinate of tile
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    public static boolean isValid(int x, int y, int gamestate) {
        return true;
    };

    public int setResource(String[] split,Tile.Resource resource, int ucrPosition, String resourceChar) {
        for (int k = ucrPosition; !split[k].equals(resourceChar); k++) {
            String[] coord =  split[k].split(",");
            this.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = resource;
            ucrPosition += 1;
        }
        return ucrPosition + 1;
    }

    // used to set attributes for each tile on a board based on a string state
    // attribute is assigned as follows:
    //            this.isStoneCircle = 0;
    //            this.resource = 1;
    //            this.occupier = 2;
    //            this.island = 3;
    //            this.type = 4;
    //            this.village = 5;
    public void setBoardAttributes(String state, int attribute, int info)  {
        String[] split = state.split(" ");
        switch (attribute) {
            case 0: //isStoneCircle
                for (int k = 1; k < split.length; k++) {
                    String[] coord = split[k].split(",");
                    this.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].isStoneCircle = true;
                }
                break;

            case 1: //resource
                int ucrPosition = 2;
                ucrPosition = setResource(split,Tile.Resource.COCO, ucrPosition, "B");
                ucrPosition = setResource(split,Tile.Resource.BBOO, ucrPosition, "W");
                ucrPosition = setResource(split,Tile.Resource.WATR, ucrPosition, "P");
                ucrPosition = setResource(split,Tile.Resource.STON, ucrPosition, "S");
                for (int k = ucrPosition; k < split.length; k++) {
                    String[] coord =  split[k].split(",");
                    this.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = Board.Tile.Resource.STAT;
                }
                break;

            case 2: //occupier
                if (split[split.length -2].equals("S")) {
                    return;
                }
                int pos = 9;
                for (int l = pos; !split[l].equals("T"); l++) {
                    String[] settlers = split[l].split(",");
                    this.tiles[Integer.parseInt(settlers[0])][Integer.parseInt(settlers[1])].occupier = info;
                    pos += 1;
                }
                pos += 1;
                if (split[split.length -1].equals("T")) {
                    return;
                }
                // retrieve all villager coordinates
                for (int l = pos; l < split.length; l ++) {
                    String[] settlers = split[l].split(",");
                    this.tiles[Integer.parseInt(settlers[0])][Integer.parseInt(settlers[1])].occupier = info;
                    this.tiles[Integer.parseInt(settlers[0])][Integer.parseInt(settlers[1])].village = 1;
                }
                break;

            case 3: //island
                for (int k = 2; k < split.length; k++) {
                    String[] coord = split[k].split(",");
                    this.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].island = info;
                }
                break;

            case 4: //type
                for (int k = 2; k < split.length; k++) {
                    String[] coord = split[k].split(",");
                    this.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].type = 1;
                }
                break;

            case 5: //village
                for (int k = 1; k < split.length; k++) {
                    String[] coord = split[k].split(",");
                    this.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].village= 1;
                }
                break;
        }
    }

    // creates a PieceNode object for a specific tile and adds this PieceNode object to all PieceNodes
    // it is beside to based on a list of PieceNodes.
    public static List<PieceNode>  addPieces(Tile tile, int x, int y, int shortlong, List<PieceNode> allPieces) {
        PieceNode currentNode = new PieceNode(tile.island, x, y, shortlong);
        allPieces.add(currentNode);
        for (PieceNode node: allPieces) {
            if (node.x == x && node.y - y == 1
                    || node.x == x && node.y - y == -1
                    || node.x - x == 1 && node.y == y
                    || node.x - x == -1 && node.y == y
                    || node.x == x + 1 - (shortlong * 2) && node.y - y == -1
                    || node.x == x + 1 - (shortlong * 2) && node.y - y == 1) {
                node.edges.add(currentNode);
                currentNode.edges.add(node);
            }
        }
        return allPieces;
    }

    // =====================================================================

    // counts the total points obtained by a particular player
    // int player -> player the board is currently checking
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    //

    public int countPoints(int player, int gamestate) {
        //**Total Islands**
        //
        //Players score points for the resources they claimed during the phase.
        //
        //For each resource type (coconuts, bamboo, water and precious stones),
        //each player receives the following points:
        //- 4+ of a kind: 20 points
        //- 3 of a kind: 10 points
        //- 2 of a kind: 5 points
        //
        //Additionally, if a player has collected all 4 different resources, they get 10 bonus points.
        //
        //Players receive 4 points per claimed statuette.
        int points = 0;

        Set<Integer> islands = new HashSet<>();
        List<PieceNode> allPieces = new ArrayList<>();
        ArrayList<Integer>[] playersOnIslands = new ArrayList[numOfIslands + 1];
        for (int i = 0; i < numOfIslands + 1; i++) {
            playersOnIslands[i] = new ArrayList<Integer>();
        }

        int x = 0;
        int len = -1;
        int shortlong = 0; //0 = short, 1 = long
        for (Tile[] k: this.tiles) {
            len = 0;
            shortlong = 0;

            if (x % 2 == 0) {
                System.out.print(" ");
                len = -1;
                shortlong = 1;
            }

            // for each tile
            for (int y = 0; y < k.length + len; y ++) {
                Tile tile = k[y];
                System.out.print(tile.island);
                System.out.print(tile.occupier + 1);
                System.out.print(" ");

                // if tile is occupied by player
                if (tile.occupier == player) {

                    // set of all islands occupied by player
                    islands.add(tile.island);
                    // adds all pieces on the board to a list
                    allPieces = addPieces(tile, x, y, shortlong, allPieces);
                }
                // adds to a counter to the number of pieces which appear per island per player
                if (tile.occupier != -1) {
                    playersOnIslands[tile.island].add(tile.occupier);
                }
            }
            System.out.println("");
            x += 1;
        }
        // processing nodes
        List<Integer> branchLen = new ArrayList<>();
        for (Board.PieceNode node: allPieces) {
            branchLen.add(node.nodeRunner(new HashSet<>(), new ArrayList<>()).size());
        }
        // counting majority for islands
        List<Integer> islandsWon = new ArrayList<>();
        for (int k = 1; k < playersOnIslands.length; k ++ ) {
            var island = playersOnIslands[k];
            var playerCount = 0;

            for (Integer i: island) {
                if (i == player) {
                    playerCount += 1;
                }
            }
            //System.out.println("island:" + k + " num of appearances:" + playerCount + " islandsize" + island.size());
            if (playerCount > island.size()/2) {
                islandsWon.add(k);
            }
        }
        System.out.println("total islands occupied:" + islands);
        System.out.println("Most number of islands in a row:" + Arrays.toString(branchLen.toArray()));
        //System.out.println("Pieces which occupy each island" + Arrays.toString(playersOnIslands));
        System.out.println("islands won" + islandsWon);
        Collections.sort(branchLen);
        return 0;
    }

    public static int count4(int player, int gamestate) {return 0;}
    public static int count3(int player, int gamestate) {return 0;}
    public static int count2(int player, int gamestate) {return 0;}

    // returns an integer array of all points of each player
    public static int[] countAll() {
        return new int[]{0};
    }

    // =====================================================================

    // checks if all resource squares have been occupied or all players have used up their pieces
    public static boolean checkEnd() {
        return true;
    }

    public static class PieceNode {
        int island;
        int x;
        int y;
        int shortlong;
        List<PieceNode> edges;

        public PieceNode(int island, int x, int y, int shortlong) {
            this.island = island;
            this.x = x;
            this.y = y;
            this.shortlong = shortlong;
            this.edges = new ArrayList<>();
        }

        //returns all islands that the edges of this node spans
        public Set<Integer> nodeRunner(Set<Integer> islands, List<PieceNode>  previousNodes ) {
            previousNodes.add(this);

            // for each node in this nodes' edges
            for (int k = 0; k < this.edges.size(); k ++) {
                var getnode = 1;
                // if this node has already appeared before, ignore
                for (PieceNode node: previousNodes) {
                    if (node.equals(this.edges.get(k))) {
                        getnode = 0;
                    }
                }
                // runs noderunner on each of the nodes
                if (getnode == 1) {
                    islands.addAll(this.edges.get(k).nodeRunner(islands, previousNodes));
                }
            }

            return islands;
        }

        @Override
        public String toString() {
            return island + ":" + x + "," + y + ":" + this.edges.size();
        }
    }


    public class Tile {
        // if it is a stone circle, a resource may be generated on the tile
        Boolean isStoneCircle;

        // resource which is currently on the tile
        Resource resource;

        // player who occupies the tile
        // -1 indicates no occupier
        int occupier;

        // village = 1, novillage = 0
        int village;

        // island which the tile is a part of
        // 0 indicates no island
        int island;

        // either water or land
        // land = 1, water = 0
        int type;
        public enum Resource {
            COCO, BBOO, WATR, STON, STAT;
        }


        // sets the occupier of the tile
        // int player -> player
        public void setPlayer(int player) {
            this.occupier = player;
        };

        public Tile() {
            this.isStoneCircle = false;
            this.resource = null;
            this.occupier = -1;
            this.island = 0;
            this.type = 0;
            this.village = 0;
        }

    }
}
