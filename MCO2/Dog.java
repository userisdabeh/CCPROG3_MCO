/**
 * Represents the Dog piece in the game.
 * The Dog has a strength of 3 and is a subclass of Piece.
 * 
 * Currently, the Dog class is not implemented and always returns false.
 */
public class Dog extends Piece {

    /**
     * Constructor for the Dog class at the specified position.
     * @param x The row position of the Dog on the board.
     * @param y The column position of the Dog on the board,
     */
    public Dog(int x, int y) {
        super("Dog", 3, x, y);
    }

    /**
     * Attempts to move the Dog to the new position.
     * 
     * @param newX The new row position of the Dog.
     * @param newY The new column position of the Dog.
     * @param board The game board.
     * @return false since the Dog cannot yet move.
     */
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false;
    }
}