
/**
 * Represents the Elephant piece in the game.
 * The Elephant has a strength of 8 and is a subclass of Piece.
 * 
 * Currently, the Elephant class is not implemented and always returns false.
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
     * Attempts to move the Elephant to the new position.
     * 
     * @param newX The new row position of the Elephant.
     * @param newY The new column position of the Elephant.
     * @param board The game board.
     * @return false since the Elephant cannot yet move.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        int dx = newX - x;
        int dy = newY - y;

        if (!board.isValidPosition(newX, newY)) {
            return false;
        }

        // Only allow straight line movement
        if (dx != 0 && dy != 0) {
            return false;
        }

        board.updatePiecePosition(this, newX, newY);
        return true;
    }
}