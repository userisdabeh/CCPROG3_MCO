public class Dog extends Piece {
    public Dog(int x, int y) {
        super("Dog", 3, x, y);
    }
    @Override
    public boolean move(int newX, int newY, Board board) {
        return false;
    }
}