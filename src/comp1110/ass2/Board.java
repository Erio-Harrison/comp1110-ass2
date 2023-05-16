package comp1110.ass2;

import java.lang.reflect.Array;
import java.util.*;

public class Board {
    // List of players in a game
    public List<Player> playerList;
    // the size of the board
    public int boardSize;
    // number of islands
    public int numOfIslands;
    // islands to points
    public List<Integer> islandToPoints;
    // array representing the tiles of the board
    public static Tile[][] tiles;


    /**
     * @param boardSize - integer representing the size of a board
     */
    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.playerList = new ArrayList<>();
        this.numOfIslands = 0;
        tiles = new Tile[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            var len = 0;
            if (row % 2 == 0) {
                len = -1;
            }
            for (int col = 0; col < boardSize + len; col++) {
                tiles[row][col] = new Tile();
            }
        }
        this.islandToPoints = new ArrayList<>();
    }

    /**
     * Returns the winner out of the playerList arraylist.
     */
    public Player declareWinner() {
        Optional<Player> max = playerList.stream().max(Comparator.comparing(Player::getPoints).thenComparing(Player::resourcesCount));
        return max.get();
    }

    /**
     * Assigns random resources to all stone coordinate positions
     * @param stoneCoords - coordinates of all stone circle locations
     */
    public void assignRanResources(List<Tile> stoneCoords) {
        assignToStone(6, Tile.Resource.WATR,stoneCoords);
        assignToStone(6, Tile.Resource.BBOO,stoneCoords);
        assignToStone(6, Tile.Resource.STON,stoneCoords);
        assignToStone(6, Tile.Resource.COCO,stoneCoords);
        assignToStone(8, Tile.Resource.STAT,stoneCoords);
    }

    /**
     * Assigns random resources to all stone coordinate positions
     * @param stoneCoords - coordinates of all stone circle locations
     */
    public void assignToStone(int count,Tile.Resource resource, List<Tile> stoneCoords){
        for (int i = 0; i < count;i++){
            int random = (int)(Math.random() * stoneCoords.size());
            stoneCoords.get(random).resource = resource;
            stoneCoords.remove(random);
        }
    }


    // checks if a tile is a valid tile for settler to be placed.
    // int x -> x coordinate of tile
    // int y -> y coordinate of tile
    // int player -> player
    // int piece -> int representing the piece 0 = settler 1 = village
    //
    public boolean isValidMove(int x, int y, int player, int piece, int gamestate) {
        int len = 0;
        if (x % 2 == 0) {len = -1;}
        int[] pos = posCreate(x, y);

        if (x < 0 || x > boardSize - 1 || y < 0 || y > boardSize - 1 + len) {return false;}
        if (tiles[x][y] == null || tiles[x][y].occupier != -1) {return false;}

        // if piece is settler
        if (gamestate == 1) {
            if (piece == 0) {
                if (this.getPlayer(player).settlers >= 30 - ((this.playerList.size() - 2) * 5)) {return false;}
                return checkOccupier(pos, x, y, player);
            }
            else {
                return false;
            }
        }
        else {
            if (piece == 0) {
                if (this.getPlayer(player).settlers >= 30 - ((this.playerList.size() - 2) * 5)) {return false;}
                if (tiles[x][y].type == 0) {return true;}
                return checkOccupier(pos,  x, y, player);
            }
            // village piece
            else {
                if (this.getPlayer(player).villages >= 5) {return false;}
                if (tiles[x][y].type == 0) {return false;}
                return checkOccupier(pos, x, y, player);
            }
        }
    }

    // returns true if there's a piece adjacent to it that has similar occupier
    public Boolean checkOccupier(int[] pos, int a, int b, int currentPlayerId) {
        var evenRow = 0;
        if (a % 2 == 0) {
            evenRow = 1;
        }

        if ((pos[0] == -1 || pos[0] == 1)) {
            if (pos[1] == 0) {
                if (tiles[a][b - 1].occupier == currentPlayerId ||
                        tiles[a][b + 1].occupier == currentPlayerId) {return true;}
            }
            else {
                if (tiles[a][b - pos[1]].occupier == currentPlayerId) {return true;}
            }

            if (evenRow == 1 || pos[1] == 0) {
                return tiles[a - pos[0]][b].occupier == currentPlayerId ||
                        tiles[a - pos[0]][b + 1 + (evenRow - 1) * 2].occupier == currentPlayerId;
            }
            else {
                return tiles[a - pos[0]][b].occupier == currentPlayerId;
            }
        }

        else if (pos[0] == 0 && (pos[1] == -1||pos[1] == 1)) {
            if (tiles[a][b-pos[1]].occupier == currentPlayerId) {return true;}

            if (pos[1] == -1 || evenRow == 1) {
                if (tiles[a-1][b].occupier == currentPlayerId ||
                        tiles[a+1][b].occupier == currentPlayerId) {return true;}

            }
            if (evenRow == 1 || pos[1] == 1) {
                return tiles[a - 1][b + 1 + (evenRow - 1) * 2].occupier == currentPlayerId ||
                        tiles[a + 1][b + 1 + (evenRow - 1) * 2].occupier == currentPlayerId;
            }
        }
        else {
            return tiles[a - 1][b].occupier == currentPlayerId
                    || tiles[a + 1][b].occupier == currentPlayerId
                    || tiles[a - 1][b + (2 * (evenRow) - 1)].occupier == currentPlayerId
                    || tiles[a + 1][b + (2 * (evenRow) - 1)].occupier == currentPlayerId
                    || tiles[a][b + 1].occupier == currentPlayerId
                    || tiles[a][b - 1].occupier == currentPlayerId;
        }

        return false;
    }

    // Authored by Tay Shao An
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

    // Authored by Tay Shao An
    // used to set attributes for each tile on a board based on a string state
    // attribute is assigned as follows:
    // isStoneCircle = 0, resource = 1, occupier = 2, island = 3, type = 4, village = 5;
    public void setBoardAttributes(String state, int attribute, int info)  {
        String[] split = state.split(" ");
        switch (attribute) {
            case 0 -> { //isStoneCircle
                for (int k = 1; k < split.length; k++) {
                    String[] coord = split[k].split(",");
                    tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].isStoneCircle = true;
                }
            }
            case 1 -> { //resource
                int ucrPosition = 2;
                ucrPosition = setResource(split, Tile.Resource.COCO, ucrPosition, "B");
                ucrPosition = setResource(split, Tile.Resource.BBOO, ucrPosition, "W");
                ucrPosition = setResource(split, Tile.Resource.WATR, ucrPosition, "P");
                ucrPosition = setResource(split, Tile.Resource.STON, ucrPosition, "S");
                for (int k = ucrPosition; k < split.length; k++) {
                    String[] coord = split[k].split(",");
                    tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = Tile.Resource.STAT;
                }
            }
            case 2 -> { //occupier
                Integer[] rsrcs = new Integer[5];
                for (int k = 3; k < 8; k++) {rsrcs[k - 3] = Integer.parseInt(split[k]);}
                Player currentPlayer = new Player(Integer.parseInt(split[1]), Integer.parseInt(split[2]), rsrcs);
                this.playerList.add(currentPlayer);
                if (split[split.length - 2].equals("S")) {return;}
                int pos = 9;
                for (int l = pos; !split[l].equals("T"); l++) {
                    String[] settlers = split[l].split(",");
                    tiles[Integer.parseInt(settlers[0])][Integer.parseInt(settlers[1])].occupier = info;
                    pos += 1;
                    currentPlayer.settlers += 1;
                }
                pos += 1;
                if (split[split.length - 1].equals("T")) {return;}

                // retrieve all villager coordinates
                for (int l = pos; l < split.length; l++) {
                    String[] settlers = split[l].split(",");
                    tiles[Integer.parseInt(settlers[0])][Integer.parseInt(settlers[1])].occupier = info;
                    tiles[Integer.parseInt(settlers[0])][Integer.parseInt(settlers[1])].village = 1;
                    currentPlayer.villages += 1;
                }
            }
            case 3 -> { //island
                this.islandToPoints.add(Integer.parseInt(split[1]));
                for (int k = 2; k < split.length; k++) {
                    String[] coord = split[k].split(",");
                    tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].island = info;
                }
            }
            case 4 -> { //type
                for (int k = 2; k < split.length; k++) {
                    String[] coord = split[k].split(",");
                    tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].type = 1;
                }
            }
            case 5 -> { //village
                for (int k = 1; k < split.length; k++) {
                    String[] coord = split[k].split(",");
                    tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].village = 1;
                }
            }
        }
    }

    // Authored by Tay Shao An
    public int setResource(String[] split,Tile.Resource resource, int ucrPosition, String resourceChar) {
        for (int k = ucrPosition; !split[k].equals(resourceChar); k++) {
            String[] coord =  split[k].split(",");
            tiles[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].resource = resource;
            ucrPosition += 1;
        }
        return ucrPosition + 1;
    }

    public ArrayList<int[]> getOccupiedTiles(int player, int village) {
        ArrayList<int[]> occupied = new ArrayList<>();
        for (int k = 0; k < boardSize; k ++) {
            for (int i = 0; i < boardSize; i ++) {
                if (tiles[k][i] != null) {
                    if (tiles[k][i].occupier == player && tiles[k][i].village == village){
                        occupied.add(new int[] {k, i});
                    }
                }
            }
        }
        return occupied;
    }

    public List<Tile> getStoneRsrcTiles() {
        List<Tile> stoneCoords = new ArrayList<>();
        for (int k = 0; k < boardSize; k ++) {
            for (int i = 0; i < boardSize; i ++) {
                if (tiles[k][i] != null) {
                    if (tiles[k][i].isStoneCircle){
                        stoneCoords.add(tiles[k][i]);
                    }
                }
            }
        }
        return stoneCoords;
    }

    public ArrayList<int[]> getStoneCoordinates() {
        ArrayList<int[]> stoneCoords = new ArrayList<>();
        for (int k = 0; k < boardSize; k ++) {
            for (int i = 0; i < boardSize; i ++) {
                if (tiles[k][i] != null) {
                    if (tiles[k][i].isStoneCircle){
                        stoneCoords.add(new int[] {k, i});
                    }
                }
            }
        }
        return stoneCoords;
    }

    // removes pieces at end of a phase and returns it to the player/
    public void removePieces() {
        for (int k = 0; k < boardSize; k ++) {
            for (int i = 0; i < boardSize; i ++) {
                if (tiles[k][i] != null) {
                    tiles[k][i].resource = null;
                    // village on stone circle
                    if (tiles[k][i].occupier != -1 && tiles[k][i].village == 1 && tiles[k][i].isStoneCircle) {
                        playerList.get(tiles[k][i].occupier).villages -= 1;
                        tiles[k][i].occupier = -1;
                        tiles[k][i].village = 0;
                    }

                    // settler
                    if (tiles[k][i].occupier != -1 && tiles[k][i].village == 0) {
                        playerList.get(tiles[k][i].occupier).settlers -= 1;
                        tiles[k][i].occupier = -1;

                    }
                }
            }
        }
    }

    // Authored by Tay Shao An
    // counts the total points obtained by a particular player
    // int player -> player the board is currently checking
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    public int countPoints(int player) {
        int points = 0;
        PlayerPointCounter pointCounter = new PlayerPointCounter(player, tiles, this.numOfIslands);
        points += pointCounter.islandsCounter();
        points += pointCounter.majorityIslandsCounter(this.islandToPoints);
        points += pointCounter.linkCounter();
        points += resourcesPoints(player);

        return points;
    }

    public Player getPlayer(int id) {
        for (Player p: this.playerList) {if (p.id == id) {return p;}}
        return null;
    }

    public int[] posCreate(int x, int y) {
        int len = 0;
        if (x % 2 == 0) {len = -1;}

        int[] pos = {0, 0};
        if (x - 1 == -1) {pos[0] = -1;}
        else if (x + 1 == boardSize) {pos[0] = 1;}
        if (y - 1 == -1) {pos[1] = -1;}
        else if (y + 1 == boardSize + len ) {pos[1] = 1;}
        return  pos;
    }

    public static int resourceToInt(Tile.Resource rsrc) {
        switch (rsrc) {
            case COCO -> {return 0;}
            case BBOO -> {return 1;}
            case WATR -> {return 2;}
            case STON -> {return 3;}
            case STAT -> {return 4;}
        }
        return -1;
    }

    // checks if all resource squares have been occupied or all players have used up their pieces
    public boolean noValidMoves(int gameState) {
        if (gameState == 0) {
            for (Player player : playerList) {
                if (player.settlers != 30 || player.villages != 5) {return false;}
            }
        }
        if (gameState == 1) {
            for (Player player : playerList) {
                if (player.settlers != 30) {return false;}
            }
        }
        return true;
    }

    public boolean allResourcesCollected() {
        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row < boardSize; row++) {
                Tile curr = tiles[col][row];
                if (curr != null) {
                    if (curr.isStoneCircle) {
                        if (curr.resource != null) {
                            if (!curr.resource.equals(Tile.Resource.STAT)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Authored by Tay Shao An
     * Stores current state of a player
     */

    //used to store the points and resources of each player
    public static class Player {
        public int id;

        public int points;

        public Integer[] resources;

        public int settlers;

        public int villages;

        public Player(int id, int points, Integer[] resources) {
            this.id = id;
            this.points = points;
            this.resources = resources;
            this.settlers = 0;
            this.villages = 0;
        }
        public void resetResources() {
            this.resources = new Integer[]{0, 0, 0, 0, 0};
        }

        public int getPoints() {
            return points;
        }
        public int resourcesCount() {
            return Arrays.stream(resources)
                    .mapToInt(Integer::intValue)
                    .sum();
        }
    }

    /**
     * Authored by Tay Shao An
     * Stores attributes about a certain tile
     */
    public static class Tile {
        // if it is a stone circle, a resource may be generated on the tile
        Boolean isStoneCircle;

        // resource which is currently on the tile
        Resource resource;

        // player who occupies the tile
        // -1 indicates no occupier
        public int occupier;

        // village = 1, settler = 0
        public int village;

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
        // initialises the tile.
        public Tile() {
            this.isStoneCircle = false;
            this.resource = null;
            this.occupier = -1;
            this.island = 0;
            this.type = 0;
            this.village = 0;
        }
        public Resource getResource() {
            return resource;
        }
    }


    public static String toURL(Tile tile) {

        if (tile != null) {
            if (tile.occupier != -1 && tile.village == 0) {return "settler" + tile.occupier + ".png";};
            if (tile.occupier != -1 && tile.village == 1) {return "village" + tile.occupier + ".png";};
            if (tile.isStoneCircle) {return "stone.png";};
            if (tile.type == 1) {return "grass.png";};
        }
        return "water.png";
    }
}

