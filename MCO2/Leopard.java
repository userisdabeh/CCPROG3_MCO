import java.util.ArrayList;

/**
 * Represents the Leopard piece in the game.
 * The Leopard has a strength of 5.
 * Extends the {@link Piece} class.
 * The Leopard can move to any adjacent square, including diagonally.
 */
public class Leopard extends Piece {

    /**
     * Constructor for the Leopard class at the specified position.
     * 
     * @param x The row position of the leopard on the board.
     * @param y The column position of the leopard on the board,
     */
    public Leopard(int x, int y) {
        super("Leopard", 5, x, y);
    }

    /**
     * Generates a list of valid moves for the leopard piece.
     * 
     * @param board The game board.
     * @return A list of valid moves for the leopard piece.
     */
    @Override
    public ArrayList<int[]> getValidMoves(Board board) {
        return generateValidMoves(board);
    }

    /**
     * Attempts to move the Leopard to the new position.
     *
     * @param newX The new row position of the Leopard.
     * @param newY The new column position of the Leopard.
     * @param board The game board.
     * @return {@code true} if the move is successful, {@code false} if the move is invalid.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        if (board.isLake(newX, newY)) return false;
        return basicMove(newX, newY, board);
    }
}