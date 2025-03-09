public class Elephant extends Piece {
    public Elephant(int x, int y) {
        super("Elephant", 8, x, y);
    }
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false; // Cannot move
    }
}