import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private Terrain[][] terrain;
    private ArrayList<int[]> p1Possible;
    private ArrayList<int[]> p2Possible;

    private Piece[][] grid = new Piece[9][7];

    public Board() {
        terrain = new Terrain[9][7];
        p1Possible = new ArrayList<>();
        p2Possible = new ArrayList<>();
        setupTerrain();
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

    public void showPossiblePositions() {
        System.out.println("\n=== INITIAL POSITIONS ===");
        System.out.println("\n  0 1 2 3 4 5 6 y");

        char[][] display = new char[9][7];

        // Initialize with terrain
        for(int i=0; i<9; i++) {
            for(int j=0; j<7; j++) {
                display[i][j] = terrain[i][j].getSymbol();
            }
        }

        // Mark positions
        markPositions(display, p1Possible);
        markPositions(display, p2Possible);

        for(int i=0; i<9; i++) {
            System.out.print(i + "  ");
            for(int j=0; j<7; j++) {
                System.out.print(display[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("x");
    }

    private void markPositions(char[][] display, ArrayList<int[]> positions) {
        for(int[] pos : positions) {
            display[pos[0]][pos[1]] = '*';
        }
    }

    // Getter methods
    public ArrayList<int[]> getP1Possible() { return p1Possible; }
    public ArrayList<int[]> getP2Possible() { return p2Possible; }

    public void setupTerrain() {
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
        HomeBase.setHomeBases(terrain);
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
     * Checks if a move to (x,y) is valid.
     */
    public boolean isValidMove(int x, int y, Player player) {
        if (!isValidPosition(x, y)) {
            return false;
        }

        Terrain tile = terrain[x][y];
        // Check if moving to own base
        return !(("Player 1".equals(player.getName()) && x == 8 && y == 3) ||
                ("Player 2".equals(player.getName()) && x == 0 && y == 3));
    }

    /**
     * Updates a piece's position.
     */
    public void updatePiecePosition(Piece piece, int newX, int newY) {
        if (piece == null || !isValidPosition(newX, newY)) return;

        // Clear old position
        terrain[piece.getX()][piece.getY()].removePiece();
        // Set new position
        terrain[newX][newY].setPiece(piece);
        piece.setPosition(newX, newY);
    }

    /**
     * Gets the piece at specified coordinates.
     */
    public Piece getPiece(int x, int y) {
        return isValidPosition(x, y) ? terrain[x][y].getPiece() : null;
    }

    /**
     * Gets the terrain type at specified coordinates
     * @param x X coordinate (row)
     * @param y Y coordinate (column)
     * @return Terrain object at position
     */
    public Terrain getTerrain(int x, int y) {
        if (isValidPosition(x, y)) {
            return terrain[x][y];
        }
        return null;  // or throw exception for invalid position
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

    /**
     * Displays the board state.
     */
    public void displayBoard() {
        System.out.println("\n=== JUNGLE KING ===");
        System.out.println("\n   0 1 2 3 4 5 6 y");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < 7; j++) {
                Terrain tile = terrain[i][j];
                if (tile.hasPiece()) {
                    System.out.print(tile.getPiece().getName().charAt(0) + " ");
                } else {
                    System.out.print(tile.getSymbol() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("x");
    }


}