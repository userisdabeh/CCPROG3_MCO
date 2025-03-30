import java.util.*;
import javax.swing.JLabel;

/**
 * JungleKing class is the main class that runs the Jungle King game.
 * It initializes the game, sets up the board1, and starts the game loop.
 */
public class JungleKing {
    private Board board1;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    private Scanner scanner;

    // First player selection state
    private int selectingPlayer = 1; // 1 for Player 1, 2 for Player 2
    private int[] player1Selection;
    private int[] player2Selection;

    /**
     * Constructor for the JungleKing class.
     * Initializes the game, sets up the board1, and starts the game loop.
     */
    public JungleKing(Board board) {
        board1 = board;  // Now initializes possible positions internally
        createPlayers();
        board1.setupTerrain(player1, player2);
        placePiecesOnBoard(player1);
        placePiecesOnBoard(player2);
    }

    /**
     * Creates the players for the game.
     */
    public void createPlayers() {
        player1 = new Player("Player 1", createPieces(board1.getP1Possible()));
        player2 = new Player("Player 2", createPieces(board1.getP2Possible()));
    }

    /**
     * Creates the pieces for the players.
     * Each piece is assigned a random position from the list of possible positions.
     *
     * @param positions The list of possible positions for the pieces.
     * @return An ArrayList of pieces with random positions.
     */
    public ArrayList<Piece> createPieces(ArrayList<int[]> positions) {
        ArrayList<Piece> pieces = new ArrayList<>();

        // Create pieces in fixed order but assign to random positions
        pieces.add(new Elephant(positions.get(0)[0], positions.get(0)[1]));
        pieces.add(new Lion(positions.get(1)[0], positions.get(1)[1]));
        pieces.add(new Tiger(positions.get(2)[0], positions.get(2)[1]));
        pieces.add(new Leopard(positions.get(3)[0], positions.get(3)[1]));
        pieces.add(new Wolf(positions.get(4)[0], positions.get(4)[1]));
        pieces.add(new Dog(positions.get(5)[0], positions.get(5)[1]));
        pieces.add(new Cat(positions.get(6)[0], positions.get(6)[1]));
        pieces.add(new Rat(positions.get(7)[0], positions.get(7)[1]));

        return pieces;
    }

    public boolean handleStartingPosition(int row, int col) {
        if (selectingPlayer == 1) {
            if (isValidSelection(row, col, board1.getP1Possible())) {
                player1Selection = new int[]{row, col};
                selectingPlayer = 2;
                return false;
            } else {
                return false;
            }
        } else if (selectingPlayer == 2) {
            if (isValidSelection(row, col, board1.getP2Possible())) {
                player2Selection = new int[]{row, col};
                selectingPlayer = 0;
                determineFirstPlayer();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean isValidSelection(int row, int col, ArrayList<int[]> positions) {
        for (int[] pos : positions) {
            if (pos[0] == row && pos[1] == col) return true;
        }
        return false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board1;
    }

    private void determineFirstPlayer() {
        int p1Strength = getPieceStrength(player1, player1Selection);
        int p2Strength = getPieceStrength(player2, player2Selection);

        currentPlayer = (p1Strength >= p2Strength) ? player1 : player2;
    }

    private int getPieceStrength(Player player, int[] position) {
        for (Piece piece : player.getPieces()) {
            if (piece.getX() == position[0] && piece.getY() == position[1]) {
                return piece.getStrength();
            }
        }
        return 0;
    }

    /**
     * Places the players' pieces on the board1.
     *
     * @param player The player whose pieces are to be placed on the board1.
     */
    private void placePiecesOnBoard(Player player) {
        for (Piece piece : player.getPieces()) {
            board1.placePiece(piece, piece.getX(), piece.getY());
            piece.setPlayer(player);
        }
    }

    public boolean isWinningMove(int x, int y, Player currentPlayer) {
        // Get opponent's base position from HomeBase
        Player opponent = (currentPlayer == player1) ? player2 : player1;
        int[] opponentBase = HomeBase.getBasePosition(opponent);

        return x == opponentBase[0] && y == opponentBase[1];
    }

    public boolean checkOpponentDefeated(Player currentPlayer) {
        Player opponent = (currentPlayer == player1) ? player2 : player1;
        return opponent.getPieces().isEmpty();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Switches the turn to the next player.
     */
    public void switchTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getCurrentPlayerName() {
        return currentPlayer.getName();
    }

    public int getSelectingPlayer() {
        return selectingPlayer;
    }
}