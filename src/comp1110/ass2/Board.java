package comp1110.ass2;

import javafx.scene.Node;

import java.util.*;

public class Board {

    int boardSize;
    Tile[][] tiles;

    // Generates a board and initialises all the tiles
    public Board(int boardsize) {
        this.boardSize = boardsize;
        this.tiles =  new Tile[boardSize][boardSize];
        for (int k = 0; k < boardsize; k ++) {
            for (int i = 0; i < boardsize; i ++) {
                tiles[k][i] = new Tile();
            }
        }

    }


    // resets board and progresses the game to the next phase
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    public static void reset(int gamestate) {
    }

    // =====================================================================

    // calls isValid to check if a tile is a valid position. If true, replaces tile's fields with
    // relevant values
    // int x -> x coordinate of tile
    // int y -> y coordinate of tile
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    // int player -> int representing which player is currently in play
    public static boolean setSettler(int x, int y, int gamestate, int player) {
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

    // =====================================================================

    // counts the total points obtained by a particular player
    // int player -> player the board is currently checking
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    //

    public int countPoints(int player, int gamestate) {
        //**Total Islands**
        //
        //Players with pieces on eight or more islands score 20 points.
        //Players with pieces on exactly seven islands score 10 points.
        //Otherwise, 0 points are scored.
        //
        //A (potentially) branching path of neighbouring settlers and villages
        //belonging to a player forms a chain. Players earn points from the chain
        //of their pieces which links the most islands. Players earn 5 points
        //per linked island in this chain.
        //
        //The player with the most pieces on an island scores
        //the points indicated on the board for that island.
        //In the case of a tie, the points are divided evenly
        //between the tied players, rounding down.
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
        if (gamestate == 0) {
            int x = 0;
            int len = -1;
            int shortlong = 0; //0 = short, 1 = long
            for (Tile[] k: this.tiles) {
                len = 0;
                shortlong = 1;

                if (x % 2 == 0) {
                    System.out.print(" ");
                    len = -1;
                    shortlong = 0;
                }

                // for each tile
                for (int y = 0; y < k.length + len; y ++) {
                    Tile tile = k[y];
                    System.out.print(shortlong);
                    System.out.print(" ");

                    // if tile is occupied by player
                    if (tile.occupier == player) {

                        // set of all islands occupied by player
                        islands.add(tile.island);

                        // checking for links code
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
                    }
                }
                System.out.println("");
                x += 1;
            }

            Set<Integer> branchLen = new HashSet<>();
            for (PieceNode node: allPieces) {
                branchLen.add(node.nodeRunner(1, new ArrayList<>()));
            }
            System.out.println("Longest string:" + Arrays.toString(branchLen.toArray()));
        }
        else if (gamestate == 1) {
        }
        System.out.println(Arrays.toString(islands.toArray()));
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

        //returns longest chain in this node
        public int nodeRunner(int length, List<PieceNode>  previousNodes ) {
            List<Integer> allengths = new ArrayList<>();
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
                    allengths.add(this.edges.get(k).nodeRunner(length + 1, previousNodes));
                }
            }

            // if this is an end node
            if (allengths.size() == 0) {
                return length;
            }

            Collections.sort(allengths);
            return allengths.get(0);
        }

        @Override
        public String toString() {
            return island + ":" + x + "," + y + ":" + this.edges.size();
        }


    }


    public static class Tile {
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
        public static void setPlayer(int player) {
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
