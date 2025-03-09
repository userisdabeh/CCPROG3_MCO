public class Wolf extends Piece {
    public Wolf(int x, int y) {
        super("Wolf", 4, x, y);
    }
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false;
    }
}