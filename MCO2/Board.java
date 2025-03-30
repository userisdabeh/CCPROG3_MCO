import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private Terrain[][] terrain;
    private ArrayList<int[]> p1Possible;
    private ArrayList<int[]> p2Possible;
    private Player p1, p2;

    public Board() {
        terrain = new Terrain[9][7];
        p1Possible = new ArrayList<>();
        p2Possible = new ArrayList<>();
        setupTerrain(p1, p2);
        setupPossiblePositions();
    }

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

    // Getter methods
    public ArrayList<int[]> getP1Possible() {
        return p1Possible;
    }
    public ArrayList<int[]> getP2Possible() {
        return p2Possible;
    }

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
     * Places a piece on the board.
     */
    public void placePiece(Piece piece, int x, int y) {
        if (isValidPosition(x, y)) {
            terrain[x][y].setPiece(piece);
            piece.setPosition(x, y);
        }
    }


    /**
     * Updates a piece's position.
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
     * Gets the piece at specified coordinates.
     */
    public Piece getPiece(int x, int y) {
        return isValidPosition(x, y) ? terrain[x][y].getPiece() : null;
    }

    /**
     * Checks if position contains a lake.
     */
    public boolean isLake(int x, int y) {
        return isValidPosition(x, y) && terrain[x][y] instanceof Lake;
    }

    /**
     * Checks if position contains a trap.
     */
    public boolean isTrap(int x, int y) {
        return isValidPosition(x, y) && terrain[x][y] instanceof Trap;
    }

    public boolean isBase(int x, int y) {
        return terrain[x][y] instanceof HomeBase;
    }

    public boolean isOwnBase(int x, int y, Player player) {
        if (!isBase(x, y)) return false;
        HomeBase base = (HomeBase) terrain[x][y];
        return base.getOwner().equals(player);
    }

    /**
     * Validates board coordinates.
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 7;
    }

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