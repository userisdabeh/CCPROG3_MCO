/**
 * Represents the Cat piece in the game.
 * The Cat has a strength of 2 and is a subclass of Piece.
 * 
 * Currently, the Cat class is not implemented and always returns false.
 */
public class Cat extends Piece {
    public Cat(int x, int y) {
        super("Cat", 2, x, y);
    }
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false;
    }
}