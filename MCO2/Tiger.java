import java.util.ArrayList;

/**
 * Represents the Tiger piece in the game.
 * The Tiger has a strength of 6 and is a subclass of Piece.
 * 
 * Currently, the Tiger class is not implemented and always returns false.
 */
public class Tiger extends Piece {

    /**
     * Constructor for the Tiger class at the specified position.
     * @param x The row position of the tiger on the board.
     * @param y The column position of the tiger on the board,
     */
    public Tiger(int x, int y) {
        super("Tiger", 6, x, y);
    }

    @Override
    public ArrayList<int[]> getValidMoves(Board board) {
        return generateValidMoves(board);
    }

    /**
     * Attempts to move the Lion to the new position.
     * The Lion can jump over the lake if no rats are in the way.
     * The Lion can only move orthogonally.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        // Try lake jump first
        if (handleLakeJump(dx, dy, board)) return true;

        // Normal move
        return basicMove(newX, newY, board) && !board.isLake(newX, newY);
    }
}
