
/**
 * Represents the Cat piece in the game.
 * The Cat has a strength of 2 and is a subclass of Piece.
 * 
 * Currently, the Cat class is not implemented and always returns false.
 */
public class Cat extends Piece {

    /**
     * Constructor for the Cat class at the specified position.
     * @param x The row position of the cat on the board.
     * @param y The column position of the cat on the board,
     */
    public Cat(int x, int y) {
        super("Cat", 2, x, y);
    }

    /**
     * Attempts to move the Cat to the new position.
     * 
     * @param newX The new row position of the Cat.
     * @param newY The new column position of the Cat.
     * @param board The game board.
     * @return false since the Cat cannot yet move.
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

        Piece targetPiece = board.getPiece(newX, newY); // Get the piece at the target position
        if (targetPiece != null && targetPiece.getPlayer() == this.player) {
            return false; // Prevent moving onto a tile occupied by the same player's piece
        }

        board.updatePiecePosition(this, newX, newY);
        return true;
    }
}