import java.util.ArrayList;

/**
 * Represents the Lion piece in the game.
 * The Lion has a strength of 7 and is a subclass of Piece.
 * The Lion has the ability to jump over the lake if no rats are in the way.
 */
public class Lion extends Piece {
    
    /**
     * Constructor for the Lion class at the specified position.
     * @param x The row position of the lion on the board.
     * @param y The column position of the lion on the board.
     */
    public Lion(int x, int y) {
        super("Lion", 7, x, y);
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