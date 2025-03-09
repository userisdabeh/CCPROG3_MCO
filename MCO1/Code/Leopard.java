public class Leopard extends Piece {
    public Leopard(int x, int y) {
        super("Leopard", 5, x, y);
    }
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false;
    }
}