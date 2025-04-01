import java.util.ArrayList;

/**
 * Represents the Cat piece in the Jungle King game.
 * The Cat has a strength of 2.
 * Extends the {@link Piece} class.
 * The Cat can move to any adjacent square, including diagonally.
 * The Cat has no special abilities or restrictions.
 */
public class Cat extends Piece {

    /**
     * Constructor for the Cat class at the specified position.
     * 
     * @param x The row position of the cat on the board.
     * @param y The column position of the cat on the board,
     */
    public Cat(int x, int y) {
        super("Cat", 2, x, y);
    }

    /**
     * Generates a list of valid moves for the Cat piece.
     * 
     * @param board The game board.
     * @return A list of valid moves for the Cat piece.
     */
    @Override
    public ArrayList<int[]> getValidMoves(Board board) {
        return generateValidMoves(board);
    }

    /**
     * Attempts to move the Cat to the new position.
     *
     * @param newX The new row position of the Cat.
     * @param newY The new column position of the Cat.
     * @param board The game board.
     * @return {@code true} if the move is successful, {@code false} if the move is invalid.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        if (board.isLake(newX, newY)) return false;
        return basicMove(newX, newY, board);
    }
}