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
        return null;
    }

    /**
     * Attempts to move the Tiger to the new position.
     * 
     * @param newX The new row position of the Tiger.
     * @param newY The new column position of the Tiger.
     * @param board The game board.
     * @return false since the Tiger cannot yet move.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false; // Cannot move
    }
}
