public class Board {
    private Piece[][] grid;
    private char[][] terrain; // To represent lakes, traps, and bases

    public Board() {
        grid = new Piece[9][7];
        terrain = new char[9][7];
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize the board with empty spaces
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                terrain[i][j] = '.';
            }
        }

        // Place lakes
        terrain[3][1] = '~';
        terrain[3][2] = '~';
        terrain[4][1] = '~';
        terrain[4][2] = '~';
        terrain[5][1] = '~';
        terrain[5][2] = '~';

        terrain[3][4] = '~';
        terrain[3][5] = '~';
        terrain[4][4] = '~';
        terrain[4][5] = '~';
        terrain[5][4] = '~';
        terrain[5][5] = '~';

        // Place bases
        terrain[8][3] = '1';
        terrain[0][3] = '2';

        // Initialize the pieces
        grid[8][6] = new Lion(8, 6); // player1
        grid[6][6] = new Rat(6, 6); // player1
        grid[0][0] = new Lion(0, 0); // player2
        grid[2][0] = new Rat(2, 0); // player2
    }

    public char getTerrain(int x, int y) {
        return terrain[x][y];
    }

    public Piece getPiece(int x, int y) {
        return grid[x][y];
    }

    public void updatePiecePosition(Piece piece, int newX, int newY) {
        // Remove the piece from its current position
        grid[piece.getX()][piece.getY()] = null;
        // Place the piece in the new position
        grid[newX][newY] = piece;
        piece.setPosition(newX, newY);
    }

    public void displayBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (grid[i][j] != null) {
                    System.out.print(grid[i][j].getName().charAt(0) + " ");
                } else {
                    System.out.print(terrain[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void removePiece(Piece piece) {
        // Remove the piece from the board
        grid[piece.getX()][piece.getY()] = null;

        // Notify the player that the piece has been removed
        Player player = piece.getPlayer();
        if (player != null) {
            player.removePiece(piece);
        }
    }
}