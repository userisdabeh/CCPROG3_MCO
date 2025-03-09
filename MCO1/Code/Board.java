import java.util.*;

public class Board {
    private Piece[][] grid;
    private char[][] terrain;

    public Board() {
        grid = new Piece[9][7];
        terrain = new char[9][7];
        setupTerrain();
    }

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

    public void setLakeArea(int startRow, int startCol, int endRow, int endCol) {
        for(int i=startRow; i<=endRow; i++) {
            for(int j=startCol; j<=endCol; j++) {
                terrain[i][j] = '~';
            }
        }
    }

    public void setTrap(int startRow, int startCol, int endRow, int endCol) {
        for(int i=startRow; i<=endRow; i++) {
            for(int j=startCol; j<=endCol; j++) {
                terrain[i][j] = '*';
            }
        }
    }

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

    public void markPositions(char[][] display, ArrayList<int[]> positions) {
        for(int[] pos : positions) {
            display[pos[0]][pos[1]] = '*';
        }
    }

    public void placePiece(Piece piece, int x, int y) {
        grid[x][y] = piece;
    }

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

    public void updatePiecePosition(Piece piece, int x, int y) {
        if (piece == null) return;
        grid[piece.getX()][piece.getY()] = null;
        grid[x][y] = piece;
        piece.setPosition(x, y);
    }

    public void removePiece(Piece piece) {
        // Remove from the board grid by making it null
        grid[piece.getX()][piece.getY()] = null;

        // Remove from the player's piece list
        Player player = piece.getPlayer();
        if (player != null) {
            player.removePiece(piece);
        }
    }

    public void displayBoard() {
        System.out.println("\n== JUNGLE KING ==");
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

    public Piece getPiece(int x, int y) {
        return grid[x][y];
    }

    public char getTerrain(int x, int y) {
        return terrain[x][y];
    }

    public boolean isLake(int x, int y) {
        return terrain[x][y] == '~';
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 7;
    }
}