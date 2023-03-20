package comp1110.ass2;

public class Board {

    int boardSize = 0;
    Tile[] tiles = new Tile[boardSize];

    // Generates a board and initialises all the tiles
    public Board() {
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

    // =====================================================================

    // counts the total points obtained by a particular player
    // int player -> player the board is currently checking
    // int gamestate -> int representing whether it is exploration(0) or settling(1) phase
    public static int countPoints(int player, int gamestate) {
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


    public static class Tile {
        // if it is a stone circle, a resource may be generated on the tile
        Boolean isStoneCircle;

        // resource which is currently on the tile
        Resource resource;

        // player who occupies the tile
        int occupier;

        // island which the tile is a part of
        int island;

        // either water or land
        int type;
        public enum Resource {
            COCO, BBOO, WATR, STON, STAT;
        }


        // sets the occupier of the tile
        // int player -> player
        public static void setPlayer(int player) {
        };

        public Tile(Boolean isStoneCircle, Resource resource, int occupier, int island, int type) {
            this.isStoneCircle = isStoneCircle;
            this.resource = resource;
            this.occupier = occupier;
            this.island = island;
            this.type = type;
        }
    }
}
