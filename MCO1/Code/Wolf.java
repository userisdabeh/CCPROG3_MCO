/**
 * Represents the Wolf piece in the game.
 * The Wolf has a strength of 4 and is a subclass of Piece.
 * 
 * Currently, the Wolf class is not implemented and always returns false.
 */
public class Wolf extends Piece {

    /**
     * Constructor for the Wolf class at the specified position.
     * @param x The row position of the wolf on the board.
     * @param y The column position of the wolf on the board,
     */
    public Wolf(int x, int y) {
        super("Wolf", 4, x, y);
    }

    /**
     * Attempts to move the Wolf to the new position.
     * 
     * @param newX The new row position of the Wolf.
     * @param newY The new column position of the Wolf.
     * @param board The game board.
     * @return false since the Wolf cannot yet move.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false;
    }
}