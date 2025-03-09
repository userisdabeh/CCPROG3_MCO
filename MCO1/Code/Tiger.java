public class Tiger extends Piece {
    public Tiger(int x, int y) {
        super("Tiger", 6, x, y);
    }
    @Override public boolean move(int newX, int newY, Board board) {
        return false; // Cannot move
    }
}
