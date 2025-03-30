import java.util.ArrayList;

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

    @Override
    public ArrayList<int[]> getValidMoves(Board board) {
        return generateValidMoves(board);
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
        if (board.isLake(newX, newY)) return false;
        return basicMove(newX, newY, board);
    }
}