import java.util.*;

/**
 * Controls core game logic and player turns.
 * Handles player piece creation and placement.
 * Manages game state and player turns.
 * Handles player selection and starting positions.
 * Determines the first player based on piece strength.
 * Checks for winning moves and opponent defeat.
 * 
 */
public class JungleKing {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    // First player selection state
    public int selectingPlayer = 1; // 1 for Player 1, 2 for Player 2
    private int[] player1Selection;
    private int[] player2Selection;

    /**
     * Constructs a JungleKing game with the given board.
     * Initializes players and sets up the game board.
     * 
     * @param board The game board used for the game.
     */
    public JungleKing(Board board) {
        this.board = board;  // Now initializes possible positions internally
        createPlayers();
        this.board.setupTerrain(player1, player2);
        placePiecesOnBoard(player1);
        placePiecesOnBoard(player2);
    }

    /**
     * Creates player instances with pieces positioned according to defined possible locations.
     */
    public void createPlayers() {
        player1 = new Player("Player 1", createPieces(board.getP1Possible()));
        player2 = new Player("Player 2", createPieces(board.getP2Possible()));
    }

    /**
     * Creates pieces for the players based on the provided positions.
     * The pieces are created in a fixed order but assigned to random positions.
     * 
     * @param positions Ordered list of [row, col] positions for the pieces.
     * @return Complete set of pieces for the player.
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

    /**
     * Handles player position during the first-player determination phase.
     * 
     * @param row Selected row (x-coordinate) on the board.
     * @param col Selected column (y-coordinate) on the board.
     * @return true when both players have made valid selections and turn order is set, false if still selecting.
     */
    public boolean handleStartingPosition(int row, int col) {
        if (selectingPlayer == 1) {
            if (isValidSelection(row, col, board.getP1Possible())) {
                player1Selection = new int[]{row, col};
                selectingPlayer = 2; // move to Player 2
                return false; // set to false since Player 2 is not done selecting
            } else {
                return false;
            }
        } else if (selectingPlayer == 2) {
            if (isValidSelection(row, col, board.getP2Possible())) {
                player2Selection = new int[]{row, col};
                selectingPlayer = 0; // default
                determineFirstPlayer();
                return true; // both players are done selecting
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Validates if a coordinate selection exists in the provided list of positions.
     * 
     * @param row The row (x-coordinate) of the selected position.
     * @param col The column (y-coordinate) of the selected position.
     * @param positions The list of valid [row, col] positions.
     * @return true if the (row, col) selection is valid, false otherwise.
     */
    private boolean isValidSelection(int row, int col, ArrayList<int[]> positions) {
        for (int[] pos : positions) {
            if (pos[0] == row && pos[1] == col) return true;
        }
        return false;
    }

    /**
     * Returns the current player.
     * 
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the board being used in the game.
     * 
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Determines the first player based on the strength of the selected pieces.
     * The player with the stronger piece goes first.
     * If both pieces are equal, Player 1 goes first.
     * Updates the currentPlayer accordingly.
     */
    private void determineFirstPlayer() {
        int p1Strength = getPieceStrength(player1, player1Selection);
        int p2Strength = getPieceStrength(player2, player2Selection);

        currentPlayer = (p1Strength >= p2Strength) ? player1 : player2;
    }

    /**
     * Gets the strength of the piece at the given position for the specified player.
     * 
     * @param player The player whose piece strength is to be checked.
     * @param position The position [row, col] of the piece.
     * @return The strength of the piece at the given position.
     */
    private int getPieceStrength(Player player, int[] position) {
        for (Piece piece : player.getPieces()) {
            if (piece.getX() == position[0] && piece.getY() == position[1]) {
                return piece.getStrength();
            }
        }
        return 0;
    }

    /**
     * Places pieces on the board for the specified player.
     * Establishes the player owner for each piece.
     * 
     * @param player The player whose pieces are to be placed on the board.
     */
    private void placePiecesOnBoard(Player player) {
        for (Piece piece : player.getPieces()) {
            board.placePiece(piece, piece.getX(), piece.getY());
            piece.setPlayer(player);
        }
    }

    /**
     * Checks if a move to a specified position is to capture the opponent's home base.
     * 
     * @param x The target x-coordinate of the move.
     * @param y The target y-coordinate of the move.
     * @param currentPlayer The player making the move.
     * @return true if the move captures the opponent's home base, false otherwise.
     */
    public boolean isWinningMove(int x, int y, Player currentPlayer) {
        // Get opponent's base position from HomeBase
        Player opponent = (currentPlayer == player1) ? player2 : player1;
        int[] opponentBase = HomeBase.getBasePosition(opponent);

        return x == opponentBase[0] && y == opponentBase[1];
    }

    /**
     * Checks if opponent has any remaining pieces on the board.
     * 
     * @param currentPlayer The player making the move.
     * @return true if the opponent is defeated (no pieces left), false otherwise.
     */
    public boolean checkOpponentDefeated(Player currentPlayer) {
        Player opponent = (currentPlayer == player1) ? player2 : player1;
        return opponent.getPieces().isEmpty();
    }

    /**
     * Updates the game state to indicate if the game is over.
     * 
     * @return true if the game is over, false otherwise.
     */
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

    /**
     * Returns the Player 1 object.
     * 
     * @return Player 1 instance
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Returns the Player 2 object
     * 
     * @return Player 2 instance
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Returns the current player name.
     * 
     * @return The name of the current player.
     */

    public String getCurrentPlayerName() {
        return currentPlayer.getName();
    }

    /**
     * Returns the Selecting Player Number.
     * 
     * @return The selecting player (1 or 2).
     */
    public int getSelectingPlayer() {
        return selectingPlayer;
    }
}