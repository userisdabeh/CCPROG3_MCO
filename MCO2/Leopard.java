/**
 * Represents the Leopard piece in the game.
 * The Leopard has a strength of 5 and is a subclass of Piece.
 * 
 * Currently, the Leopard class is not implemented and always returns false.
 */
public class Leopard extends Piece {

    /**
     * Constructor for the Leopard class at the specified position.
     * @param x The row position of the Leopard on the board.
     * @param y The column position of the Leopard on the board,
     */
    public Leopard(int x, int y) {
        super("Leopard", 5, x, y);
    }

    /**
     * Attempts to move the Leopard to the new position.
     * 
     * @param newX The new row position of the Leopard.
     * @param newY The new column position of the Leopard.
     * @param board The game board.
     * @return false since the Leopard cannot yet move.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false;
    }
}