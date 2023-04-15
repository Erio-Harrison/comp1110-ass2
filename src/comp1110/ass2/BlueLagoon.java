package comp1110.ass2;

import java.util.*;
import java.util.stream.Collectors;


public class BlueLagoon {
    // The Game Strings for five maps have been created for you.
    // They have only been encoded for two players. However, they are
    // easily extendable to more by adding additional player statements.
    public static final String DEFAULT_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 1,4 2,0 2,1; i 6 0,5 0,6 0,7 1,6 1,7 1,8 2,6 2,7 2,8 3,7 3,8; i 6 7,12 8,11 9,11 9,12 10,10 10,11 11,10 11,11 11,12 12,10 12,11; i 8 0,9 0,10 0,11 1,10 1,11 1,12 2,10 2,11 3,10 3,11 3,12 4,10 4,11 5,11 5,12; i 8 4,0 5,0 5,1 6,0 6,1 7,0 7,1 7,2 8,0 8,1 8,2 9,0 9,1 9,2; i 8 10,3 10,4 11,0 11,1 11,2 11,3 11,4 11,5 12,0 12,1 12,2 12,3 12,4 12,5; i 10 3,3 3,4 3,5 4,2 4,3 4,4 4,5 5,3 5,4 5,5 5,6 6,3 6,4 6,5 6,6 7,4 7,5 7,6 8,4 8,5; i 10 5,8 5,9 6,8 6,9 7,8 7,9 7,10 8,7 8,8 8,9 9,7 9,8 9,9 10,6 10,7 10,8 11,7 11,8 12,7 12,8; s 0,0 0,5 0,9 1,4 1,8 1,12 2,1 3,5 3,7 3,10 3,12 4,0 4,2 5,9 5,11 6,3 6,6 7,0 7,8 7,12 8,2 8,5 9,0 9,9 10,3 10,6 10,10 11,0 11,5 12,2 12,8 12,11; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String WHEELS_GAME = "a 13 2; c 0 E; i 5 0,1 0,2 0,3 0,4 1,1 1,5 2,0 2,5 3,0 3,6 4,0 4,5 5,1 5,5 6,1 6,2 6,3 6,4; i 5 0,8 0,9 0,10 1,8 1,11 2,7 2,11 3,8 3,11 4,8 4,9 4,10; i 7 8,8 8,9 8,10 9,8 9,11 10,7 10,11 11,8 11,11 12,8 12,9 12,10; i 7 10,0 10,1 10,4 10,5 11,0 11,2 11,3 11,4 11,6 12,0 12,1 12,4 12,5; i 9 2,2 2,3 3,2 3,4 4,2 4,3; i 9 2,9; i 9 6,6 6,7 6,8 6,9 6,10 6,11 7,6 8,0 8,1 8,2 8,3 8,4 8,5; i 9 10,9; s 0,1 0,4 0,10 2,2 2,3 2,9 2,11 3,0 3,2 3,4 3,6 4,2 4,3 4,10 6,1 6,4 6,6 6,11 8,0 8,5 8,8 8,10 10,0 10,5 10,7 10,9 10,11 11,3 12,1 12,4 12,8 12,10; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String FACE_GAME = "a 13 2; c 0 E; i 6 0,0 0,1 0,2 0,3 0,4 0,5 0,6 0,7 0,8 0,9 0,10 0,11 1,0 1,12 2,0 2,11 3,0 3,12 4,0 4,11 5,0 5,12 6,0 6,11 7,0 7,12 8,0 8,11 9,0 9,12 10,0 10,11 11,0 11,12 12,0 12,1 12,2 12,3 12,4 12,5 12,6 12,7 12,8 12,9 12,10 12,11; i 6 2,4 2,5 2,6 2,7; i 9 4,4 4,5 4,6 4,7; i 9 6,5 6,6 7,5 7,7 8,5 8,6; i 12 2,2 3,2 3,3 4,2 5,2 5,3 6,2 7,2 7,3; i 12 2,9 3,9 3,10 4,9 5,9 5,10 6,9 7,9 7,10; i 12 9,2 9,10 10,2 10,3 10,4 10,5 10,6 10,7 10,8 10,9; s 0,3 0,8 1,0 1,12 2,2 2,4 2,7 2,9 4,2 4,5 4,6 4,9 5,0 5,12 6,2 6,5 6,6 6,9 8,0 8,5 8,6 8,11 9,2 9,10 10,3 10,5 10,6 10,8 11,0 11,12 12,4 12,7; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SIDES_GAME =  "a 7 2; c 0 E; i 4 0,0 0,1 0,2 0,3 1,0 1,1 1,2 1,3 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 4,0 4,1 4,2 4,3 5,0 5,1 5,2 5,3 6,0 6,1 6,2 6,3; i 20 0,5 1,5 1,6 2,5 3,5 3,6 4,5 5,5 5,6 6,5; s 0,0 0,1 0,2 0,3 1,1 1,2 1,3 1,5 1,6 2,0 2,1 2,2 2,3 3,0 3,1 3,2 3,3 3,5 3,6 4,0 4,1 4,2 4,3 5,1 5,2 5,3 5,5 5,6 6,0 6,1 6,2 6,3; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";
    public static final String SPACE_INVADERS_GAME = "a 23 2; c 0 E; i 6 0,2 0,7 1,3 1,7 2,2 2,3 2,4 2,5 2,6 2,7 3,2 3,4 3,5 3,6 3,8 4,0 4,1 4,2 4,3 4,4 4,5 4,6 4,7 4,8 4,9 5,0 5,1 5,3 5,4 5,5 5,6 5,7 5,9 5,10 6,0 6,2 6,7 6,9 7,3 7,4 7,6 7,7; i 6 0,14 0,19 1,15 1,19 2,14 2,15 2,16 2,17 2,18 2,19 3,14 3,16 3,17 3,18 3,20 4,12 4,13 4,14 4,15 4,16 4,17 4,18 4,19 4,20 4,21 5,12 5,13 5,15 5,16 5,17 5,18 5,19 5,21 5,22 6,12 6,14 6,19 6,21 7,15 7,16 7,18 7,19; i 6 17,9 18,8 18,9 19,6 19,7 19,8 19,9 19,10 19,11 19,12 20,5 20,6 20,7 20,8 20,9 20,10 20,11 20,12 21,5 21,6 21,7 21,8 21,9 21,10 21,11 21,12 21,13 22,5 22,6 22,7 22,8 22,9 22,10 22,11 22,12; i 8 12,3 12,5 13,3 13,4 13,5 13,6 14,1 14,2 14,3 14,4 14,5 15,1 15,2 15,3 16,1 16,2; i 8 12,17 12,18 12,19 13,17 13,18 13,19 13,20 14,17 14,18 14,19 14,20 15,19 15,20 15,21 16,19 16,20; i 8 13,14 14,13 14,14 15,13 15,14 15,15 16,13 16,14; i 8 14,7 15,7 15,8 16,7; i 10 8,9 9,9 10,9 11,9; i 10 8,12 9,13 10,12 11,13; i 10 9,1 10,1 11,1 12,1; i 10 9,22 10,21 11,22 12,21; i 10 13,10 14,10 15,10; i 10 17,0 18,0 19,0 20,0; i 10 17,16 18,16 19,16 20,16; s 0,2 0,7 0,14 0,19 3,5 3,17 6,0 6,9 6,12 6,21 7,4 7,6 7,16 7,18 11,9 11,13 12,1 12,19 12,21 13,10 15,2 15,8 15,14 15,20 17,9 18,8 18,9 20,0 20,16 21,6 21,9 21,12; r C B W P S; p 0 0 0 0 0 0 0 S T; p 1 0 0 0 0 0 0 S T;";

    /**
     * Check if the string encoding of the game state is well-formed.
     * Note that this does not mean checking that the state is valid
     * (represents a state that players could reach in game play),
     * only that the string representation is syntactically well-formed.+
     * <p>
     * only that the string representation is syntactically well-formed.
     * <p>.
     * A description of the state string will be included in README.md
     * in an update of the project after D2B is complete.
     *
     * @param stateString a string representing a game state
     * @return true if stateString is well-formed and false otherwise
     */
    public static boolean isStateStringWellFormed(String stateString){
        var count = 0;
        int k = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == ';') {
                count++;
            }
        }

        String[] statelist = stateString.split("; ", count);
        if (statelist.length <= 5) {
            return false;
        }


        // Game Arrangement Statement
        String gArrangement = statelist[0];
        if (!gArrangement.matches("^a \\d{1,} \\d{1,}")) {
            return false;
        }

        // Current State Statement
        String csState = statelist[1];
        if (!csState.matches("^c \\d{1,} [ES]")) {
            return false;
        }

        // Island State Statement
        ArrayList iState = (ArrayList) Arrays.stream(statelist).collect(Collectors.partitioningBy(n -> n.charAt(0) == 'i')).values().toArray()[1];
        for (k = 0; k < iState.size(); k++) {
            if (!((String) iState.get(k)).matches("^i \\d{1,}( \\d{1,},\\d{1,})+")) {
                return false;
            }
        }

        // Stones Statement
        String stoneState = (statelist[iState.size() + 2]);
        if (!stoneState.matches("^s( \\d{1,},\\d{1,})+")) {
            return false;
        }

        // Unclaimed Resources Statement
        String ucrState = (statelist[iState.size() + 3]);
        if (!ucrState.matches("^r C( \\d{1,},\\d{1,}){0,} B( \\d{1,},\\d{1,}){0,} W( \\d{1,},\\d{1,}){0,} P( \\d{1,},\\d{1,}){0,} S( \\d{1,},\\d{1,}){0,}")) {
            return false;
        }

        // Players Statement
        ArrayList playState = (ArrayList) Arrays.stream(statelist).collect(Collectors.partitioningBy(n -> n.charAt(0) == 'p')).values().toArray()[1];
        if (playState.size() != Integer.parseInt(gArrangement.split(" ")[2])) {
            return false;
        }
        for (k = 0; k < playState.size(); k++) {
            if (!(((String) playState.get(k)).matches("^p [0-9](( [1-9][0-9]?[0-9]?)|( 0)){1,} S( \\d{1,},\\d{1,}){0,} T( \\d{1,},\\d{1,}){0,};?"))) {
                return false;
            }
        }
        return true; // FIXME Task 3
    }

    /**
     * Check if the string encoding of the move is syntactically well-formed.
     * <p>
     * A description of the move string will be included in README.md
     * in an update of the project after D2B is complete.
     *
     * @param moveString a string representing a player's move
     * @return true if moveString is well-formed and false otherwise
     */
    public static boolean isMoveStringWellFormed(String moveString){
         return false; // FIXME Task 4
    }

    /**
     * Given a state string which is yet to have resources distributed amongst the stone circles,
     * randomly distribute the resources and statuettes between all the stone circles.
     * <p>
     * There will always be exactly 32 stone circles.
     * <p>
     * The resources and statuettes to be distributed are:
     * - 6 coconuts
     * - 6 bamboo
     * - 6 water
     * - 6 precious stones
     * - 8 statuettes
     * <p>
     * The distribution must be random.
     *
     * @param stateString a string representing a game state without resources distributed
     * @return a string of the game state with resources randomly distributed
     */
    public static String distributeResources(String stateString){
        var count = 0;
        int k = 0;
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == ';') {
                count++;
            }
        }

        String[] statelist = stateString.split("; ", count);
        // Game Arrangement Statement
        String gArrangement = statelist[0];

        // Current State Statement
        String csState = statelist[1];

        // Island State Statement
        ArrayList iState = (ArrayList) Arrays.stream(statelist).collect(Collectors.partitioningBy(n -> n.charAt(0) == 'i')).values().toArray()[1];
        String iString= "i" + stateString.split("i", 2)[1].split("s", 2)[0];

        // Stones Statement
        String stoneState = (statelist[iState.size() + 2]);

        // Players Statement
        String playString = "p" + stateString.split("p", 2)[1];

        //assign rsrcs
        ArrayList stoneCoords = new ArrayList(Arrays.asList(stoneState.split(" ")));
        ArrayList rsrcs = new ArrayList();
        stoneCoords.remove(0);
        while (stoneCoords.size() > 0) {
            var num = 6;
            if (stoneCoords.size() == 8) {
                num = 8;
            }

            String[] rscrsSub = new String[num];
            for (k = 0; k < num; k ++) {
                int random = (int)(Math.random() * stoneCoords.size());
                rscrsSub[k] = (String) stoneCoords.get(random);
                stoneCoords.remove(random);
            }
            rsrcs.add(rscrsSub);
        }

        // accumulate coordinates into rsrcs string
        String rsrcAccum = "r ";
        int j = 0;
        char[] symbols = new char[] {'C', 'B', 'W', 'P', 'S'};
        for (k = 0; k < rsrcs.size(); k ++) {
            rsrcAccum += symbols[k];
            var current = (String[]) rsrcs.get(k);
            for (j = 0; j < current.length; j++) {
                rsrcAccum += " " + current[j];
            }
            if (k!= 4) {
                rsrcAccum += " ";
            }
        }
        return gArrangement + "; " + csState + "; " + iString + stoneState + "; " + rsrcAccum + "; " + playString; // FIXME Task 6
    }

    /**
     * Given a state string and a move string, determine if the move is
     * valid for the current player.
     * <p>
     * For a move to be valid, the player must have enough pieces left to
     * play the move. The following conditions for each phase must also
     * be held.
     * <p>
     * In the Exploration Phase, the move must either be:
     * - A settler placed on any unoccupied sea space
     * - A settler or a village placed on any unoccupied land space
     *   adjacent to one of the player's pieces.
     * <p>
     * In the Settlement Phase, the move must be:
     * - Only a settler placed on an unoccupied space adjacent to
     *   one of the player's pieces.
     * Importantly, players can now only play on the sea if it is
     * adjacent to a piece they already own.
     *
     * @param stateString a string representing a game state
     * @param moveString a string representing the current player's move
     * @return true if the current player can make the move and false otherwise
     */
    //0:sea
    //1:island/land
    //2:occupied settler
    //3:occupied village
    //4:current player occupied settler
    //5:current player occupied village
    public static boolean isMoveValid(String stateString, String moveString) {
        //System.out.println(stateString);
        String[] stateArray = stateString.split("; |;"); //check
        String[] gameArrangeStatement = stateArray[0].split(" ");
        int numPlayers = Integer.parseInt(gameArrangeStatement[2]);//check
        String[] currentStateStatement = stateArray[1].split(" ");
        int length = stateArray.length;
        int currentPlayerId = Integer.parseInt(currentStateStatement[1]);//check
        String phase = (currentStateStatement[2]);//check
        int[][] layout = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9}

        };
        int[][] mapstatus = {
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9}

        };
        //islandStatement
        Set<String> islandState = new HashSet<String>();//check
        int i = 2;
        while (stateArray[i].charAt(0) == 'i') {
            islandState.add(stateArray[i]);
            i++;
        }
//       for(String e: islandState){
//        System.out.println(e);
//       }
        for (String j : islandState) {
            //System.out.println(j);
            String[] temp = j.split(" ");
            for (int k = 2; k < temp.length; k++) {
                //System.out.println(temp[k]);

                String[] coord = temp[k].split(",");

                int x = Integer.parseInt(coord[0]);
                int y = Integer.parseInt(coord[1]);
                layout[x][y] = 1; // 1 represents island
            }
        }
        String stonesStatement = stateArray[i];
        i++;
        String unclaimedResourcesAndStatuettesStatement = stateArray[i];
        i++;
        // System.out.println(stateArray[i]);

        List<String> playerStatement = new ArrayList<String>();
        while (i < length) {
            playerStatement.add(stateArray[i]);
            i++;
        }
        for (String p : playerStatement) {
            //System.out.println(p);
            String[] scores = p.split(" ");
            int id =Integer.parseInt(scores[1]);
            int l = 9; //'S'
            //System.out.println(scores[l]);unchecked
            while (!scores[l].equals("T")) {
                //System.out.println("path");
                String[] settlers = scores[l].split(",");
                int x = Integer.parseInt(settlers[0]);
                int y = Integer.parseInt(settlers[1]);
                mapstatus[x][y] = id; // 2 represents occupied settlers
                l++;
            }
            l++;//'T'
            while (l < scores.length) {
                String[] settlers = scores[l].split(",");
                int x = Integer.parseInt(settlers[0]);
                int y = Integer.parseInt(settlers[1]);
                mapstatus[x][y] = id; // 3 represents occupied villages
                l++;
            }
        }
        String currentPlayStatement = playerStatement.get(currentPlayerId);
        String[] current = currentPlayStatement.split(" ");
        int z = 9;
        while (!current[z].equals("T")) {
//            String[] coord = current[z].split(",");
//            int x = Integer.parseInt(coord[0]);
//            int y = Integer.parseInt(coord[1]);
//            //mapstatus[x][y] = 4; // 4 represents current settlers
            z++;
        }
        int restSettlerPiece=(30-(numPlayers-2)*5)-(z-9);
        //System.out.println(restSettlerPiece);
        z++;
        int acorh=z;
        while (z < current.length) {
//            String[] settlers = current[z].split(",");
//            int x = Integer.parseInt(settlers[0]);
//            int y = Integer.parseInt(settlers[1]);
//            //mapstatus[x][y] = 5; // 5 represents current villages
            z++;
        }
        int restVillagePieces= 5-z+acorh;
        //System.out.println(restVillagePieces);

//        for(int a=0;a<13;a++){
//            for(int b=0;b<13;b++){
//                System.out.print(mapstatus[a][b]);
//            }
//            System.out.println();
//        }
        String[] mve = moveString.split(" ");
        String pieceType = mve[0];
        String[] targetCoordinate = mve[1].split(",");
        int target_x = Integer.parseInt(targetCoordinate[0]);
        int target_y = Integer.parseInt(targetCoordinate[1]);

        if (target_x < 0
                || target_y < 0
                || target_x > 12
                || target_y > 12) {
            //System.out.println("Out of map boundary");
            return false;
        }
        else if(target_y==12&&target_x%2==0){return false;}
        else if(mapstatus[target_x][target_y]!=8){return false;}
        else {
            if (phase.equals("E")) {
                if (pieceType.equals("T")) {
                    if (layout[target_x][target_y] == 0) {
                        //System.out.println("village can't be on sea");
                        return false;//village can't be on sea
                    }
                    if (restVillagePieces == 0) {
                        return false;
                    }
                }
                // else if(pieceType.equals("S")) {
                else{
                    if (layout[target_x][target_y] == 0) {
                        return true;
                    }
                    if (restSettlerPiece == 0)
                        return false;
                }
                //if (layout[target_x][target_y] == 1) {
                if (target_x % 2 == 0) {
                    try {
                        if (mapstatus[target_x - 1][target_y] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x + 1][target_y] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x + 1][target_y + 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x][target_y + 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x - 1][target_y + 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x][target_y - 1] == currentPlayerId) {
                            return true;
                        }
                    }catch (Exception e){}
                }
                else {
                    //System.out.println("enter");
                    try {
                        if (mapstatus[target_x - 1][target_y - 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x - 1][target_y] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x + 1][target_y - 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x + 1][target_y] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x][target_y - 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x][target_y + 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            //}
            else if(phase.equals("S")){
                if(pieceType.equals("T")){return false;}
                if(restSettlerPiece==0){return false;}
                if (target_x % 2 == 0) {
                    try {
                        if (mapstatus[target_x - 1][target_y] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x + 1][target_y] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x + 1][target_y + 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x][target_y + 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x - 1][target_y + 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x][target_y - 1] == currentPlayerId) {
                            return true;
                        }
                    }catch (Exception e){}
                }
                else {
                    //System.out.println("enter");
                    try {
                        if (mapstatus[target_x - 1][target_y - 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x - 1][target_y] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x + 1][target_y - 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x + 1][target_y] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x][target_y - 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (mapstatus[target_x][target_y + 1] == currentPlayerId) {
                            return true;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        //System.out.println(" final false");
        return false;
    }
    /**
     * Given a state string, generate a set containing all move strings playable
     * by the current player.
     * <p>
     * A move is playable if it is valid.
     *
     * @param stateString a string representing a game state
     * @return a set of strings representing all moves the current player can play
     */
    public static Set<String> generateAllValidMoves(String stateString){
        HashSet<String> ms=new HashSet<String>();
        String[] stateArray = stateString.split("; |;"); //check
        String[] gameArrangeStatement = stateArray[0].split(" ");
        int numPlayers = Integer.parseInt(gameArrangeStatement[2]);//check
        String[] currentStateStatement = stateArray[1].split(" ");
        int length = stateArray.length;
        int currentPlayerId = Integer.parseInt(currentStateStatement[1]);//check
        String phase = (currentStateStatement[2]);//check
        int[][] layout = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9}

        };
        int[][] mapstatus = {
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
                {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9}

        };
        //islandStatement
        Set<String> islandState = new HashSet<String>();//check
        int i = 2;
        while (stateArray[i].charAt(0) == 'i') {
            islandState.add(stateArray[i]);
            i++;
        }

        for (String j : islandState) {
            //System.out.println(j);
            String[] temp = j.split(" ");
            for (int k = 2; k < temp.length; k++) {
                //System.out.println(temp[k]);

                String[] coord = temp[k].split(",");

                int x = Integer.parseInt(coord[0]);
                int y = Integer.parseInt(coord[1]);
                layout[x][y] = 1; // 1 represents island
            }
        }
        String stonesStatement = stateArray[i];
        i++;
        String unclaimedResourcesAndStatuettesStatement = stateArray[i];
        i++;


        List<String> playerStatement = new ArrayList<String>();
        while (i < length) {
            playerStatement.add(stateArray[i]);
            i++;
        }
        for (String p : playerStatement) {
            String[] scores = p.split(" ");
            int id =Integer.parseInt(scores[1]);
            int l = 9; //'S'
            while (!scores[l].equals("T")) {
                //System.out.println("path");
                String[] settlers = scores[l].split(",");
                int x = Integer.parseInt(settlers[0]);
                int y = Integer.parseInt(settlers[1]);
                mapstatus[x][y] = id; // 2 represents occupied settlers
                l++;

            }

            l++;//'T'
            while (l < scores.length) {
                String[] settlers = scores[l].split(",");
                int x = Integer.parseInt(settlers[0]);
                int y = Integer.parseInt(settlers[1]);
                mapstatus[x][y] = id; // 3 represents occupied villages
                l++;
            }
        }
        String currentPlayStatement = playerStatement.get(currentPlayerId);
        String[] current = currentPlayStatement.split(" ");
        int z = 9;
        while (!current[z].equals("T")) {
            z++;
        }
        int restSettlerPiece=(30-(numPlayers-2)*5)-(z-9);

        z++;
        int acorh=z;
        while (z < current.length) {
            z++;
        }
        int restVillagePieces= 5-z+acorh;


        for (int a=0;a<13;a++) {
            for (int b = 0; b < 13; b++) {
                if(a%2==0&&b==12){continue;}
                if(mapstatus[a][b]!=8){continue;}
                String MoveString1 = "S " + a + "," + b;
                String MoveString2 = "T " + a + "," + b;
                if (phase.equals("S"))
                {
                    if(restSettlerPiece!=0) {
                        if (a % 2 == 0) {
                            try {
                                if (mapstatus[a - 1][b] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a + 1][b] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a + 1][b + 1] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a][b + 1] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a - 1][b + 1] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a][b - 1] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                        } else {

                            try {
                                if (mapstatus[a - 1][b - 1] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a - 1][b] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a + 1][b - 1] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a + 1][b] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a][b - 1] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (mapstatus[a][b + 1] == currentPlayerId) {
                                    ms.add(MoveString1);
                                }
                            } catch (Exception e) {
                            }

                        }
                    }


                }
                else if (phase.equals("E")) {
                    if (layout[a][b] == 0) {
                        ms.add(MoveString1);
                    } else  {
                        if(restSettlerPiece!=0) {
                            if (a % 2 == 0) {
                                try {
                                    if (mapstatus[a - 1][b] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a + 1][b] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a + 1][b + 1] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a][b + 1] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a - 1][b + 1] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a][b - 1] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                            }
                            else {
                                //System.out.println("enter");
                                try {
                                    if (mapstatus[a - 1][b - 1] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a - 1][b] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a + 1][b - 1] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a + 1][b] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a][b - 1] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a][b + 1] == currentPlayerId) {
                                        ms.add(MoveString1);

                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                        if(restVillagePieces!=0){
                            if (a % 2 == 0) {
                                try {
                                    if (mapstatus[a - 1][b] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a + 1][b] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a + 1][b + 1] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a][b + 1] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a - 1][b + 1] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a][b - 1] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                            }
                            else {
                                //System.out.println("enter");
                                try {
                                    if (mapstatus[a - 1][b - 1] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a - 1][b] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a + 1][b - 1] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a + 1][b] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a][b - 1] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                                try {
                                    if (mapstatus[a][b + 1] == currentPlayerId) {
                                        ms.add(MoveString2);

                                    }
                                } catch (Exception e) {
                                }
                            }





                        }
                    }
                }

            }

        }
        return ms;

    }

//        HashSet<String> ms=new HashSet<String>();
//        for (int a=0;a<13;a++){
//            for(int b=0;b<13;b++){
//                //if(a%2==0&&b==12){continue;}
//                String MoveString1 ="S "+a+","+b;
//                String MoveString2 ="T "+a+","+b;
//                if(isMoveValid(stateString,MoveString1)){ms.add(MoveString1);}
//                if(isMoveValid(stateString,MoveString2)){ms.add(MoveString2);}
//            }
//        }
//
//        return ms; // FIXME Task 8
//    }

    /**
     * Given a state string, determine whether it represents an end of phase state.
     * <p>
     * A phase is over when either of the following conditions hold:
     * - All resources (not including statuettes) have been collected.
     * - No player has any remaining valid moves.
     *
     * @param stateString a string representing a game state
     * @return true if the state is at the end of either phase and false otherwise
     */
    public static boolean isPhaseOver(String stateString){
         return false; // FIXME Task 9
    }

    /**
     * Given a state string and a move string, place the piece associated with the
     * move on the board. Ensure the player collects any corresponding resource or
     * statuettes.
     * <p>
     * Do not handle switching to the next player here.
     *
     * @param stateString a string representing a game state
     * @param moveString a string representing the current player's move
     * @return a new state string achieved by placing the move on the board
     */
    public static String placePiece(String stateString, String moveString){
         return ""; // FIXME Task 10
    }

    /**
     * Given a state string, calculate the "Islands" portion of the score for
     * each player as if it were the end of a phase. The return value is an
     * integer array sorted by player number containing the calculated score
     * for the respective player.
     * <p>
     * The "Islands" portion is calculated for each player as follows:
     * - If the player has pieces on 8 or more islands, they score 20 points.
     * - If the player has pieces on 7 islands, they score 10 points.
     * - No points are scored otherwise.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Islands" portion of
     * the score for each player
     */
    public static int[] calculateTotalIslandsScore(String stateString){
         return new int[]{0, 0}; // FIXME Task 11
    }

    /**
     * Given a state string, calculate the "Links" portion of the score for
     * each player as if it were the end of a phase. The return value is an
     * integer array sorted by player number containing the calculated score
     * for the respective player.
     * <p>
     * Players earn points for their chain of pieces that links the most
     * islands. For each island linked by this chain, they score 5 points.
     * <p>
     * Note the chain needn't be a single path. For instance, if the chain
     * splits into three or more sections, all of those sections are counted
     * towards the total.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Links" portion of
     * the score for each player
     */
    public static int[] calculateIslandLinksScore(String stateString){
         return new int[]{0, 0}; // FIXME Task 11
    }

    /**
     * Given a state string, calculate the "Majorities" portion of the score for
     * each player as if it were the end of a phase. The return value is an
     * integer array sorted by player number containing the calculated score
     * for the respective player.
     * <p>
     * The "Majorities" portion is calculated for each island as follows:
     * - The player with the most pieces on the island scores the number
     *   of points that island is worth.
     * - In the event of a tie for pieces on an island, those points are
     *   divided evenly between those players rounding down. For example,
     *   if two players tied for an island worth 7 points, they would
     *   receive 3 points each.
     * - No points are awarded for islands without any pieces.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Majorities" portion
     * of the score for each player
     */
    public static int[] calculateIslandMajoritiesScore(String stateString){
         return new int[]{0, 0}; // FIXME Task 11
    }

    /**
     * Given a state string, calculate the "Resources" and "Statuettes" portions
     * of the score for each player as if it were the end of a phase. The return
     * value is an integer array sorted by player number containing the calculated
     * score for the respective player.
     * <p>
     * Note that statuettes are not resources.
     * <p>
     * In the below "matching" means a set of the same resources.
     * <p>
     * The "Resources" portion is calculated for each player as follows:
     * - For each set of 4+ matching resources, 20 points are scored.
     * - For each set of exactly 3 matching resources, 10 points are scored.
     * - For each set of exactly 2 matching resources, 5 points are scored.
     * - If they have all four resource types, 10 points are scored.
     * <p>
     * The "Statuettes" portion is calculated for each player as follows:
     * - A player is awarded 4 points per statuette in their possession.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated "Resources" and "Statuettes"
     * portions of the score for each player
     */
    public static int[] calculateResourcesAndStatuettesScore(String stateString){
         return new int[]{0, 0}; // FIXME Task 11
    }

    /**
     * Given a state string, calculate the scores for each player as if it were
     * the end of a phase. The return value is an integer array sorted by player
     * number containing the calculated score for the respective player.
     * <p>
     * It is recommended to use the other scoring functions to assist with this
     * task.
     *
     * @param stateString a string representing a game state
     * @return an integer array containing the calculated scores for each player
     */
    public static int[] calculateScores(String stateString){
         return new int[]{0, 0}; // FIXME Task 11
    }

    /**
     * Given a state string representing an end of phase state, return a new state
     * achieved by following the end of phase rules. Do not move to the next player
     * here.
     * <p>
     * In the Exploration Phase, this means:
     * - The score is tallied for each player.
     * - All pieces are removed from the board excluding villages not on stone circles.
     * - All resources and statuettes remaining on the board are removed. All resources are then
     *   randomly redistributed between the stone circles.
     * <p>
     * In the Settlement Phase, this means:
     * - Only the score is tallied and added on for each player.
     *
     * @param stateString a string representing a game state at the end of a phase
     * @return a string representing the new state achieved by following the end of phase rules
     */
    public static String endPhase(String stateString){
         return ""; // FIXME Task 12
    }

    /**
     * Given a state string and a move string, apply the move to the board.
     * <p>
     * If the move ends the phase, apply the end of phase rules.
     * <p>
     * Advance current player to the next player in turn order that has a valid
     * move they can make.
     *
     * @param stateString a string representing a game state
     * @param moveString a string representing the current player's move
     * @return a string representing the new state after the move is applied to the board
     */
    public static String applyMove(String stateString, String moveString){
         return ""; // FIXME Task 13
    }

    /**
     * Given a state string, returns a valid move generated by your AI.
     * <p>
     * As a hint, generateAllValidMoves() may prove a useful starting point,
     * maybe if you could use some form of heuristic to see which of these
     * moves is best?
     * <p>
     * Your AI should perform better than randomly generating moves,
     * see how good you can make it!
     *
     * @param stateString a string representing a game state
     * @return a move string generated by an AI
     */
    public static String generateAIMove(String stateString){
         return ""; // FIXME Task 16
    }
}
