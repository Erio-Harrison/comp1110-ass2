package comp1110.ass2;

import javafx.scene.Node;
import comp1110.ass2.Model;

import java.lang.reflect.Array;
import java.util.*;

public class Board {

    public static List<Player> playerList;
    public static int boardSize;

    public static int numOfIslands;

    public static List<Integer> islandToPoints;
    public static Tile[][] tiles;


    // Generates a board and initialises all the tiles
    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.playerList = new ArrayList<>();
        this.numOfIslands = 0;
        this.tiles = new Tile[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            var len = 0;
            if (row % 2 == 0) {
                len = -1;
            }
                for (int col = 0; col < boardSize + len; col++) {
                    this.tiles[row][col] = new Tile();
                }
        }
        this.islandToPoints = new ArrayList<>();
    }


    public static Tile getTiles(int x,int y) {
        return tiles[x][y];
    }

    // resets board and progresses the game to the next phase
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    public void reset(int gamestate) {
        ArrayList<Tile> stoneCoords = new ArrayList();
        if(gamestate == 1){
            // resources are removed
            for (int k = 0; k < boardSize; k ++) {
                for (int i = 0; i < boardSize; i ++) {
                    tiles[k][i].resource=null;
                    if (tiles[k][i].isStoneCircle){
                        tiles[k][i].village = 0;
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
    // int piece -> int representing the piece 0 = settler 1 = village
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    // int player -> int representing which player is currently in play
    public static boolean setSettler(int x, int y, int player, int piece,int gamestate) {
        if (gamestate == 0){
            if (isValidExploration(x,y,player,piece)){
                tiles[x][y].occupier = player;
                return true;
            }
        }
        else if(gamestate == 1){
            if (isValidSettle(x,y,player,piece)){
                tiles[x][y].occupier = player;
                return true;
            }
        }
        return false;
    }
    // checks if a tile is a valid tile for settler to be placed.
    // int x -> x coordinate of tile
    // int y -> y coordinate of tile
    // int player -> player
    // int piece -> int representing the piece 0 = settler 1 = village
    //
    public static boolean isValidSettle (int x, int y, int player, int piece){
        // Out of bounds
        if (x < 0 || x > boardSize || y < 0 || y > boardSize) {
            return false;
        }
        // Occupied
        if (tiles[x][y].occupier != -1) {
            return false;
        }
        // if piece is settler
        if (piece == 0) {
            if (tiles[x][y].type == 1) {
                ArrayList<Tile> adjacent = adjacentTiles(x,y);
                if (!containsPlayerTiles(player,adjacent)){
                    return false;
                }
            }
        }
        else {
            // can't place village
            return false;
        }
        return true;
    }

    // checks if a tile is a valid tile for settler to be placed.
    // int x -> x coordinate of tile
    // int y -> y coordinate of tile
    // int player -> player
    // int piece -> int representing the piece 0 = settler 1 = village
    //

    public static boolean isValidSettlement() {
        return false;
    }
    public static boolean isValidExploration (int x, int y, int player, int piece) {
        // Out of bounds
        if (x < 0 || x > boardSize || y < 0 || y > boardSize) {
            return false;
        }

        // Occupied
        if (tiles[x][y].occupier != -1) {
            return false;
        }
        // if piece is settler
        if (piece == 0) {
            // tile is a water tile
            if (tiles[x][y].type == 0) {
                return true;
            } else {
                ArrayList<Tile> adjacent = adjacentTiles(x,y);
                if (!containsPlayerTiles(player,adjacent)) {
                    return false;
                }
            }

            // village piece
        } else {
            // Village can't be place on water
            if (tiles[x][y].type == 0) {
                return false;
            }

            ArrayList<Tile> adjacent = adjacentTiles(x,y);
            if (!containsPlayerTiles(player,adjacent)) {
                return false;
            }
        }

        return true;
    }


    // check if a players neighbours tiles contains one that is occupied.
    public static Boolean containsPlayerTiles(int player, ArrayList<Tile> adjacent) {
        if (adjacent.isEmpty()) {
            return false;
        }
        for (Tile n : adjacent) {
            if (n.occupier == player) {
                return true;
            }
        }
        return false;
    }
    // helper method to get neighbouring pieces

    public static ArrayList<Tile> adjacentTiles(int col, int row) {
        ArrayList<Tile> adjacent = new ArrayList<>();
        if (col < 0 || (row % 2 == 0 && col > boardSize - 2) || (row % 2 != 0 && col > boardSize - 1)  || row < 0 || row > boardSize) {
            return adjacent;
        }
        // check Left
        if (col > 0) {
            adjacent.add(tiles[col-1][row]);
        }
        // check Right
        if (row % 2 == 0) {
            if (col < boardSize-2) {
                adjacent.add(tiles[col+1][row]);
            }
        } else if (row % 2 != 0) {
            if (col < boardSize-1) {
                adjacent.add(tiles[col+1][row]);
            }
        }

        // Check Up
        if (row > 0) {
            if (row % 2 == 0) {
                adjacent.add(tiles[col+1][row-1]);
                adjacent.add(tiles[col][row-1]);
            } else if (row % 2 != 0) {
                if (col == 0) {
                    adjacent.add(tiles[col][row-1]);
                } else if (col == boardSize - 1) {
                    adjacent.add(tiles[col-1][row-1]);
                } else {
                    adjacent.add(tiles[col-1][row-1]);
                    adjacent.add(tiles[col][row-1]);

                }

            }

        }
        // Check Down
        if (row < boardSize - 1) {
            if (row % 2 == 0) {
                adjacent.add(tiles[col][row+1]);
                adjacent.add(tiles[col+1][row+1]);
            } else if (row % 2 != 0) {
                if (col == 0) {
                    adjacent.add(tiles[col][row+1]);
                } else if (col == boardSize - 1) {
                    adjacent.add(tiles[col-1][row+1]);
                } else {
                    adjacent.add(tiles[col-1][row+1]);
                    adjacent.add(tiles[col][row+1]);

                }

            }

        }





        return adjacent;
    }



    public int setResource(String[] split,Tile.Resource resource, int ucrPosition, String resourceChar) {
        for (int k = ucrPosition; !split[k].equals(resourceChar); k++) {
            String[] coord =  split[k].split(",");
            this.tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = resource;
            ucrPosition += 1;
        }
        return ucrPosition + 1;
    }

    public int resourcesPoints(int player) {
        var points = 0;
        for (Player k: this.playerList) {
            if (k.id == player) {
                var bonusP = 1;
                for (int i = 0; i < k.resources.length - 1; i++) {
                    if (k.resources[i] >= 4) {points += 20;}
                    else if (k.resources[i] == 3) {points += 10;}
                    else if (k.resources[i] == 2) {points += 5;}

                    if (k.resources[i] == 0) {
                        bonusP = 0;}
                }
                //statuettes
                points += k.resources[k.resources.length - 1] * 4 + (bonusP * 10);
            }
        }
        return points;
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
                Integer[] rsrcs = new Integer[5];
                for (int k = 3; k < 8; k ++) {
                    rsrcs[k - 3] = Integer.parseInt(split[k]);
                }
                this.playerList.add(new Player(Integer.parseInt(split[1]), Integer.parseInt(split[2]), rsrcs));
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
                this.islandToPoints.add(Integer.parseInt(split[1]));
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

    // counts the total points obtained by a particular player
    // int player -> player the board is currently checking
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    //

    public int countPoints(int player) {
        int points = 0;

        PlayerPointCounter pointCounter = new PlayerPointCounter(player, this.tiles, this.numOfIslands);
        points += pointCounter.islandsCounter();
        points += pointCounter.majorityIslandsCounter(this.islandToPoints);
        points += pointCounter.linkCounter();
        points += resourcesPoints(player);

        return points;
    }

    // returns an integer array of all points of each player
    public static int[] countAll() {
        return new int[]{0};
    }

    // =====================================================================

    // checks if all resource squares have been occupied or all players have used up their pieces
    public static boolean checkEnd() {
        return true;
    }

    //used to store the points and resources of each player
    public class Player {
        int id;
        int points;
        Integer[] resources;

        public Player(int id, int points, Integer[] resources) {
            this.id = id;
            this.points = points;
            this.resources = resources;
        }
    }

    public static class PlayerPointCounter {
        Integer player;
        Integer numOfIslands;
        Set<Integer> islands;
        List<PieceNode> allPieces;
        ArrayList<Integer>[] playersOnIslands;

        public PlayerPointCounter(int player, Tile[][] tiles, int numOfIslands) {
            this.player = player;
            this.numOfIslands = numOfIslands;
            this.islands = new HashSet<>();
            this.allPieces = new ArrayList<>();
            this.playersOnIslands = new ArrayList[numOfIslands + 1];

            for (int i = 0; i < numOfIslands + 1; i++) {
                playersOnIslands[i] = new ArrayList<>();
            }

            int len;
            int x = 0;

            for (Tile[] k: tiles) {
                len = 0;
                if (x % 2 == 0) {len = -1;}

                for (int y = 0; y < k.length + len; y ++) {
                    Tile tile = k[y];

                    // if tile is occupied by player
                    if (tile.occupier == player) {
                        // set of all islands occupied by (number of islands occupied)
                        if (tile.island != 0) {islands.add(tile.island);};

                        // adds all pieces on the board to a list (linked islands)
                        addNodePieces(tile, x, y, len);
                    }
                    // adds to a counter to the number of pieces which appear per island per player (majority islands)
                    if (tile.occupier != -1) {
                        playersOnIslands[tile.island].add(tile.occupier);
                    }
                }
                x += 1;
            }
        }

        public List<PieceNode> addNodePieces(Tile tile, int x, int y, int shortlong) {
            PieceNode currentNode = new PieceNode(tile.island, x, y, shortlong);
            this.allPieces.add(currentNode);
            for (PieceNode node: this.allPieces) {
                if (node.x == x && node.y - y == 1
                        || node.x == x && node.y - y == -1
                        || node.x - x == 1 && node.y == y
                        || node.x - x == -1 && node.y == y
                        || node.x == x + 1 + 2*(shortlong) && node.y - y == 1
                        || node.x + 1 + 2*(shortlong) == x && node.y - y == -1) {
                    node.edges.add(currentNode);
                    currentNode.edges.add(node);
                }
            }
            return allPieces;
        }

        public int islandsCounter() {
            if (islands.size() >= 8) {return 20;}
            else if (islands.size() == 7) {return 10;}
            return 0;
        }

        public int linkCounter() {
            // processing nodes
            List<Integer> branchLen = new ArrayList<>();
            for (Board.PieceNode node: this.allPieces) {
                branchLen.add(node.nodeRunner(new HashSet<>(), new ArrayList<>()).size());
            }
            Collections.sort(branchLen);
            if (branchLen.size() >= 1) {
                return branchLen.get(branchLen.size() - 1)*5;
            }
            return 0;
        }

        public int majorityIslandsCounter(List<Integer> islandToPoints) {
            Integer points = 0;
            List<Integer> islandsWon = new ArrayList<>();
            for (int k = 1; k < playersOnIslands.length; k ++ ) {
                var island = playersOnIslands[k];
                var playerCount = 0;

                for (Integer i: island) {
                    if (i == player) {
                        playerCount += 1;
                    }
                }
                if (island.size() > 0) {
                    if (playerCount > island.size()/2) {
                        islandsWon.add(islandToPoints.get(k - 1));
                    }
                    else if (playerCount == (float) (island.size())/2) {
                        islandsWon.add(islandToPoints.get(k - 1)/2);
                    }
                }
            }

            for (int k = 0; k < islandsWon.size(); k ++) {
                points += islandsWon.get(k);
            }
            return points;
        }
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
            if (this.island != 0) {
                islands.add(this.island);
            }

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
        int occupier;
        // if it is a stone circle, a resource may be generated on the tile
        Boolean isStoneCircle;

        // resource which is currently on the tile
        Resource resource;

        // player who occupies the tile
        // -1 indicates no occupier


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
