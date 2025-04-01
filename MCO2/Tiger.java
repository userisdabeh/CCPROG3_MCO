import java.util.ArrayList;

/**
 * Represents the Tiger piece in the game.
 * The Tiger has a strength of 6.
 * Extends the {@link Piece} class.
 * The Tiger can move to any adjacent square, including diagonally.
 * The Tiger has the ability to jump over the lake if no rats are in the way.
 */
public class Tiger extends Piece {

    /**
     * Constructor for the Tiger class at the specified position.
     * 
     * @param x The row position of the tiger on the board.
     * @param y The column position of the tiger on the board.
     */
    public Tiger(int x, int y) {
        super("Tiger", 6, x, y);
    }

    /**
     * Generates a list of valid moves for the tiger piece.
     * 
     * @param board The game board.
     * @return A list of valid moves for the tiger piece.
     */
    @Override
    public ArrayList<int[]> getValidMoves(Board board) {
        return generateValidMoves(board);
    }

    /**
     * Executes the move for the Tiger piece with special handling for lake jumps. Movement process:
     * <ol>
     *  <li>Check if the new position is a lake and if the current position is a lake edge.</li>
     *  <li>Validates standard movement if not jumping</li>
     *  <li>Verifies that there are no rats in the way when jumping over the lake.</li>
     *  <li>Updates the position of the Tiger piece.</li>
     * </ol>
     * 
     * @param newX The new row position of the Tiger.
     * @param newY The new column position of the Tiger.
     * @param board The game board.
     * @return {@code true} if the move is successful, {@code false} if the move is invalid.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        if (board.isLake(newX, newY) && isLakeEdge(x, y))
            return handleLakeJump(dx, dy, board);

        // Normal move
        return basicMove(newX, newY, board) && !board.isLake(newX, newY);
    }
}
