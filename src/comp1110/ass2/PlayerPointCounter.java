package comp1110.ass2;

import java.util.*;

/**
 * Authored by Tay Shao An
 * Used to count the number of points obtained by a particular player
 */
public class PlayerPointCounter {
    Integer player;
    Integer numOfIslands;
    Set<Integer> islands;
    List<PieceNode> allPieces;
    ArrayList<Integer>[] playersOnIslands;

    public PlayerPointCounter(int player, Board.Tile[][] tiles, int numOfIslands) {
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

        for (Board.Tile[] k: tiles) {
            len = 0;
            if (x % 2 == 0) {len = -1;}

            for (int y = 0; y < k.length + len; y ++) {
                Board.Tile tile = k[y];

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

    public List<PieceNode> addNodePieces(Board.Tile tile, int x, int y, int shortlong) {
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
        for (PieceNode node: this.allPieces) {
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

    public class PieceNode {
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
        public Set<Integer> nodeRunner(Set<Integer> islands, List<PieceNode> previousNodes) {
            previousNodes.add(this);
            if (this.island != 0) {
                islands.add(this.island);
            }

            // for each node in this nodes' edges
            for (int k = 0; k < this.edges.size(); k++) {
                var getnode = 1;
                // if this node has already appeared before, ignore
                for (PieceNode node : previousNodes) {
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


}


