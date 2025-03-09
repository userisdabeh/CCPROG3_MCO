import java.util.*;

/**
 * Represents the game board in Jungle King.
 * The board is a 9x7 grid with terrain and pieces.
 */
public class Board {
    private Piece[][] grid;
    private char[][] terrain;

    /**
     * Constructor for the Board class.
     * Initializes the grid and terrain of the board.
     */
    public Board() {
        grid = new Piece[9][7];
        terrain = new char[9][7];
        setupTerrain();
    }

    /**
     * Initializes the terrain of the board.
     * Adds lakes, traps, and bases to the terrain.
     */
    public void setupTerrain() {
        // Initialize empty terrain
        for(int i=0; i<9; i++) {
            for(int j=0; j<7; j++) {
                terrain[i][j] = '.';
            }
        }

        // Add lakes
        // Left Side
        setLakeArea(3, 1, 3, 2);
        setLakeArea(4, 1, 4, 2);
        setLakeArea(5, 1, 5, 2);
        // Right Side
        setLakeArea(3, 4, 3, 5);
        setLakeArea(4, 4, 4, 5);
        setLakeArea(5, 4, 5, 5);

        // Add traps
        setTrap(0, 2, 0, 4);
        setTrap(1, 3, 1, 3);
        setTrap(8, 2, 8, 4);
        setTrap(7, 3, 7, 3);

        // Add bases
        terrain[8][3] = '1'; // Player 1 base
        terrain[0][3] = '2'; // Player 2 base
    }

    /**
     * Marks a section of the board as a lake.
     * @param startRow The Starting row of the lake.
     * @param startCol The Starting column of the lake.
     * @param endRow The Ending row of the lake.
     * @param endCol The Ending column of the lake.
     */
    public void setLakeArea(int startRow, int startCol, int endRow, int endCol) {
        for(int i=startRow; i<=endRow; i++) {
            for(int j=startCol; j<=endCol; j++) {
                terrain[i][j] = '~';
            }
        }
    }

    /**
     * Marks the surrounding spaces of the base as a trap.
     * @param startRow The Starting row of the trap.
     * @param startCol The Starting column of the trap.
     * @param endRow The Ending row of the trap.
     * @param endCol The Ending column of the trap.
     */
    public void setTrap(int startRow, int startCol, int endRow, int endCol) {
        for(int i=startRow; i<=endRow; i++) {
            for(int j=startCol; j<=endCol; j++) {
                terrain[i][j] = '*';
            }
        }
    }

    /**
     * Displays the board with the possible positions of the pieces.
     * @param p1 The possible positions of Player 1's pieces.
     * @param p2 The possible positions of Player 2's pieces.
     */
    public void showPossiblePositions(ArrayList<int[]> p1, ArrayList<int[]> p2) {
        char[][] display = new char[9][7];

        // Initialize empty
        for(int i=0; i<9; i++) {
            for(int j=0; j<7; j++) {
                display[i][j] = '.';
                display[8][3] = '1';
                display[0][3] = '2';
            }
        }

        // Mark possible positions
        markPositions(display, p1);
        markPositions(display, p2);

        System.out.println("\n=== JUNGLE KING ===");
        System.out.println("\n  0 1 2 3 4 5 6 y");
        for(int i=0; i<9; i++) {
            System.out.print(i + "  ");
            for(int j=0; j<7; j++) {
                System.out.print(display[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("x");
    }

    /**
     * Marks the possible positions on the board.
     * @param display The board display.
     * @param positions The possible positions to mark.
     */
    public void markPositions(char[][] display, ArrayList<int[]> positions) {
        for(int[] pos : positions) {
            display[pos[0]][pos[1]] = '*';
        }
    }

    /**
     * Places a piece on the board at the specified position.
     * 
     * @param piece The piece to place on the board.
     * @param x The row position to place the piece.
     * @param y The column position to place the piece.
     */
    public void placePiece(Piece piece, int x, int y) {
        grid[x][y] = piece;
    }

    /**
     * Checks if the move is valid.
     * 
     * @param x The row position to move to.
     * @param y The column position to move to.
     * @param player The player making the move.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValidMove(int x, int y, Player player) {
        // If out of bounds
        if (x < 0 || x >= 9 || y < 0 || y >= 7) {
            return false;
        }

        // If capturing own base
        if (terrain[x][y] == '1' && player == JungleKing.player1) {
            return false;
        }
        if (terrain[x][y] == '2' && player == JungleKing.player2) {
            return false;
        }

        return true;
    }

    /**
     * Updates the position of the piece on the board.
     * 
     * @param piece The piece to update the position of.
     * @param x The new row position of the piece.
     * @param y The new column position of the piece.
     */
    public void updatePiecePosition(Piece piece, int x, int y) {
        if (piece == null) return;
        grid[piece.getX()][piece.getY()] = null;
        grid[x][y] = piece;
        piece.setPosition(x, y);
    }

    /**
     * Removes a piece from the board.
     * 
     * @param piece The piece to remove from the board.
     */
    public void removePiece(Piece piece) {
        // Remove from the board grid by making it null
        grid[piece.getX()][piece.getY()] = null;

        // Remove from the player's piece list
        Player player = piece.getPlayer();
        if (player != null) {
            player.removePiece(piece);
        }
    }

    /**
     * Displays the current state of the board.
     */
    public void displayBoard() {
        System.out.println("\n=== JUNGLE KING ===");
        System.out.println("\n   0 1 2 3 4 5 6 y");
        for(int i=0; i<9; i++) {
            System.out.print(i + "  ");
            for(int j=0; j<7; j++) {
                if(grid[i][j] != null) {
                    System.out.print(grid[i][j].getName().charAt(0) + " ");
                } else {
                    System.out.print(terrain[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("x");
    }

    /**
     * Gets the piece at the specified position.
     * 
     * @param x The row position of the piece.
     * @param y The column position of the piece.
     * @return The piece at the specified position.
     */
    public Piece getPiece(int x, int y) {
        return grid[x][y];
    }

    /**
     * Gets the terrain at the specified position.
     * 
     * @param x The row position of the terrain.
     * @param y The column position of the terrain.
     * @return The terrain at the specified position.
     */
    public char getTerrain(int x, int y) {
        return terrain[x][y];
    }

    /**
     * Checks if the specified position is a lake.
     * 
     * @param x The row position to check.
     * @param y The column position to check.
     * @return true if the position is a lake, false otherwise.
     */
    public boolean isLake(int x, int y) {
        return terrain[x][y] == '~';
    }

    /**
     * Checks if a given position is within the board's boundaries.
     * 
     * @param x The row position.
     * @param y The column position.
     * @return true if the position is within bounds, false otherwise.
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 7;
    }
}