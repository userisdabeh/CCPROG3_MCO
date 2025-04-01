import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the board, which consists of a grid of terrain tiles and manages piece placements.
 */

public class Board {
    private Terrain[][] terrain;
    private ArrayList<int[]> p1Possible;
    private ArrayList<int[]> p2Possible;
    private Player p1, p2;

    /**
     * Constructs a Board object and initializes the terrain and possible positions.
     */
    public Board() {
        terrain = new Terrain[9][7];
        p1Possible = new ArrayList<>();
        p2Possible = new ArrayList<>();
        setupTerrain(p1, p2);
        setupPossiblePositions();
    }

    /**
     * Initializes possible starting positions for both players and shuffles them.
     */
    public void setupPossiblePositions() {
        // Player 1 positions
        p1Possible.add(new int[]{8,6});
        p1Possible.add(new int[]{6,6});
        p1Possible.add(new int[]{7,5});
        p1Possible.add(new int[]{6,4});
        p1Possible.add(new int[]{6,2});
        p1Possible.add(new int[]{7,1});
        p1Possible.add(new int[]{8,0});
        p1Possible.add(new int[]{6,0});

        // Player 2 positions
        p2Possible.add(new int[]{0,0});
        p2Possible.add(new int[]{2,0});
        p2Possible.add(new int[]{1,1});
        p2Possible.add(new int[]{2,2});
        p2Possible.add(new int[]{2,4});
        p2Possible.add(new int[]{1,5});
        p2Possible.add(new int[]{0,6});
        p2Possible.add(new int[]{2,6});

        Collections.shuffle(p1Possible);
        Collections.shuffle(p2Possible);
    }

    /**
     * Returns the list of possible starting positions for Player 1.
     * @return ArrayList of possible positions for Player 1.
     */
    public ArrayList<int[]> getP1Possible() {
        return p1Possible;
    }

    /**
     * Returns the list of possible starting positions for Player 2.
     * @return ArrayList of possible positions for Player 2.
     */
    public ArrayList<int[]> getP2Possible() {
        return p2Possible;
    }

    /**
     * Initializes the terrain of the board with lakes, traps, and home bases.
     * 
     * @param p1 Player 1 Instance
     * @param p2 Player 2 Instance
     */
    public void setupTerrain(Player p1, Player p2) {
        // Initialize all tiles to normal terrain first
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                terrain[i][j] = new NormalTerrain();
            }
        }

        // Setup lakes
        Lake.setLakeArea(terrain, 3, 1, 5, 2);
        Lake.setLakeArea(terrain, 3, 4, 5, 5);

        // Setup traps
        Trap.setTrapArea(terrain, 0, 2, 0, 4);
        Trap.setTrapArea(terrain, 1, 3, 1, 3);
        Trap.setTrapArea(terrain, 8, 2, 8, 4);
        Trap.setTrapArea(terrain, 7, 3, 7, 3);

        // Setup home bases
        terrain[HomeBase.P1_BASE[0]][HomeBase.P1_BASE[1]] = new HomeBase(p1);
        terrain[HomeBase.P2_BASE[0]][HomeBase.P2_BASE[1]] = new HomeBase(p2);
    }

    /**
     * Places a piece on the board at the specified coordinates.
     * 
     * @param piece The piece to be placed.
     * @param x The x-coordinate (row) of the terrain tile.
     * @param y The y-coordinate (column) of the terrain tile.
     */
    public void placePiece(Piece piece, int x, int y) {
        if (isValidPosition(x, y)) {
            terrain[x][y].setPiece(piece);
            piece.setPosition(x, y);
        }
    }


    /**
     * Updates the position of a piece on the board.
     * 
     * @param piece The piece to be moved.
     * @param newX The new x-coordinate (row) of the piece.
     * @param newY The new y-coordinate (column) of the piece.
     */
    public void updatePiecePosition(Piece piece, int newX, int newY) {
        // Clear old position from terrain
        int oldX = piece.getX();
        int oldY = piece.getY();
        if (isValidPosition(oldX, oldY)) {
            terrain[oldX][oldY].removePiece();
        }

        // Place piece in new position
        placePiece(piece, newX, newY);
    }

    /**
     * Checks if the x and y coordinates are within the bounds of the board and returns the terrain at that position.
     * 
     * @param x The x-coordinate (row) of the terrain tile.
     * @param y The y-coordinate (column) of the terrain tile.
     * @return The terrain at the specified coordinates, or null if the coordinates are invalid.
     */
    public Piece getPiece(int x, int y) {
        return isValidPosition(x, y) ? terrain[x][y].getPiece() : null;
    }

    /**
     * Checks if a specific tile is a lake.
     * 
     * @param x The x-coordinate (row) of the terrain tile.
     * @param y The y-coordinate (column) of the terrain tile.
     * @return True if the tile is a lake, false otherwise.
     */
    public boolean isLake(int x, int y) {
        return isValidPosition(x, y) && terrain[x][y] instanceof Lake;
    }

    /**
     * Checks if a specific tile is a trap.
     * 
     * @param x The x-coordinate (row) of the terrain tile.
     * @param y The y-coordinate (column) of the terrain tile.
     * @return True if the tile is a trap, false otherwise.
     */
    public boolean isTrap(int x, int y) {
        return isValidPosition(x, y) && terrain[x][y] instanceof Trap;
    }

    /**
     * Checks if a specific tile is a home base.
     * 
     * @param x The x-coordinate (row) of the terrain tile.
     * @param y The y-coordinate (column) of the terrain tile.
     * @return True if the tile is a home base, false otherwise.
     */
    public boolean isBase(int x, int y) {
        return terrain[x][y] instanceof HomeBase;
    }

    /**
     * Checks if the home base at the specified coordinates belongs to the given player.
     * 
     * @param x The x-coordinate (row) of the terrain tile.
     * @param y The y-coordinate (column) of the terrain tile.
     * @param player The player to check ownership.
     * @return True if the home base belongs to the player, false otherwise.
     */
    public boolean isOwnBase(int x, int y, Player player) {
        if (!isBase(x, y)) return false;
        HomeBase base = (HomeBase) terrain[x][y];
        return base.getOwner().equals(player);
    }

    /**
     * Checks if the specified coordinates are within valid board bounds.
     * 
     * @param x The x-coordinate (row) of the terrain tile.
     * @param y The y-coordinate (column) of the terrain tile.
     * @return True if the coordinates are valid, false otherwise.
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 7;
    }

    /**
     * Removes a piece from the board at the specified coordinates.
     * 
     * @param x The x-coordinate (row) of the terrain tile.
     * @param y The y-coordinate (column) of the terrain tile.
     */
    public void removePiece(int x, int y) {
        if (isValidPosition(x, y)) {
            Piece piece = terrain[x][y].getPiece();  // Get the piece first
            terrain[x][y].removePiece();  // Remove from board

            if (piece != null) {
                Player player = piece.getPlayer();
                if (player != null) {
                    player.removePiece(piece);  // Remove from player's list
                }
            }
        }
    }

}