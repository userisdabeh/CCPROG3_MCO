import java.util.ArrayList;

/**
 * Represents the Elephant piece in the Jungle King game.
 * The Elephant has a strength of 8.
 * Extends the {@link Piece} class.
 * The Elephant can move to any adjacent square, including diagonally.
 * The Elephant has no special abilities or restrictions.
 * The Elephant cannot move into a lake.
 * The Elephant can be captured by any piece with a higher strength or a rat on land.
 */
public class Elephant extends Piece {

    /**
     * Constructor for the Elephant class at the specified position.
     * @param x The row position of the Elephant on the board.
     * @param y The column position of the Elephant on the board,
     */
    public Elephant(int x, int y) {
        super("Elephant", 8, x, y);
    }

    /**
     * Generates a list of valid moves for the Elephant piece.
     * 
     * @param board The game board.
     * @return A list of valid moves for the Elephant piece.
     */
    @Override
    public ArrayList<int[]> getValidMoves(Board board) {
        return generateValidMoves(board);
    }

    /**
     * Attempts to move the Elephant to the new position.
     * 
     * @param newX The new row position of the Elephant.
     * @param newY The new column position of the Elephant.
     * @param board The game board.
     * @return {@code true} if the move is successful, {@code false} if the move is invalid.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        if (board.isLake(newX, newY)) return false;
        return basicMove(newX, newY, board);
    }
}